package com.springboot.springtest.common;

import com.springboot.springtest.bean.User;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 反射工具类
 */
@Slf4j
public class ReflectUtil {
    /**
     * map 集合转对象工具类
     * @param source
     * @param target
     * @return
     * @throws IllegalAccessException
     */
    public static Object MapToBean(Map<String,Object> source, Object target){
        try {
            Class aClass = target.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                // 取消属性的访问权限控制，即使private属性也可以进行访问。
                declaredField.setAccessible(true);
                for (String s : source.keySet()) {
                    if (Objects.equals(s,declaredField.getName())) {
                        declaredField.set(target,source.get(s));
                    }
                }
            }
        } catch (IllegalAccessException e) {
            log.info("map转对象异常--"+e.getStackTrace());
        }
        return target;
    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",20000l);
        map.put("name","ceshi");
        map.put("age",23);
        User user = new User();
        User user1 = (User) MapToBean(map, user);
        System.out.println(user1);
    }
}
