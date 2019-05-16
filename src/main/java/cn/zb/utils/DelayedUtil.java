package cn.zb.utils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @ClassName:  DelayedUtil   
 * @Description:用于缓存对象   
 * @author: 陈军
 * @date:   2019年3月19日 下午3:56:22   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public class DelayedUtil {
    
    
    private static final Logger logger = LoggerFactory.getLogger(DelayedUtil.class);

    
    
    
    private final static DelayQueue<DelayedKey> DELAYED_MAIN = new DelayQueue<>();

    private final static Map<String, DelayedKey> DELAYED_KEY_MAP = new ConcurrentHashMap<>();

    /**
     * 默认保存时间
     */
    private final static long DEFAULT_TIME = 30 * 60;
    /**
     * 默认保存时间间隔
     */
    private final static TimeUnit DEFAULT_UNIT = TimeUnit.SECONDS;

    public static String put(Object obj) {

        String id = UUID.randomUUID().toString().toUpperCase();

        return put(id, obj, DEFAULT_TIME, DEFAULT_UNIT);

    }

    public static String put(Object obj, Long time) {
        String id = UUID.randomUUID().toString().toUpperCase();

        return put(id, obj, time, DEFAULT_UNIT);
    }

    public static String put(String id, Object obj, Long time, TimeUnit unit) {
        if (obj == null) {
            return null;
        }
        if (StringUtils.isBlank(id)) {
            id = UUID.randomUUID().toString().toUpperCase();
        }
        if (time == null || unit == null) {
            time = DEFAULT_TIME;
            unit = DEFAULT_UNIT;
        }
        long liveTime= TimeUnit.MILLISECONDS.convert(time, unit);
        
        DelayedKey delayedKey = new DelayedKey(obj, liveTime);

        delayedKey.setId(id);
        DELAYED_MAIN.offer(delayedKey);
        DELAYED_KEY_MAP.put(id, delayedKey);
        if(logger.isDebugEnabled()) {
            logger.debug("容器中内容：{}",JSONObject.toJSON(DELAYED_MAIN));
        }
        return id;

    }

    /**
     * 
     * @Title: flush   
     * @Description: 更新缓存时间
     * @author:陈军
     * @date 2019年3月19日 下午4:08:40 
     * @param id      
     * void      
     * @throws
     */
    public static void flush(String id) {
        DelayedKey delayedKey = DELAYED_KEY_MAP.get(id);
        if (delayedKey == null) {
            return;
        }
        if (delayedKey.getLiveTime() < System.currentTimeMillis()) {
            return;
        }
        delayedKey.setMoveTime(System.currentTimeMillis() + delayedKey.getLiveTime());
    }

    /**
     * 
     * @Title: get   
     * @Description: 取出保存的对象 
     * @author:陈军
     * @date 2019年3月19日 下午4:08:58 
     * @param id      
     * void      
     * @throws
     */
    public static Object get(String id) {

        DelayedKey delayedKey = DELAYED_KEY_MAP.get(id);
        if (delayedKey == null) {
            return null;
        }
        //flush(id);
        return delayedKey.getKey();

    }

    static {

        Runnable r = new Runnable() {

            @Override
            public void run() {

                while (true) {

                    try {
                        Thread.sleep(10);
                        DelayedKey delayedKey = DELAYED_MAIN.poll();
                        if (delayedKey == null) {
                            continue;
                        }
                        String id = delayedKey.getId();
                        DELAYED_KEY_MAP.remove(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        };
        ThreadFactory.excute(r);

    }

    public static void main(String[] args) {

        Integer a = 1000;

        String id = put(a);

        Runnable r = new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        String s = DateUtil.formartDate(new java.util.Date(), "yyyy-MM-dd HH:mm:ss");
                        System.out.println(get(id) + "----" + s);
                        Thread.sleep(1000*60*2);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        ThreadFactory.excute(r);

    }
    
   

}
