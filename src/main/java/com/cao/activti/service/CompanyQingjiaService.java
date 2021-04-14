package com.cao.activti.service;

import com.cao.activti.entity.Qingjia;
import com.cao.activti.vo.QingjiaVo;

import java.io.InputStream;
import java.util.List;

public interface CompanyQingjiaService {
    // 请假申请并开启流程
    void insertCompanyQingjia(Qingjia qingjia);
    /**
     * 组长或部门经理审批
     * @param taskId    任务id
     * @param uuid      项目id
     * @param userId    组任务用户id
     */
    void agree(String taskId,String uuid,String userId);
    // 查看当前流程图
    InputStream lookCurrentProcessImage(String taskId);
    // 总经理审批或直接结束
    void endOrManager(String taskId);
    // 根据用户id查看待办任务
    List<QingjiaVo> listQingjiaDaiban(String userId);
    // 根据用户id查看已办任务
    List<QingjiaVo> listQingjiaYiban(String userId);

}
