package cn.zb.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zb.utils.BeanFactory;

/**
 * 系统配置工厂
 * 
 * @author chen
 *
 */
@Service
public class SystemConfigFactory {
	@Autowired
	private ApplicationProperties properties;

	public ISystemConfig getSystemConfig() {

		try {
			String systemServiceName = properties.getConfigServiceClass();

			if (StringUtils.isNotBlank(systemServiceName)) {
				Class<?> cl = Class.forName(systemServiceName);
				return (ISystemConfig) BeanFactory.getBean(cl);
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}

	}

}
