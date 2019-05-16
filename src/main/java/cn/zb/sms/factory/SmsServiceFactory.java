package cn.zb.sms.factory;

import java.util.HashMap;
import java.util.Map;

import cn.zb.sms.config.SmsProperties;
import cn.zb.sms.interfaces.ISmsService;
import cn.zb.utils.Assert;
import cn.zb.utils.BeanFactory;


/**
 * 
 * @ClassName:  SmsServiceFactory   
 * @Description:短信服务工厂  
 * @author: 陈军
 * @date:   2019年5月8日 下午1:58:36   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public class SmsServiceFactory {

    private final static Map<String, ISmsService> SMS_SERVICE = new HashMap<>();

    public static ISmsService smsService() throws ClassNotFoundException {

        SmsProperties properties = BeanFactory.getBean(SmsProperties.class);

        String className = properties.getSendServiceClass().trim();

        ISmsService service = SMS_SERVICE.get(className);

        if (service == null) {
            synchronized (SMS_SERVICE) {
                service = (ISmsService) BeanFactory.getBean(Class.forName(className));
                
                Assert.isNull(service, "加载短信服务异常");

                SMS_SERVICE.put(className, service);
            }
        }

        return service;

    }

}
