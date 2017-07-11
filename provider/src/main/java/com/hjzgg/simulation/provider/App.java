package com.hjzgg.simulation.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

// SpringBoot标准注解, 一般写在App入口处
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScans({
        @ComponentScan("com.hjzgg.simulation.provider"),
        //扫描 工具类中的 ContextUtils获取上下文
        @ComponentScan("com.hjzgg.simulation.common.utils")
})
// 开启Servlet扫描
@ServletComponentScan

public class App {
    public static void main(String[] args) {

        SpringApplication.run(App.class, args);

    }

}