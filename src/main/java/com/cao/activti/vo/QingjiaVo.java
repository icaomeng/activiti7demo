package com.cao.activti.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class QingjiaVo {
    private String uuid;
    private String name;
    private String cause;
    private String day;
    private String phone;
    private String times;
    private String taskId;
    private String taskName;
}
