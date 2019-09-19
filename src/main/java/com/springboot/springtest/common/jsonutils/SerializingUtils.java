package com.springboot.springtest.common.jsonutils;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;

/**
 * @author tuojinshi
 * @ClassName: SerializingUtils
 * @Description： protostuff 序列化工具类，序列化效果高于fastJson,java自带得序列化
 * @Date 2019/9/19 16:02
 */
public class SerializingUtils {
    /**
     * 将目标类序列化为byte数组
     *
     * @param source
     * @param <T>
     * @return
     */
    public static <T> byte[] serialize(T source) {
        RuntimeSchema<T> schema;
        LinkedBuffer buffer = null;
        byte[] result;
        try {
            schema = RuntimeSchema.createFrom((Class<T>) source.getClass());
            buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
            result = ProtostuffIOUtil.toByteArray(source, schema, buffer);
        } catch (Exception e) {
            throw new RuntimeException("serialize exception");
        } finally {
            if (buffer != null) {
                buffer.clear();
            }
        }

        return result;
    }

    /**
     * 将byte数组反序列化为目标类
     *
     * @param source
     * @param typeClass
     * @param <T>
     * @return
     */
    public static <T> T deserialize(byte[] source, Class<T> typeClass) {
        RuntimeSchema<T> schema;
        T newInstance;
        try {
            schema = RuntimeSchema.createFrom(typeClass);
            newInstance = typeClass.newInstance();
            ProtostuffIOUtil.mergeFrom(source, newInstance, schema);
        } catch (Exception e) {
            throw new RuntimeException("deserialize exception");
        }

        return newInstance;
    }

    public static void main(String[] args) {
        byte[] hellos = serialize("hello");
        String hello = deserialize(hellos,String.class);
        System.out.println(hello);
        System.out.println(hellos);
    }
}
