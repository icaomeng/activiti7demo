package com.cao.activti.mapper;

import com.cao.activti.entity.Qingjia;
import com.cao.activti.entity.User;
import com.cao.activti.vo.QingjiaVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QingjiaMapper {
    // 新增请假申请
    void insertQingjia(Qingjia qingjia);
    // 根据用户id查看已办任务
    List<QingjiaVo> listQingjiaYiban(@Param("userId") String userId);
    // 根据用户id查看待办任务
    List<QingjiaVo> listQingjiaDaiban(@Param("userId") String userId);
    // 根据工程id获取到详细信息
    QingjiaVo detailByUuid(String uuid);
}
