package cn.zb.operlogger.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import cn.zb.operlogger.Interface.IBaseLogerService;

/**
 * 
 * @ClassName:  OperLogable   
 * @Description:操作日志记录--在service实现类上添加注解,允许当前的实现类记录操作日志
 *              该对象需要有一个实现类来实现IBaseLogerService来处理记录日志的具体业务 
 * @author: 陈军
 * @date:   2019年1月4日 上午8:32:52   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface OperLogable {
    /**
     * 
     * @Title: value   
     * @Description: 是否记录日志
     * @author:陈军
     * @date 2019年1月14日 上午10:33:08 
     * @return      
     * boolean      
     * @throws
     */
    boolean value() default true;

    /**
     * 
     * @Title: operLogPram   
     * @Description:是否记录日志开关，与value作用相同，使用时优先级会比value搞 
     * @author:陈军
     * @date 2019年1月14日 上午10:33:52 
     * @return      
     * int      
     * @throws
     */
    int operLogPram() default 0;

    /**
     * 
     * @Title: logServiceClass   
     * @Description: 日志记录服务层 
     * @author:陈军
     * @date 2019年2月22日 下午3:59:33 
     * @return      
     * Class<? extends IBaseLogerService<?>>      
     * @throws
     */
    Class<? extends IBaseLogerService<?>> logServiceClass();

}
