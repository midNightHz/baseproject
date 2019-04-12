package cn.zb.cache.impl;



/**
 * 
 * @ClassName:  ZBRedisCacheImpl   
 * @Description:redis 缓存的实现  
 * @author: 陈军
 * @date:   2019年1月25日 下午5:39:00   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
//@Component("redisCache")
public class ZBRedisCacheImpl extends AbstractCacheImpl {

    //private static Logger logger = LoggerFactory.getLogger(ZBRedisCacheImpl.class);

    //@Autowired
    //private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Object get(String name, Object key, Class<?> cl) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void put(String name, Object key, Object value) {
        // TODO Auto-generated method stub

    }

    @Override
    public void remove(String name, Object key) {
        // TODO Auto-generated method stub

    }

    @Override
    public void clean() {

    }


}
