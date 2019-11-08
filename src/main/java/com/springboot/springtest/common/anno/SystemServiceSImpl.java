package com.springboot.springtest.common.anno;

import com.springboot.springtest.common.anno.GloblaAnnotation;
import com.springboot.springtest.common.anno.SystemService;

/**
 * @author tuojinshi
 * @ClassName: SystemServiceSImpl
 * @Description：
 * @Date 2019/11/8 11:02
 */
@GloblaAnnotation(SystemCode = "Student")
public class SystemServiceSImpl implements SystemService {
    @Override
    public String getSystemUser() {
        return "student";
    }
}
