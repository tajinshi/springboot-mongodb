package com.springboot.springtest.quene.active;

import javax.jms.Destination;

public interface ProductService {
    void sendMessage(Destination destination, String message);
}
