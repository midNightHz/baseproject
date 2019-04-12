package cn.zb.cache.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import cn.zb.operlogger.constants.OperTypeValue;

@Target({ TYPE, ElementType.METHOD })
@Retention(RUNTIME)
public @interface ZBCacheable {
    /**
     * 
     * @Title: key   
     * @Description: key如果取第一个字段 则key=#0,如果是第一个字段的某一个属性 #0.id,会调用对象个getId方法
     * @author:陈军
     * @date 2019年1月18日 下午4:50:14 
     * @return      
     * String      
     * @throws
     */
    String key() default "";

    /**
     * 
     * @Title: operType   
     * @Description:操作类型 插入时不做缓存的更新，查询和保存在完成之后进行更新，删除在删除完成以后更新缓存
     * @author:陈军
     * @date 2019年1月23日 下午1:21:08 
     * @return      
     * OperTypeValue      
     * @throws
     */
    OperTypeValue operType();

}
