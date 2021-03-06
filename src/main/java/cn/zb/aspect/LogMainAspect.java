package cn.zb.aspect;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.zb.base.controller.CallContext;
import cn.zb.base.entity.BaseEntity;
import cn.zb.base.model.ObjectDifference;
import cn.zb.exception.OperNeedAuditException;
import cn.zb.operlogger.Interface.IBaseLogerService;
import cn.zb.operlogger.Interface.ILoggerServiceFactory;
import cn.zb.operlogger.annotation.AuditSubmitable;
import cn.zb.operlogger.annotation.OperLogable;
import cn.zb.operlogger.annotation.OperType;
import cn.zb.operlogger.constants.LogAuditConstants;
import cn.zb.operlogger.constants.OperTypeValue;
import cn.zb.operlogger.entity.BaseOperLogger;
import cn.zb.operlogger.service.ISystemOperLogItemService;

/**
 * 
 * @ClassName:  LogMain   
 * @Description:操作日志入口，采用切面的方式来记录日志  
 * @author: 陈军
 * @date:   2019年1月4日 上午10:21:57   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Aspect
@Order(20)
@Component
public class LogMainAspect {

    private static Logger logger = LoggerFactory.getLogger(LogMainAspect.class);

    @Autowired
    private ISystemOperLogItemService SystemOperLogItemService;

    @Autowired
    private ILoggerServiceFactory loggerServiceFactory;

    // 定义切面
    @Pointcut("@annotation(cn.zb.operlogger.annotation.OperType)")
    // @Pointcut("execution(* cn.zb..*Service.**(..))")
    public void excudeService() {

    }

    /**
     * 
     * @Title: aroundAdive   
     * @Description: 切面的具体业务逻辑 
     * @author:陈军
     * @date 2019年1月4日 下午4:16:59 
     * @param point
     * @return
     * @throws Throwable      
     * Object      
     * @throws
     */
    @Around("excudeService()")
	public Object aroundAdive(ProceedingJoinPoint point) throws Throwable {

		// logger.info("开始进入切面");

		// 获取到切面对象的类型
		Class<?> targetClass = point.getTarget().getClass();
		// 要排除掉记录日志的服务类
		if (targetClass.isAssignableFrom(IBaseLogerService.class)
				|| targetClass.isAssignableFrom(ISystemOperLogItemService.class)) {
			return point.proceed();
		}
		// DEBUG日志
		if (logger.isDebugEnabled()) {
			logger.debug("target args:" + JSONObject.toJSONString(point.getArgs()));
		}
		// service上是否有允许记录日志的注解
		OperLogable annotion = targetClass.getAnnotation(OperLogable.class);
		if (annotion == null) {
			return point.proceed();
		}
		// TODO 从注解上获取当前是否需要审核 ---待优化功能，未确定是否要实现该功能

		// 获取目标方法上的注解
		Method m = ((MethodSignature) (point.getSignature())).getMethod();
		OperType operType = m.getDeclaredAnnotation(OperType.class);
		if (operType == null) {
			return point.proceed();
		}
		OperTypeValue value = operType.type();
		// 获取对应日志记录服务层对象
		IBaseLogerService<?> loggerService = loggerServiceFactory.getLoggerService(annotion);
		if (loggerService == null) {
			logger.error("未获取到对应的loggerserver");
			return point.proceed();
		}
		// 具体的业务逻辑
		// 1、修改、删除时需要记录到systemoperlogitems中
		boolean auditLog = loggerService.shouldAudit();
		if (logger.isDebugEnabled()) {
			logger.debug("日志审核：" + auditLog + "-----------" + targetClass);
		}

		// 2、需要根据开关判断是否刷新实体对象
		if (value == OperTypeValue.DELETE) {// 删除时的策略
			return processDeleteLog(point, loggerService, value, auditLog);
		} else if (value == OperTypeValue.UPDATE || value == OperTypeValue.SOFTDELETE) {// 修改时候的策略
			return processUpdateLog(point, loggerService, value, auditLog);
		} else {// 新增时候的策略
			return processInsertLog(point, loggerService, value, auditLog);
		}

	}

	/**
	 * 
	 * @Title: processDeleteLog
	 * @Description: 处理修改时的日志
	 * @param point
	 * @param loggerService
	 * @param value
	 * @param auditLog
	 * @return
	 * @throws Throwable
	 * @return Object
	 * @author 陈军
	 * @date 2019年7月19日 上午10:27:56
	 */
	private Object processDeleteLog(ProceedingJoinPoint point, IBaseLogerService<?> loggerService, OperTypeValue value,
			boolean auditLog) throws Throwable {
		BaseEntity<?> entity = (BaseEntity<?>) point.getArgs()[0];
		Serializable id = entity.getId();
		List<? extends BaseOperLogger> logs = loggerService.findbyObjectId(id + "", LogAuditConstants.INIT);
		if (logs != null && logs.size() > 0) {
			throw new Exception("当前记录已在审核中，请审核以后再操作");
		}
		CallContext callContext = (CallContext) point.getArgs()[1];
		auditLog = auditLog && getObjectAudit(entity);

		// 记录日志
		BaseOperLogger operLogger = loggerService.saveOperLogger(null, entity, value, callContext, "删除", auditLog);
		// 记录日志详情
		SystemOperLogItemService.saveLogItem(null, entity, callContext, operLogger, null);
		if (auditLog) {
			return null;
		}
		Object obj = point.proceed();
		return obj;
	}

	/**
	 * 
	 * @Title: processUpdateLog
	 * @Description: 处理修改是的日志
	 * @param point
	 * @param loggerService
	 * @param value
	 * @param auditLog
	 * @return
	 * @throws Throwable
	 * @return Object
	 * @author 陈军
	 * @date 2019年7月19日 上午10:27:37
	 */
	private Object processUpdateLog(ProceedingJoinPoint point, IBaseLogerService<?> loggerService, OperTypeValue value,
			boolean auditLog) throws Throwable {

		BaseEntity<?> target = (BaseEntity<?>) point.getArgs()[0];
		BaseEntity<?> source = (BaseEntity<?>) point.getArgs()[1];

		CallContext callContext = (CallContext) point.getArgs()[2];
		// 记录日志
		Serializable id = target.getId();
		List<? extends BaseOperLogger> logs = loggerService.findbyObjectId(id + "", LogAuditConstants.INIT);

		if (logs != null && logs.size() > 0) {
			throw new Exception("当前记录已在审核中，请审核以后再操作");
		}
		List<ObjectDifference> diffs = source.checkDiffFromOther(target);
		auditLog = auditLog && getObjectAudit(target);
		String logDesc = getLogDesc(diffs);
		if (value == OperTypeValue.SOFTDELETE) {
			logDesc = "废弃";
		}
		BaseOperLogger operLogger = loggerService.saveOperLogger(target, source, value, callContext, logDesc, auditLog);
		// 记录日志详情
		SystemOperLogItemService.saveLogItem(source, target, callContext, operLogger, diffs);

		if (auditLog) {
			throw new OperNeedAuditException();
			// return source;
		}
		return point.proceed();

	}

	/**
	 * 
	 * @Title: processInsertLog
	 * @Description: 处理新增时日志
	 * @param point
	 * @param loggerService
	 * @param value
	 * @param auditLog
	 * @return
	 * @throws Throwable
	 * @return Object
	 * @author 陈军
	 * @date 2019年7月19日 上午10:27:14
	 */
	private Object processInsertLog(ProceedingJoinPoint point, IBaseLogerService<?> loggerService, OperTypeValue value,
			boolean auditLog) throws Throwable {
		BaseEntity<?> entity = (BaseEntity<?>) point.proceed();
		CallContext callContext = (CallContext) point.getArgs()[1];
		loggerService.saveOperLogger(entity, null, value, callContext, "新增", auditLog);
		return entity;
	}

    private String getLogDesc(List<ObjectDifference> diffs) {
        StringBuilder sb = new StringBuilder();
        diffs.forEach(e -> sb.append(e.toString() + ";"));
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
    
    public static boolean getObjectAudit(BaseEntity<?> entity) {
        AuditSubmitable auditSubmitable = entity.getClass().getAnnotation(AuditSubmitable.class);
        // TODO 是否审核需要从数据库进行配置，----待优化，字段为注解中的auditPramId
        return auditSubmitable != null && auditSubmitable.value();

    }

}
