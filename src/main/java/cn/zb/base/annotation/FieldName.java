package cn.zb.base.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName:  FieldName   
 * @Description:用来给实体类的字段 赋属性名，用户维护字段的自定义名 
 * @author: 陈军
 * @date:   2019年1月7日 下午4:16:03   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface FieldName {
    /**
     * 
     * @Title: value   
     * @Description: 字段名  
     * @author:陈军
     * @date 2019年1月22日 上午11:05:29 
     * @return      
     * String      
     * @throws
     */
    String value() default "";

}
