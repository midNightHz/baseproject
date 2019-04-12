package cn.zb.utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @ClassName:  SerializeUtil   
 * @Description:序列化工具  
 * @author: 陈军
 * @date:   2019年1月28日 上午9:32:45   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public class SerializeUtil {

    /**
     * @throws UnsupportedEncodingException 
     * 
     * @Title: serializeObject   
     * @Description: 对象的序列化  
     * @author:陈军
     * @date 2019年1月28日 上午9:59:44 
     * @param obj
     * @return      
     * byte[]      
     * @throws
     */
    public static byte[] serializeObject(Object obj) throws UnsupportedEncodingException {

        if (obj == null) {
            return new byte[] {};
        }

        SerializeObject serializeObj = new SerializeObject(obj);

        return JSONObject.toJSON(serializeObj).toString().getBytes("utf-8");
    }

    /**
     * @throws ClassNotFoundException 
     * @throws UnsupportedEncodingException 
     * 
     * @Title: deserialization   
     * @Description: 对象的反序列化  
     * @author:陈军
     * @date 2019年1月28日 上午9:59:56 
     * @param bytes
     * @return      
     * Object      
     * @throws
     */
    public static Object deserialization(byte[] bytes) throws UnsupportedEncodingException, ClassNotFoundException {

        String JonStr = new String(bytes, "utf-8");

        JSONObject json = JSON.parseObject(JonStr);

        Class<?> currClass = Class.forName(json.getString("className"));
        if (currClass.isAssignableFrom(String.class)) {
            return json.getString("obj");
        }
        if (currClass.isAssignableFrom(Integer.class)) {
            return json.getInteger("obj");
        }
        if (currClass.isAssignableFrom(Double.class)) {
            return json.getDouble("obj");
        }
        if (currClass.isAssignableFrom(Byte.class)) {
            return json.getByte("obj");
        }
        if (currClass.isAssignableFrom(Short.class)) {
            return json.getShort("obj");
        }
        if (currClass.isAssignableFrom(Boolean.class)) {
            return json.getBoolean("obj");
        }
        if (currClass.isAssignableFrom(Long.class)) {
            return json.getLong("obj");
        }
        if (currClass.isAssignableFrom(Float.class)) {
            return json.getFloat("obj");
        }
        if (currClass.isAssignableFrom(Date.class)) {
            String str = json.getString("obj");
            if (str == null) {
                return null;
            }
            try {
                if (NumberUtils.isLong(str)) {
                    return new Date(new Long(str));
                }
                return DateUtil.parseDate(str, "yyyy-MM-dd HH:mm:ss");
            } catch (Exception e) {
                try {
                    return DateUtil.parseDate(str, "yyyy-MM-dd");
                } catch (Exception e2) {
                    return null;
                }

            }
        }

        JSONObject obj = json.getJSONObject("obj");

        return JSON.parseObject(obj.toString(), currClass);

    }

    static class SerializeObject {

        private String className;

        private Object obj;

        public SerializeObject(Object obj) {
            this.obj = obj;
            this.className = obj.getClass().getName();
        }

        public String getClassName() {
            return className;
        }

        public Object getObj() {
            return obj;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }

    }

    public static void main(String[] args) throws UnsupportedEncodingException, ClassNotFoundException {
        Integer a=1;
        
        Long time=System.currentTimeMillis();
        
        byte[] bytes = serializeObject(a);
        
        System.out.println(System.currentTimeMillis()-time);

        System.out.println(Arrays.toString(bytes));

        System.out.println(deserialization(bytes));

    }

}
