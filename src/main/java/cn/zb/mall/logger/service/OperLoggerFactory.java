package cn.zb.mall.logger.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.zb.log.Interface.IBaseLogerService;
import cn.zb.log.Interface.ILoggerServiceFactory;
import cn.zb.log.annotation.OperLogable;
import cn.zb.utils.BeanFactory;

@Service
public class OperLoggerFactory implements ILoggerServiceFactory {
    
    private static Logger logger = LoggerFactory.getLogger(OperLoggerFactory.class);

    @Override
    public IBaseLogerService<?> getLoggerService(OperLogable operLogable) {

        IBaseLogerService<?> baseLogService = null;
        try {
            baseLogService = (IBaseLogerService<?>) BeanFactory.getBean(operLogable.logServiceClass());
        } catch (Exception e) {
            logger.error("加载bean异常");
        }
        return baseLogService;
    }

}
