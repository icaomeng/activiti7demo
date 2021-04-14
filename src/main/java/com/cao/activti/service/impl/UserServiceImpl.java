package com.cao.activti.service.impl;

import com.cao.activti.entity.User;
import com.cao.activti.mapper.UserMapper;
import com.cao.activti.service.UserService;
import com.cao.activti.utils.CommUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void inserUser(User user) {
        user.setUuid(CommUtils.getUUID());
        userMapper.insertUser(user);
    }
}
