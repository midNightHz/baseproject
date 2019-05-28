package cn.zb.utils;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

public abstract class Assert {

    /**
     * 
     * @Title: isEmpty   
     * @Description: 当前字符串为空 则抛出异常  
     * @author:陈军
     * @date 2019年5月22日 上午10:53:21 
     * @param str
     * @param message      
     * void      
     * @throws
     */
    public static void isEmpty(String str, String message) {

        if (StringUtils.isBlank(str)) {
            throw new IllegalArgumentException(message);
        }

    }

    /**
     * 
     * @Title: isNull   
     * @Description: 当前对象如果为null 则抛出异常  
     * @author:陈军
     * @date 2019年5月22日 上午10:52:45 
     * @param obj
     * @param message      
     * void      
     * @throws
     */
    public static void isNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 
     * @Title: isEquals   
     * @Description: 比较两个对象 如果不相等则抛出异常 
     * @author:陈军
     * @date 2019年5月22日 上午10:52:17 
     * @param a
     * @param b
     * @param message      
     * void      
     * @throws
     */
    public static void isEquals(Object a, Object b, String message) {
        if (!ObjectUtils.equals(a, b)) {
            throw new IllegalArgumentException(message);
        }

    }

    /**
     * 
     * @Title: isEmpty   
     * @Description: 判断集合是否为null 或者为空 否则抛出异常
     * @author:陈军
     * @date 2019年5月22日 上午10:55:27 
     * @param colleciton
     * @param message      
     * void      
     * @throws
     */
    public static <T> void isEmpty(Collection<T> colleciton, String message) {

        isNull(colleciton, message);
        if (colleciton.isEmpty()) {
            throw new IllegalArgumentException(message);
        }

    }
    
    /**
     * 
     * @Title: isMatch   
     * @Description: 正则匹配校验 
     * @author:陈军
     * @date 2019年5月22日 上午11:05:36 
     * @param reg
     * @param str
     * @param message      
     * void      
     * @throws
     */
    public static void isMatch(String reg, String str, String message) {

        if (!RegUtils.isMatch(reg, str)) {
            throw new IllegalArgumentException(message);
        }

    }

}
