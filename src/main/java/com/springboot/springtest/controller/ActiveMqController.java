package com.springboot.springtest.controller;

import com.springboot.springtest.common.ApiResponse;
import com.springboot.springtest.quene.active.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;

@RestController
@Api(description = "activeMq生产消息")
@RequestMapping(value = "api")
public class ActiveMqController {
     @Autowired
     private ProductService productService;
    @PostMapping("sendMessage")
    @ApiOperation(value = "发送消息")
    public ApiResponse sendMessage() {
        ApiResponse apiResponse;
        try {
            Destination destination = new ActiveMQQueue("active.queue");
            String message  ="发送短信验证码";
            productService.sendMessage(destination,message);
            return ApiResponse.success("发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            apiResponse = ApiResponse.error("请求异常");
        }
        return apiResponse;
    }
}
