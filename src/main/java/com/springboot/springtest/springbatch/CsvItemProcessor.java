package com.springboot.springtest.springbatch;

import com.springboot.springtest.bean.Person;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

import java.util.Objects;
import java.util.UUID;

/**
 * 自定义一个校验器，用于数据处理操作
 */
public class CsvItemProcessor extends ValidatingItemProcessor<Person> {
    @Override
    public Person process(Person item) throws ValidationException {
        /**
         * 需要执行super.process(item)才会调用自定义校验器
         */
        super.process(item);
        /**
         * 对数据进行简单的处理，若民族为汉族，则数据转换为01，其余转换为02
         */
        item.setId(UUID.randomUUID().toString().replace("-",""));
        if (Objects.equals(item.getJob(),"公务员")) {
            item.setJob("01");
        } else {
            item.setJob("02");
        }
        return item;
    }
}