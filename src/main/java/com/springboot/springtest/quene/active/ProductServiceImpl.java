package com.springboot.springtest.quene.active;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * 生产者
 */
@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
   // 发送消息，destination是发送到的队列，message是待发送的消息
    @Override
    public void sendMessage(Destination destination,final String message) {
        jmsMessagingTemplate.convertAndSend(destination, message);
    }
}
