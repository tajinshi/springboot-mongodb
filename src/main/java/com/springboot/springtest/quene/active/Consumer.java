package com.springboot.springtest.quene.active;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @JmsListener(destination = "active.queue")
    public void receiveQueue(String message) {
        System.out.println("Consumer收到的报文为:" + message);
    }

}
