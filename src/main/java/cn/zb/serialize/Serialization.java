package cn.zb.serialize;

/**
 * 
 * @ClassName:  Serialization   
 * @Description:序列化器   
 * @author: 陈军
 * @date:   2019年2月21日 上午9:27:14   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public interface Serialization {

    /**
     * 
     * @Title: serialize   
     * @Description: 序列化工具 
     * @author:陈军
     * @date 2019年2月21日 上午9:32:36 
     * @param t
     * @return
     * @throws Exception      
     * byte[]      
     * @throws
     */
    byte[] serialize(Object t);

    /**
     * 
     * @Title: deserialize   
     * @Description: 反序列化 
     * @author:陈军
     * @date 2019年2月22日 下午4:06:45 
     * @param bytes
     * @param cl
     * @return      
     * T      
     * @throws
     */
    <T> T deserialize(byte[] bytes, Class<T> cl);

}
