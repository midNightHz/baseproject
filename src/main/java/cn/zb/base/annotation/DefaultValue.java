package cn.zb.base.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName:  DefaultValue   
 * @Description:字段默认值  number 和string类型
 * @author: 陈军
 * @date:   2019年1月24日 下午3:54:25   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface DefaultValue {

    String value() default "";

}
