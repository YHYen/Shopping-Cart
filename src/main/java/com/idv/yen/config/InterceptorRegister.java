package com.idv.yen.config;

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
@Configuration
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
        pathPattern.add("/home.html");
        pathPattern.add("/login.html");
        pathPattern.add("/Products.html");
        pathPattern.add("/register.html");
        pathPattern.add("/users/login");
        pathPattern.add("/users/isLogin");
        pathPattern.add("/users/register");
        pathPattern.add("/users/findUsername/**");
        pathPattern.add("/products/findAllProducts");

        // temp path
        interceptorRegistry.addInterceptor(userInterceptor).excludePathPatterns(pathPattern).order(1);
    }
}
