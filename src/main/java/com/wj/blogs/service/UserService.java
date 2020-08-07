package com.wj.blogs.service;

import com.wj.blogs.po.User;

public interface UserService {

    User checkUser(String username,String password);

}
