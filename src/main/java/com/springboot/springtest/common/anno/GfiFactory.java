package com.springboot.springtest.common.anno;

import java.lang.reflect.Method;
import java.util.*;

/**
 *
 * @ClassName GfiFactory
 * @author zl
 * @date 2019年1月8日
 * @Description TODO
 */
public class GfiFactory {


    private static volatile Map<String,Map<String,Class<?>>> classCache = new HashMap<String,Map<String,Class<?>>>();
    /**
     *
     * @author zl
     * @param tClass
     * @return
     * @throws Exception
     */
    public static <T> T getInterface(Class<T> tClass, String systemCode) throws RuntimeException{
        Class<?> clazz = null;
        if(classCache.containsKey(tClass.getName())){
            clazz = classCache.get(tClass.getName()).get(systemCode);
        }else{
            clazz = getImpClass(systemCode,tClass);
        }
        if(clazz==null){
            throw new RuntimeException("GFI 未找到当前国家标识下的实现类。tClass:"+tClass.toString()+",systemCode:"+systemCode);
        }
        try{
            if(isContainMethod(clazz)){
                Method method = clazz.getMethod("getInstance");
                return (T) method.invoke(null,null);
            }
            return (T) clazz.newInstance();
        }catch(Exception e){
            throw new RuntimeException("GFI 创建实现类时发生错误。Class:"+clazz.toString()+",systemCode:"+systemCode);
        }
    }



    private synchronized static Class<?> getImpClass(String countryCode,Class<?> tClass)throws RuntimeException {
        if(classCache.containsKey(tClass.getName())){
            return classCache.get(tClass.getName()).get(countryCode);
        }
        Map<String,Class<?>> clazzes = new HashMap<String,Class<?>>();
        Set<Class<?>> clss = ClassUtil.getClassSet(tClass.getPackage().getName());
        if (clss != null && clss.size() > 0) {
            for (Class<?> cls : clss) {
                if(tClass.isAssignableFrom(cls)){
                    GloblaAnnotation gfiCCAnno = cls.getAnnotation(GloblaAnnotation.class);
                    if(gfiCCAnno!=null){
                        clazzes.put(gfiCCAnno.SystemCode(),cls);
                    }
                }
            }
        }
        classCache.put(tClass.getName(), clazzes);
        return clazzes.get(countryCode);
    }

    private static boolean isContainMethod(Class<?> clazz){
        Method[] methods = clazz.getMethods();
        if(methods != null && methods.length > 0){
            for(Method m :methods){
                String mName = m.getName();
                if("getInstance".equals(mName)){
                    return true;
                }
            }
        }
        return false;
    }



}