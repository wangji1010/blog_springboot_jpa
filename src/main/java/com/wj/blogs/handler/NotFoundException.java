package com.wj.blogs.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
* 页面找不到异常
* */
@ResponseStatus(HttpStatus.NOT_FOUND)//定义状态，找不到会跳转到404
public class NotFoundException extends RuntimeException{

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
