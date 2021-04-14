package com.cao.activti.service;

import com.cao.activti.entity.Qingjia;
import com.cao.activti.vo.QingjiaVo;
import org.apache.ibatis.annotations.Param;

import java.io.InputStream;
import java.util.List;

public interface QingjiaService {
    // 请假申请并开启流程
    void insertQingjia(Qingjia qingjia);
    // 班主任同意
    void agree(String taskId);
    // 查看当前流程图
    InputStream lookCurrentProcessImage(String taskId);
    // 辅导员节点通过或不同意
    void fudaoyuan(String taskId,String status);
    // 根据用户id查看待办任务
    List<QingjiaVo> listQingjiaDaiban(String userId);
    // 根据用户id查看已办任务
    List<QingjiaVo> listQingjiaYiban(String userId);

}
