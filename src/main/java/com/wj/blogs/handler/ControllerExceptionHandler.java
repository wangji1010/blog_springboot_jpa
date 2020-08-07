package com.wj.blogs.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/*
* 控制异常类  拦截处理
* */
@ControllerAdvice//拦截所有带有controller的注解
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //拦截所有的异常同一做处理
    @ExceptionHandler(Exception.class)//标识方法可以做异常处理
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
        //记录异常
        logger.error("Request URL : {},Exception : {}",request.getRequestURI(),e);
        //判断拦截到的异常是否有404
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) !=null){
            throw e;//抛出异常交给springboot处理
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURI());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
    }
}
