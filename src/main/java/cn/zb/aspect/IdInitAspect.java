package cn.zb.aspect;

import java.lang.reflect.Method;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import cn.zb.base.entity.BaseEntity;
import cn.zb.base.entity.EntityUtil;
import cn.zb.operlogger.annotation.OperType;
import cn.zb.operlogger.constants.OperTypeValue;

/**
 * 
 * @ClassName: IdInitAspect
 * @Description:初始化id切面,只会初始化Id类型为long或者int的Id,排除掉 GeneratedValue注解的id
 * @author: 陈军
 * @date: 2019年1月22日 上午11:01:04
 * 
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved.
 *
 */
@Aspect
@Order(100)
@Component
public class IdInitAspect {

	/**
	 * 日志记录
	 */

	private static Logger logger = LoggerFactory.getLogger(IdInitAspect.class);

	/**
	 * 
	 * @Title: excudeService @Description: 切面的定义，切面的定义直接用操作的注解 @author:陈军 @date
	 *         2019年2月14日 下午3:08:33 void @throws
	 */
	@Pointcut("@annotation(cn.zb.operlogger.annotation.OperType)")
	public void excudeService() {

	}

	/**
	 * 
	 * @Title: aroundAdive @Description: 切面的具体业务 @author:陈军 @date 2019年2月14日
	 *         下午3:09:23 @param point @return @throws Throwable Object @throws
	 */
	// @SuppressWarnings({ "rawtypes" })
	@Around("excudeService()")
	public Object aroundAdive(ProceedingJoinPoint point) throws Throwable {

		logger.info("开始进入切面");
		Method m = ((MethodSignature) (point.getSignature())).getMethod();
		OperType operType = m.getDeclaredAnnotation(OperType.class);
		if (operType == null) {
			return point.proceed();
		}
		// 是否有新增的注解
		if (operType.type() != OperTypeValue.INSERT) {
			return point.proceed();
		}

		BaseEntity<?> object = (BaseEntity<?>) point.getArgs()[0];
		
		if (object.getId() == null) {
			EntityUtil.setMaxId(object);
		}

		return point.proceed();

	}

}
