package com.springboot.springtest.bean;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection="user")
public class User implements Serializable {
    private Long id;
    private String name;
    private int age;
    private String address;
}
