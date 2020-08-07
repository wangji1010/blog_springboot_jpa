package com.wj.blogs.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
* 拦截器的配置类
* */
@Configuration
public class WenConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")//添加拦截的地址
                .excludePathPatterns("/admin")//排除拦截的地址
                .excludePathPatterns("/admin/test")//排除拦截的地址
                .excludePathPatterns("/admin/login");

    }
}
