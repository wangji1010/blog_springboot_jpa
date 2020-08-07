package com.wj.blogs.dao;

import com.wj.blogs.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepostory  extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);

}
