package cn.zb.base.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName:  FieldUncheckDiff   
 * @Description:比较字段差异时，不比较该字段   
 * @author: 陈军
 * @date:   2019年1月7日 上午9:48:33   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface FieldUncheckDiff {

}
