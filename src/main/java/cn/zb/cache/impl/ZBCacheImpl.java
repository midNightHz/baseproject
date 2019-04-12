package cn.zb.cache.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.zb.utils.Time;

/**
 * 
 * @ClassName:  ZBCacheImpl   
 * @Description:缓存的实现策略 
 * @author: 陈军
 * @date:   2019年2月21日 上午8:53:04   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Component("simplecache")
public class ZBCacheImpl extends AbstractCacheImpl {

    private static Logger logger = LoggerFactory.getLogger(ZBCacheImpl.class);

    private static final AtomicLong COUNT = new AtomicLong(0L);

    @Override
    public void put(String name, Object key, Object value) {
        Time time = new Time();
        Map<Object, byte[]> map = null;
        try {
            map = CACHE_MAP.get(name);
            if (map == null) {
                map = new ConcurrentHashMap<>(32);
                CACHE_MAP.put(name, map);
            }
            if (key != null) {
                byte[] bytes = serialization().serialize(value);
                if (bytes != null) {

                    map.put(key, bytes);
                    COUNT.addAndGet(1L);
                }
            }
        } finally {
            if (logger.isDebugEnabled()) {
                time.stop();
                logger.debug(serialization.getClass().getName());
                logger.debug("添加缓存对象:key={} object :{} 耗时:{} ms", key, JSONObject.toJSON(value), time.getTime());
                logger.debug("caches:{}", JSONObject.toJSON(map));
            }
        }
    }

}
