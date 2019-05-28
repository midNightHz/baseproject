package cn.zb.base.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * @title:RegCheck.java
 * @ClassName: RegCheck
 * @Description: 用正则去校验字段的合法性
 * @author: 陈军
 * @date: 2019年5月27日 下午5:14:53
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved.
 *
 */
@Target({FIELD,ElementType.TYPE})
@Retention(RUNTIME)
public @interface RegCheck {
	/**
	 * 
	 * @Title: reg
	 * @Description: 正则表达式
	 * @return
	 * @return String
	 * @author 陈军
	 * @date 2019年5月27日 下午5:15:40
	 */
	String reg();
}
