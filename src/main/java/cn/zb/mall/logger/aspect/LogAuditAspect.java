package cn.zb.mall.logger.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.zb.base.controller.CallContext;
import cn.zb.log.Interface.IBaseLogerService;
import cn.zb.log.entity.BaseOperLogger;
import cn.zb.mall.logger.service.ISystemOperLogItemService;

@Aspect
@Order(30)
@Component
public class LogAuditAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAuditAspect.class);
    @Autowired
    private ISystemOperLogItemService systemOperLogItemService;

    @Pointcut("execution(* cn.zb.log.Interface.IBaseLogerService.auditOperLog(..))")
    public void excudeService() {

    }

    @Around("excudeService()")
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    public Object aroundAdive(ProceedingJoinPoint point) throws Throwable {

        // logger.info("start logauditAspect");

        IBaseLogerService<?> baseLoggerService = (IBaseLogerService<?>) point.getTarget();

        if (logger.isDebugEnabled()) {

            logger.debug("切面目标对象：" + baseLoggerService.getClass().toString());

        }
        // 获取待审核的日志
        BaseOperLogger operLogger = (BaseOperLogger) point.getArgs()[0];
        // 审核状态
        Integer status = (Integer) point.getArgs()[1];

        CallContext callContext = (CallContext) point.getArgs()[2];

        int id = operLogger.getId();
        // 获取审核的数据库
        String tableName = baseLoggerService.getOperTargetTableName();

        systemOperLogItemService.auditLogItem(id, tableName, callContext, status);

        if (logger.isDebugEnabled()) {

            logger.debug(JSONObject.toJSONString(operLogger));

        }

        return null;
    }
}
