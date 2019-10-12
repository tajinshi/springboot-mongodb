package com.springboot.springtest.controller;

import com.springboot.springtest.bean.Student;
import com.springboot.springtest.common.excel.EasyExcelUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tuojinshi
 * @ClassName: ExcelDownload
 * @Description：
 * @Date 2019/10/12 16:21
 */
@RestController
public class ExcelDownloadController {
    @GetMapping("download")
    public void download(HttpServletResponse response){
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Student student = new Student();
            student.setUserName("小明"+i);
            student.setAge(i);
            student.setSex("男"+i);
            student.setAddress("地址"+i);
            list.add(student);
        }
        EasyExcelUtils.exportExcle(response,"测试导出","sheetdemo",Student.class,list);
    }


}
