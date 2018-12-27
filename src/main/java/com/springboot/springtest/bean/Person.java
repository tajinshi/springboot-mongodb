package com.springboot.springtest.bean;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 测试批处理的实体
 * </p>
 *
 * @author D.Yang
 * @since 2018-12-27
 */
@TableName("person")
@Data
public class Person extends Model<Person> {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String address;
    private Integer age;
    private String job;
    private String school;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Person{" +
        ", id=" + id +
        ", name=" + name +
        ", address=" + address +
        ", age=" + age +
        ", job=" + job +
        ", school=" + school +
        "}";
    }
}
