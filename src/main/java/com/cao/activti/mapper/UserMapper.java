package com.cao.activti.mapper;

import com.cao.activti.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    void insertUser(User user);
}
