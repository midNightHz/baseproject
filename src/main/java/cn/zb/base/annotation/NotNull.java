package cn.zb.base.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName:  NotNull   
 * @Description:标记在字段上面,用来校验字段是否允许为空
 * @author: 陈军
 * @date:   2019年1月11日 上午9:40:00   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface NotNull {

}
