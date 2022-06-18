package com.meituan.logan.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zb
 * @date 2022/06/18 12:11
 **/
@MapperScan(basePackages = {"com.meituan.logan.web.mapper"})
@SpringBootApplication(scanBasePackages = { "com.meituan.logan.web"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}