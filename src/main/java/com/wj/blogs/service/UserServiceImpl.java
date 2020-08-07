package com.wj.blogs.service;

import com.wj.blogs.dao.UserRepostory;
import com.wj.blogs.po.User;
import com.wj.blogs.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepostory userRepostory;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepostory.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
