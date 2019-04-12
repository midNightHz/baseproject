package cn.zb.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * 
 * @ClassName:  CacheConfig   
 * @Description:缓存配置   
 * @author: 陈军
 * @date:   2019年1月28日 上午9:03:17   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Service("cacheConfig")
@PropertySource("classpath:appConfig.properties")
public class CacheConfig {
    
    

}
