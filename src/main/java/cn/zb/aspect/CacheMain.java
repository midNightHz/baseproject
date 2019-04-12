package cn.zb.aspect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;

import cn.zb.cache.annotation.ZBCacheConfig;
import cn.zb.cache.annotation.ZBCacheable;
import cn.zb.cache.interfaces.ZBCache;
import cn.zb.cache.interfaces.ZBCacheFactory;
import cn.zb.operlogger.constants.OperTypeValue;

@Aspect
@Order(300)
@Component
public class CacheMain {

    private static Logger logger = LoggerFactory.getLogger(CacheMain.class);

    // 定义切面
    @Pointcut("@annotation(cn.zb.cache.annotation.ZBCacheable)")
    public void excudeService() {

    }

    private static ZBCache cache;

    @Autowired
    private ZBCacheFactory zbCacheFactory;

    private ZBCache getCache() {

        if (cache == null) {
            cache = zbCacheFactory.getZBCache();
        }
        return cache;

    }

    /**
     * 
     * @Title: aroundAdive   
     * @Description: 切面的具体业务逻辑 
     * @author:陈军
     * @date 2019年1月4日 下午4:16:59 
     * @param point
     * @return
     * @throws Throwable      
     * Object      
     * @throws
     */
    @Around("excudeService()")
    public Object aroundAdive(ProceedingJoinPoint point) throws Throwable {

        // 是否需要进行缓存
        Object target = point.getTarget();

        Class<?> targetClass = target.getClass();

        ZBCacheConfig cacheConfig = targetClass.getAnnotation(ZBCacheConfig.class);

        Method m = ((MethodSignature) (point.getSignature())).getMethod();
        Class<?> returnType = m.getReturnType();

        if (logger.isDebugEnabled()) {
            logger.debug("target:{} \n args:{} \n method:{}", target.getClass().getName(),
                    JSONArray.toJSON(point.getArgs()), m.getName());
        }
        if (cacheConfig == null) {
            return point.proceed();
        }
        String name = cacheConfig.nameSpace();

        if (StringUtils.isEmpty(name)) {
            name = targetClass.getName();
        }

        ZBCacheable cacheable = m.getAnnotation(ZBCacheable.class);

        if (cacheable == null) {
            return point.proceed();
        }
        Object id = getKey(cacheable, point);

        OperTypeValue operType = cacheable.operType();
        Object result = null;

        switch (operType) {
        // 修改的情况
        case UPDATE:
            result = point.proceed();
            if (result != null)
                getCache().put(name, id, result);
            return result;
        // 删除的情况
        case DELETE:
            result = point.proceed();
            getCache().remove(name, id);
            return result;
        // 查询的情况
        case SELECT:
            Object obj = getCache().get(name, id, returnType);
            if (obj != null) {
                return obj;
            }
            result = point.proceed();
            if (result != null)
                getCache().put(name, id, result);
            return result;

        default:
            return point.proceed();
        }

    }

    // 获取对象的ID
    private Object getKey(ZBCacheable cacheable, ProceedingJoinPoint point) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object[] args = point.getArgs();
        String key = cacheable.key();
        if (StringUtils.isEmpty(key)) {
            logger.error("获取key异常 key=[" + key + "]");
            return null;
        }
        key = key.replaceAll("#", "");
        if (key.indexOf(".") > 0) {

            int index = 0;
            String fileName = null;
            try {
                index = new Integer(key.substring(0, key.indexOf(".")));

                fileName = key.substring(key.indexOf(".") + 1);
            } catch (Exception e) {
                logger.error("获取key异常 key=[" + key + "]");
                return null;
            }
            Object obj = args[index];

            String methodName = "get" + fileName.toUpperCase().charAt(0) + fileName.substring(1);

            Method method = obj.getClass().getDeclaredMethod(methodName);

            return method.invoke(obj);

        }

        int index = new Integer(key);

        return args[index];

    }

}
