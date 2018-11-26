package com.springboot.springtest.quene.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 1.需要使用rabbitmq发送消息的地方注入：AmqpTemplate
 * 2.调用converAndSend 发送消息
 */
public class HelloSender{

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        //发送到hello队列
        rabbitTemplate.convertAndSend("hello", context);
        //发送到topic.message
        this.rabbitTemplate.convertAndSend("exchange", "topic.message", context);
    }


}