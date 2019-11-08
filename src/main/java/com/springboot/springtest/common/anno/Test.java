package com.springboot.springtest.common.anno;

import java.util.ServiceLoader;

/**
 * @author tuojinshi
 * @ClassName: Test
 * @Description：
 * @Date 2019/11/8 11:14
 */
public class Test {
    public static void main(String[] args) {
        String student = GfiFactory.getInterface(SystemService.class, "Student").getSystemUser();
        System.out.println(student);
//        ServiceLoader<SystemService> loader = ServiceLoader.load(SystemService.class);
//        for (SystemService systemService : loader) {
//            GloblaAnnotation annotation = systemService.getClass().getAnnotation(GloblaAnnotation.class);
//            System.out.println(annotation.SystemCode());
//        }
    }
}
