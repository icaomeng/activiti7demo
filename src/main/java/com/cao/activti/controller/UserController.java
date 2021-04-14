package com.cao.activti.controller;

import com.cao.activti.entity.User;
import com.cao.activti.service.UserService;
import com.cao.activti.utils.AjaxObj;
import com.cao.activti.utils.CommUtils;
import com.cao.activti.utils.ReturnValCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/insertUser")
    public AjaxObj insertUser(User user){
        System.err.println("进入");
        return  new AjaxObj(ReturnValCode.RTN_VAL_CODE_SUCCESS,"新增成功");
    }
}
