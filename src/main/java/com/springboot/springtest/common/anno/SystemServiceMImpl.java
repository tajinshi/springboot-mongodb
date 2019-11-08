package com.springboot.springtest.common.anno;

import com.springboot.springtest.common.anno.GloblaAnnotation;
import com.springboot.springtest.common.anno.SystemService;

/**
 * @author tuojinshi
 * @ClassName: SystemServiceImpl
 * @Description：
 * @Date 2019/11/8 11:00
 */
@GloblaAnnotation(SystemCode = "Manager")
public class SystemServiceMImpl implements SystemService {
    @Override
    public String getSystemUser() {
        return "Manager";
    }
}
