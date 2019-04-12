package cn.zb.base.controller;

import org.slf4j.Logger;

/**
 * 
 * @ClassName:  CommonController   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: 陈军
 * @date:   2019年1月23日 上午8:41:23   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public interface CommonController {

    /**
     * 
     * @Title: getLogger   
     * @Description: 日志的获取 
     * @author:陈军
     * @date 2019年1月23日 上午8:41:32 
     * @return      
     * Logger      
     * @throws
     */

    Logger getLogger();
    
    /**
     * 
     * @Title: getServiceController   
     * @Description: TODO(这里用一句话描述这个方法的作用)  
     * @author:陈军
     * @date 2019年1月30日 下午1:04:36 
     * @return      
     * ServiceController      
     * @throws
     */

    default ServiceController getServiceController() {

        return (ServiceController) this;
    }
}
