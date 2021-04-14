package com.cao.activti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude ={SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})  // 移除activiti自动认证
//@SpringBootApplication
@MapperScan("com.cao.activti.mapper")
public class ActivtiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivtiApplication.class, args);
        // 不知道什么原因自动部署无法使用，我在这里使用了手动部署
//            // 1.创建ProcessEngine对象
//            ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();
//            // 2.得到RepositoryService实例
//            RepositoryService repositoryService = processEngine.getRepositoryService();
//            // 3.进行部署
//            Deployment deploy = repositoryService.createDeployment()
//                    .addClasspathResource("processes/wangguan1.bpmn")
//                    .addClasspathResource("processes/wangguan1.png").name("公司请假流程").deploy();
//            // 4.输出部署的一些信息
//            System.err.println(deploy.getName());
//            System.err.println(deploy.getId());
    }


}
