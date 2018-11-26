package com.springboot.springtest.controller;

import com.springboot.springtest.common.ApiResponse;
import com.springboot.springtest.quene.rabbit.HelloSenderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/user")
@Api(description = "mq发送消息")
public class rabbitmqController {
    @Autowired
    private HelloSenderService senderService;

    @GetMapping("sendMessage")
    public ApiResponse sendMessage(){
        senderService.send();
        return ApiResponse.success("发送成功");
    }
}
