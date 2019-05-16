package cn.zb.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class ClassUtils {

    public static List<Field> getFields(Class<?> c) {

        if (c == null) {
            return null;
        }
        List<Field> fs = new ArrayList<>();

        Field[] fields = c.getDeclaredFields();
        if (fields.length > 0) {
            fs.addAll(Arrays.asList(fields));
        }
        Class<?> parent = c.getSuperclass();
        List<Field> pfs = getFields(parent);
        if (pfs != null && pfs.size() > 0) {
            fs.addAll(pfs);
        }
        return fs;
    }

    public static Field getField(Class<?> c, String fileName) {
        if (c == null) {
            return null;
        }
        try {
            return c.getDeclaredField(fileName);

        } catch (Exception e) {
            Class<?> parent = c.getSuperclass();
            return getField(parent, fileName);
        }

    }

    /**
     * 
     * @Title: getSame   
     * @Description: 用来比较两个相似的字段  
     * @author:陈军
     * @date 2019年1月24日 下午1:18:12 
     * @param a
     * @param b
     * @return      
     * List<ClassSameFieldModel>      
     * @throws
     */
    public static List<ClassSameFieldModel> getSame(Class<?> a, Class<?> b) {

        List<Field> fsa = getFields(a);
        List<Field> fsb = getFields(b);

        Map<String, Field> aMap = new HashMap<>();

        if (fsa == null || fsb == null) {
            return new ArrayList<>();
        }
        if (fsa.size() == 0 || fsb.size() == 0) {
            return new ArrayList<>();
        }
        List<ClassSameFieldModel> list = new ArrayList<>();
        fsa.forEach(e -> {
            aMap.put(e.getName(), e);
        });
        fsb.forEach(e -> {
            if (aMap.containsKey(e.getName())) {

                list.add(new ClassSameFieldModel(a, b, e.getName(), aMap.get(e.getName()).getType(), e.getType()));
            }
        });
        return list;

    }

    static class ClassSameFieldModel {

        private Class<?> a;

        private Class<?> b;

        private String fieldName;

        private Class<?> aFieldClass;

        private Class<?> bFieldClass;

        public ClassSameFieldModel() {

        }

        public ClassSameFieldModel(Class<?> a, Class<?> b, String fieldName, Class<?> aFieldClass,
                Class<?> bFieldClass) {
            super();
            this.a = a;
            this.b = b;
            this.fieldName = fieldName;
            this.aFieldClass = aFieldClass;
            this.bFieldClass = bFieldClass;
        }

        public Class<?> getA() {
            return a;
        }

        public Class<?> getB() {
            return b;
        }

        public String getFieldName() {
            return fieldName;
        }

        public Class<?> getaFieldClass() {
            return aFieldClass;
        }

        public Class<?> getbFieldClass() {
            return bFieldClass;
        }

        public void setA(Class<?> a) {
            this.a = a;
        }

        public void setB(Class<?> b) {
            this.b = b;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public void setaFieldClass(Class<?> aFieldClass) {
            this.aFieldClass = aFieldClass;
        }

        public void setbFieldClass(Class<?> bFieldClass) {
            this.bFieldClass = bFieldClass;
        }

    }

    public static Object cloneObject(Object obj) throws Exception {

        Class<?> cl = obj.getClass();

        List<Field> fields = getFields(cl);

        Object result = cl.newInstance();

        for (Field f : fields) {
            f.setAccessible(true);
            f.set(result, f.get(obj));
        }

        return result;
    }

   

}
