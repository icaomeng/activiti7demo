package com.cao.activti.service.impl;

import com.cao.activti.entity.Qingjia;
import com.cao.activti.entity.User;
import com.cao.activti.mapper.QingjiaMapper;
import com.cao.activti.mapper.UserMapper;
import com.cao.activti.service.QingjiaService;
import com.cao.activti.service.UserService;
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
public class QingjiaServiceImpl implements QingjiaService {
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
    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;

    @Override
    public void insertQingjia(Qingjia qingjia) {
        qingjia.setUuid(CommUtils.getUUID());
        qingjia.setTimes(Tools.getTimestamp(null));
        qingjiaMapper.insertQingjia(qingjia);
        // 开启流程
        Map<String, Object> variables=new HashMap<>();
        // 申请人id
        variables.put("invoiceRequest", "oighhqnqn8298929r");
        // 获取流程实例
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("qingjia", qingjia.getUuid(), variables);
        // 获取流程实例id
        String piid = pi.getId();
        // 查询当前流程实例的任务
        Task task = taskService.createTaskQuery().processInstanceId(piid).singleResult();
        taskService.complete(task.getId());
    }

    @Override
    public void agree(String taskId) {
        Map<String,Object> variables = new HashMap<String, Object>();
        // 3.查询当前用户是否有任务
        List<Task> task = taskService.createTaskQuery().processDefinitionKey("qingjia").taskAssignee("oighhqnqn8298929r").list();
        if(!CommUtils.isEmpty(task)){
            // 指定辅导员
            variables.put("invoiceRequest", "943nf9eut89oijwp299j");
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
    public void fudaoyuan(String taskId, String status) {
        Map<String,Object> variables = new HashMap<String, Object>();
        // 3.查询辅导员是否有任务
        List<Task> task = taskService.createTaskQuery().processDefinitionKey("qingjia").taskAssignee("943nf9eut89oijwp299j").list();
        if(!CommUtils.isEmpty(task)){
            if("false".equals(status)){
                variables.put("invoiceRequest","oighhqnqn8298929r");
            }
            variables.put("status",status);
            taskService.complete(taskId, variables);
        }
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
