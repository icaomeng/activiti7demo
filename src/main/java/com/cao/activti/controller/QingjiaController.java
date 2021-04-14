package com.cao.activti.controller;

import com.cao.activti.entity.Qingjia;
import com.cao.activti.entity.User;
import com.cao.activti.service.ImageService;
import com.cao.activti.service.QingjiaService;
import com.cao.activti.service.UserService;
import com.cao.activti.utils.AjaxObj;
import com.cao.activti.utils.ReturnValCode;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/qingjia")
public class QingjiaController {

    @Autowired
    private QingjiaService qingjiaService;
    @Autowired
    private ImageService imageService;

    // 请假申请并开启流程
    @PostMapping(value = "/insertQingjia")
    public AjaxObj insertQingjia(Qingjia qingjia){
        qingjiaService.insertQingjia(qingjia);
        return  new AjaxObj(ReturnValCode.RTN_VAL_CODE_SUCCESS,"新增成功");
    }

    // 班主任节点
    @GetMapping(value = "/agree")
    public AjaxObj agree(String taskId){
        qingjiaService.agree(taskId);
        return  new AjaxObj(ReturnValCode.RTN_VAL_CODE_SUCCESS,"请求成功");
    }

    // 辅导员节点
    @GetMapping(value = "/fudaoyuan")
    public AjaxObj fudaoyuan(String taskId,String status){
        qingjiaService.fudaoyuan(taskId,status);
        return  new AjaxObj(ReturnValCode.RTN_VAL_CODE_SUCCESS,"请求成功");
    }

    // 已办任务查询
    @GetMapping(value = "/yiban")
    public AjaxObj yiban(String userId){
        return  new AjaxObj(ReturnValCode.RTN_VAL_CODE_SUCCESS,"请求成功",qingjiaService.listQingjiaYiban(userId));
    }

    // 待办任务查询
    @GetMapping(value = "/daiban")
    public AjaxObj daiban(String userId){
        return  new AjaxObj(ReturnValCode.RTN_VAL_CODE_SUCCESS,"请求成功",qingjiaService.listQingjiaDaiban(userId));
    }

    // 查看自己创建的任务






    /**
    * 查看当前流程图
	 */
    @GetMapping("/lookCurrentProcessImage")
    public void lookCurrentProcessImage(HttpServletRequest request, HttpServletResponse response,String taskId)throws IOException {
        InputStream imageStream = qingjiaService.lookCurrentProcessImage(taskId);
        byte[] b = new byte[1024];
        int len;
        while ((len = imageStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 查看历史流程图
     */
    @RequestMapping("/lookWholeProcessImage")
    public void lookWholeProcessImage(HttpServletRequest request, HttpServletResponse response, String taskId)
            throws Exception {
        //换了一种形式，通过高亮、颜色不同 清晰显示
        HistoricTaskInstance task = ProcessEngines.getDefaultProcessEngine().getHistoryService()
                .createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        byte[] b = imageService.getFlowImgByProcInstId(task.getProcessInstanceId());
        response.getOutputStream().write(b);
    }

}
