package cn.zb.cache.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.zb.cache.interfaces.ZBCache;
import cn.zb.cache.interfaces.ZBCacheFactory;
import cn.zb.config.AppConfig;
import cn.zb.utils.BeanFactory;

/**
 * 
 * @ClassName:  ZbCacheFactoryImpl   
 * @Description:缓存工厂的实现   
 * @author: 陈军
 * @date:   2019年2月22日 下午3:06:49   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Component
public class ZbCacheFactoryImpl implements ZBCacheFactory {

    private static Logger logger = LoggerFactory.getLogger(ZbCacheFactoryImpl.class);

    private ZBCache cache;

    @Autowired
    private AppConfig appConfig;

    @Override
    public ZBCache getZBCache() {

        if (cache == null) {
            initCache();
        }
        return cache;
    }

    private void initCache() {
        try {

            String cacheMode = appConfig.getCacheMode();

            switch (cacheMode.toUpperCase()) {
            case "REDIS":
                cache = BeanFactory.getBean(ZBRedisCacheImpl.class);
                break;
            case "SIMPLE":
                cache = BeanFactory.getBean(ZBCacheImpl.class);
                break;
            case "DELAYED":
                cache = BeanFactory.getBean(ZBDelayedCache.class);
                break;
            default:
                cache = BeanFactory.getBean(ZBCacheImpl.class);
                break;
            }
        } catch (Exception e) {
            logger.warn("加载缓存失败:{}", e.getMessage());
            cache = BeanFactory.getBean(ZBCacheImpl.class);
        }
    }

}
