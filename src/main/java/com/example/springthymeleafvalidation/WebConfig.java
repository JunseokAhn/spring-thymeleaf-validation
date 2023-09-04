package com.example.springthymeleafvalidation;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
//        WebMvcConfigurer.super.extendHandlerExceptionResolvers(resolvers);
        
        //ResponseStatusExceptionResolver와 DefaultHandlerExceptionResolver보다 높은 우선순서를 가지는 커스텀 Exception리졸버 정의
        resolvers.add(1, new CustomExceptionHandlerResolver());
        for (HandlerExceptionResolver resolver : resolvers) {
            System.out.println(resolver.getClass().getName());
        }
        /*  실행순서
            org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver <- ( GlobalExceptionHandler )
            com.example.springthymeleafvalidation.CustomExceptionHandlerResolver <- ( 405에러처리를 위해 추가 )
            org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver
            org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver
        */

    }
}
