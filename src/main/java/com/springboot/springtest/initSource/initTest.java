package com.springboot.springtest.initSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner 初始化项目加载资源
 */
@Component
public class initTest implements CommandLineRunner{
    @Override
    public void run(String... args) throws Exception {
        System.out.println("初始化加载资源");
    }
}
