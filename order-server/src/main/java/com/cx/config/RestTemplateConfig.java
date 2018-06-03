package com.cx.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component  //这里要将主类加上Component注解将主类对象注入到ioc容器中，否则里头的@Bean注解将无法使用
public class RestTemplateConfig {

    @LoadBalanced //加上@LoadBalanced帮我们做一个负载均衡
    @Bean //@bean注解相当于向spring容器注册这个bean，用法如下
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
