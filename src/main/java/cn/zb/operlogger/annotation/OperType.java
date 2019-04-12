package cn.zb.operlogger.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.zb.operlogger.constants.OperTypeValue;

/**
 * 
 * @ClassName:  OperType   
 * @Description:操作类型   
 * @author: 陈军
 * @date:   2019年1月8日 上午10:03:44   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperType {

    OperTypeValue type();

}
