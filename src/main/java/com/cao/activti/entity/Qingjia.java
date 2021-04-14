package com.cao.activti.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Qingjia {
    private String uuid;
    private String name;
    private String cause;
    private String day;
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date times;
}
