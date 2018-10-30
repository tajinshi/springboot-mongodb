package com.springboot.springtest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2

@MapperScan(basePackages = {"com.springboot.springtest.dao"})
public class SpringtestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringtestApplication.class, args);
	}
}
