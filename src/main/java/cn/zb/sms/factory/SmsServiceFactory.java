package cn.zb.sms.factory;

import cn.zb.sms.config.SmsProperties;
import cn.zb.sms.interfaces.ISmsService;
import cn.zb.utils.BeanFactory;

/**
 * 短信服务工厂
 * 
 * @author chen
 *
 */
public class SmsServiceFactory {

	public static ISmsService smsService() throws ClassNotFoundException {
		SmsProperties properties = BeanFactory.getBean(SmsProperties.class);

		String className = properties.getSmsServiceClass();

		ISmsService smsService = (ISmsService) BeanFactory.getBean(Class.forName(className));

		return smsService;

	}

}
