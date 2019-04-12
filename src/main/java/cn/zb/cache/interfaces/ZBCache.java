package cn.zb.cache.interfaces;

public interface ZBCache {
    /**
     * 
     * @Title: get   
     * @Description: 从缓存中查询  
     * @author:陈军
     * @date 2019年1月23日 下午1:27:50 
     * @param name
     * @param key      
     * void      
     * @throws
     */
    Object get(String name, Object key, Class<?> cl);

    /**
     * 
     * @Title: put   
     * @Description: 添加到缓存  
     * @author:陈军
     * @date 2019年1月23日 下午1:28:06 
     * @param name
     * @param key
     * @param value      
     * void      
     * @throws
     */

    void put(String name, Object key, Object value);

    void remove(String name, Object key);

    void clean();

}
