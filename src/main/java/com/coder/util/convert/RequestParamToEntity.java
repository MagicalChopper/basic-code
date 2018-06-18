package com.coder.util.convert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

/**
 * @Author LiuHao
 * @Date 2018/6/10 14:22
 * @Description 请求参数映射为实体
 */
public class RequestParamToEntity {

    private static final Log log = LogFactory.getLog(RequestParamToEntity.class);

    @SuppressWarnings("Duplicates")
    public static <T> T  convert(HttpServletRequest request, Class<T> clazz){
        T entity = null;
        try {
            entity = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String value = request.getParameter(field.getName());
                String type = field.getType().getName();
                if(value!=null){
                    switch (type){
                        case "byte":
                            field.setByte(entity, Byte.parseByte(value));
                            break;
                        case "short":
                            field.setShort(entity, Short.parseShort(value));
                            break;
                        case "int":
                            field.setInt(entity, Integer.parseInt(value));
                            break;
                        case "long":
                            field.setLong(entity, Long.parseLong(value));
                            break;
                        case "char":
                            field.setChar(entity,value.charAt(0));
                            break;
                        case "float":
                            field.setFloat(entity, Float.parseFloat(value));
                            break;
                        case "double":
                            field.setDouble(entity, Double.parseDouble(value));
                            break;
                        case "boolean":
                            field.setBoolean(entity, Boolean.parseBoolean(value));
                            break;
                        default:
                            field.set(entity,value);
                    }
                }
            }
        } catch (InstantiationException e) {
            log.info("请求参数类型转换异常"+e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            log.info("请求参数类型转换异常"+e.getMessage());
            e.printStackTrace();
        }
        return entity;
    }
}
