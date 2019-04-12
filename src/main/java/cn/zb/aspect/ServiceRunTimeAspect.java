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

import com.alibaba.fastjson.JSONObject;

import cn.zb.utils.Time;

/**
 * 
 * @ClassName:  RunTimeAspect   
 * @Description:运行时的切面，用来统计各个方法的运行时间,杀伤力太大,慎用
 * @author: 陈军
 * @date:   2019年1月22日 下午1:56:46   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Aspect
@Order(200)
@Component
public class ServiceRunTimeAspect {

    private static Logger logger = LoggerFactory.getLogger(ServiceRunTimeAspect.class);

    @Pointcut("execution(* cn.zb..*Service.*(..))")
    public void excudeService() {

    }

    @Around("excudeService()")
    public Object aroundAdive(ProceedingJoinPoint point) throws Throwable {
        // logger.info("进入切面");
        Object target = null;
        Method m = null;
        Time time = null;
        // 参数的记录
        if (logger.isDebugEnabled()) {
            time = new Time();
            target = point.getTarget();
            m = ((MethodSignature) (point.getSignature())).getMethod();
            Object[] args = point.getArgs();
            logger.debug("target:{};method:{};args:{}", target, m, JSONObject.toJSON(args));
            time.start();
        }

        Object obj = point.proceed();
        // 记录运行时间
        if (logger.isDebugEnabled()) {
            time.stop();
            logger.debug(target.toString() + ";" + m.getName() + "运行时间:" + time.getTime() + "ms");
        }
        return obj;
    }
}
