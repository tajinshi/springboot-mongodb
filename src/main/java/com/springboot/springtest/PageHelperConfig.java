package com.springboot.springtest;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class PageHelperConfig {

        @Bean
        public PageHelper pageHelper() {
            System.out.println("MyBatisConfiguration.pageHelper()");
            PageHelper pageHelper = new PageHelper();
            Properties properties = new Properties();
            properties.setProperty("offsetAsPageNum", "true");
            properties.setProperty("rowBoundsWithCount", "true");
            //properties.setProperty("reasonable", "true");
            properties.setProperty("dialect", "mysql");    //配置mysql数据库的方言
            pageHelper.setProperties(properties);
            System.out.println("========== pagehelper init success ============");
            return pageHelper;
        }
}
