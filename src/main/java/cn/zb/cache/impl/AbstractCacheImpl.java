package cn.zb.cache.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.zb.cache.interfaces.ZBCache;
import cn.zb.serialize.Serialization;
import cn.zb.serialize.SerializationFactory;
import cn.zb.utils.Time;

/**
 * 
 * @ClassName:  AbstractCacheImpl   
 * @Description:缓存的抽象实现类  
 * @author: 陈军
 * @date:   2019年2月22日 上午11:27:37   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public abstract class AbstractCacheImpl implements ZBCache {

    protected final static Map<String, Map<Object, byte[]>> CACHE_MAP = new ConcurrentHashMap<>(16);

    protected static Logger logger = LoggerFactory.getLogger(AbstractCacheImpl.class);

    @Autowired
    protected SerializationFactory factory;

    protected Serialization serialization;

    protected Serialization serialization() {
        if (serialization == null) {
            serialization = factory.getSerialization();
        }
        return serialization;
    }

    @Override
    public void clean() {

        synchronized (CACHE_MAP) {

            CACHE_MAP.clear();
            // COUNT.set(0L);
        }
    }

    @Override
    public Object get(String name, Object key, Class<?> cl) {
        Time time = new Time();
        try {
            Map<Object, byte[]> map = CACHE_MAP.get(name);
            if (map == null) {
                map = new ConcurrentHashMap<>(32);
                CACHE_MAP.put(name, map);
                return null;
            }
            if (key != null) {
                byte[] bs = map.get(key);
                if (bs != null && bs.length > 0) {
                    Object obj = serialization().deserialize(bs, cl);
                    return obj;
                }
            }
            return null;
        } finally {
            if (logger.isDebugEnabled()) {
                time.stop();
                logger.debug("获取缓存对象:key={} 耗时:{} ms", key, time.getTime());
            }
        }
    }

    @Override
    public void remove(String name, Object key) {
        Map<Object, byte[]> map = CACHE_MAP.get(name);
        if (map == null) {
            map = new ConcurrentHashMap<>(32);
            CACHE_MAP.put(name, map);
            return;
        }
        if (key != null) {
            map.remove(key);
            // COUNT.addAndGet(-1L);
        }

    }

}
