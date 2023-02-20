package com.idv.yen.config;

import com.idv.yen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * register interceptor
 */
//@Configuration
public class InterceptorRegister implements WebMvcConfigurer {

    private UserInterceptor userInterceptor;

    @Autowired
    public void setUserInterceptor(UserInterceptor userInterceptor) {
        this.userInterceptor = userInterceptor;
    }

    /**
     * Register the defined interceptors as Bean
     */
    @Bean
    public HandlerInterceptor getInterceptor() {
        return new UserInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        List<String> pathPattern = new ArrayList<>();
        pathPattern.add("/css/style.css");
        pathPattern.add("/js/script.js");
        pathPattern.add("/footer.html");
        pathPattern.add("/header.html");
        pathPattern.add("/home.html");
        pathPattern.add("/login.html");
        pathPattern.add("/Products.html");
        pathPattern.add("/register.html");
        pathPattern.add("/users/isLogin");
        pathPattern.add("/users/register");
        pathPattern.add("/users/findUsername/**");
        pathPattern.add("/users/login");
        // temp path
        pathPattern.add("/images/product");
        interceptorRegistry.addInterceptor(userInterceptor).excludePathPatterns(pathPattern).order(1);
    }
}