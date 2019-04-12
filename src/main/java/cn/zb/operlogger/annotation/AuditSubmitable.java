package cn.zb.operlogger.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import cn.zb.base.service.BaseService;
import cn.zb.operlogger.Interface.IBaseLogerService;

/**
 * 
 * @ClassName:  AuditSubmitable   
 * @Description:允许对象先审核再进行提交保存/修改操作  --注解记录在实体类上,该实体类的服务层要实现BaseService,
 *              还需要有记录日志的服务层实现类
 *              如何使联合主键的情况
 * @author: 陈军
 * @date:   2019年1月4日 下午3:45:27   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface AuditSubmitable {
    /**
     * 
     * @Title: value   
     * @Description: 记录的日志是否需要审核 
     * @author:陈军
     * @date 2019年1月9日 上午10:18:40 
     * @return      
     * boolean      
     * @throws
     */
    boolean value() default true;

    /**
     * 
     * @Title: auditPramId   
     * @Description: 数据库中是否审核日志的配置id，与value作用相同，使用时比value优先级高 
     * @author:陈军
     * @date 2019年1月14日 上午10:31:25 
     * @return      
     * int      
     * @throws
     */
    int auditPramId() default 0;

    /**
     * 
     * @Title: serviceClass   
     * @Description: 记录日志目标实体类的服务层 
     * @author:陈军
     * @date 2019年1月9日 上午10:18:55 
     * @return      
     * Class<? extends BaseService<?,?>>      
     * @throws
     */
    Class<? extends BaseService<?, ?>> serviceClass();

    /**
     * 
     * @Title: logServiceClass   
     * @Description: 记录日志的服务层 
     * @author:陈军
     * @date 2019年1月9日 上午10:19:15 
     * @return      
     * Class<? extends IBaseLogerService<?>>      
     * @throws
     */
    Class<? extends IBaseLogerService<?>> logServiceClass();

}
