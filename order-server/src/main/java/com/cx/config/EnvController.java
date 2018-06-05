package com.cx.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 校验统一配置中心是否能正常使用
 */
@RestController
@RequestMapping("/env")
public class EnvController {

    @Value("${env}")//这里通过注解来在spring容器初始化是初始化它的值
    private String env;

    @RequestMapping("print")
    public String getEnv(){
        return env;
    }
}
