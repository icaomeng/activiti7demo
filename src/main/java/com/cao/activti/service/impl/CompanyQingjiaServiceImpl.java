package com.cao.activti.service.impl;

import com.cao.activti.entity.Qingjia;
import com.cao.activti.mapper.QingjiaMapper;
import com.cao.activti.service.CompanyQingjiaService;
import com.cao.activti.service.QingjiaService;
import com.cao.activti.utils.CommUtils;
import com.cao.activti.utils.Tools;
import com.cao.activti.vo.QingjiaVo;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyQingjiaServiceImpl implements CompanyQingjiaService {
    @Autowired
    private QingjiaMapper qingjiaMapper;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;

    @Override
    public void insertCompanyQingjia(Qingjia qingjia) {
        qingjia.setUuid(CommUtils.getUUID());
        qingjia.setTimes(Tools.getTimestamp(null));
        qingjiaMapper.insertQingjia(qingjia);
        // 开启流程
        Map<String, Object> variables=new HashMap<>();
        // 申请人id
        variables.put("createUser", "49206bf4-3232-4eaa-a1c5-65dba3e236e2");
        // 包含网关的条件
        List<String> list=new ArrayList<>();
        list.add("0dbf020a-3196-4e7e-88d6-f592fd45b195");
        list.add("460d1e63-f932-4973-80c6-58d5a1c3d58f");
        variables.put("day", qingjia.getDay());
        // 如果请假天数小于或等于3天组长审批，否则就是部门经理审批
        if(Integer.parseInt(qingjia.getDay())<=3){
            variables.put("zuzhangList", list);
        }else{
            // 指定部门经理
            variables.put("departmanager", "ba387f8a-9699-4f20-9e7e-ef01f96ad919");
        }
        // 获取流程实例
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("myProcess", qingjia.getUuid(), variables);
        // 获取流程实例id
        String piid = pi.getId();
        // 查询当前流程实例的任务
        Task task = taskService.createTaskQuery().processInstanceId(piid).singleResult();
        taskService.complete(task.getId());
    }

    @Override
    public void agree(String taskId,String uuid,String userId) {
        Map<String,Object> variables = new HashMap<String, Object>();
        QingjiaVo qingjiaVo = qingjiaMapper.detailByUuid(uuid);
        // 如果请假天数小于等于3，组长拾取任务并审批，否则就是部门经理审批
        if(Integer.parseInt(qingjiaVo.getDay())<=3){
            // 根据请假天数来走流程：小于等于5直接结束
            if(Integer.parseInt(qingjiaVo.getDay())<=5){
                // 指定条件
                variables.put("day",qingjiaVo.getDay());
            }
            // 根据用户id拾取任务
            taskService.claim(taskId,userId);
            taskService.complete(taskId,variables);
        }else{
            // 根据请假天数来走流程：大于等于5总经理审批否则就直接结束
            if(Integer.parseInt(qingjiaVo.getDay())>=5){
                // 指定条件和经理
                variables.put("day",qingjiaVo.getDay());
                variables.put("manager","2d15f621-93ae-47c5-a65d-5fadf6e45b1b");
            }
            taskService.complete(taskId, variables);
        }




    }

    @Override
    public InputStream lookCurrentProcessImage(String taskId) {
        HistoricTaskInstance task = historyService// 与历史数据（历史表）相关的Service
                .createHistoricTaskInstanceQuery()// 创建历史任务实例查询
                .taskId(taskId)// 指定历史任务的办理人
                .singleResult();
        // 流程定义
        BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
        ProcessDiagramGenerator ge = new DefaultProcessDiagramGenerator();
        InputStream resource = ge.generateDiagram(bpmnModel, "png",
                runtimeService.getActiveActivityIds(task.getExecutionId()),
                new ArrayList<String>(), "宋体", "宋体", null, 1.0d);
        return resource;
    }

    @Override
    public void endOrManager(String taskId) {
        taskService.complete(taskId);
    }

    @Override
    public List<QingjiaVo> listQingjiaDaiban(String userId) {
        return qingjiaMapper.listQingjiaDaiban(userId);
    }
    @Override
    public List<QingjiaVo> listQingjiaYiban(String userId) {
        return qingjiaMapper.listQingjiaYiban(userId);
    }

}
