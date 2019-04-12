package cn.zb.operlogger.Interface;

import cn.zb.operlogger.annotation.OperLogable;

/**
 * 
 * @ClassName:  ILoggerServiceFactory   
 * @Description:日志记录服务层工厂   
 * @author: 陈军
 * @date:   2019年1月22日 上午11:11:34   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public interface ILoggerServiceFactory {

    IBaseLogerService<?> getLoggerService(OperLogable operLogable);

}
