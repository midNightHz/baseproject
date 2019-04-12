package cn.zb.serialize;

/**
 * 
 * @ClassName:  SerializationFactory   
 * @Description:序列化工具建造工厂   
 * @author: 陈军
 * @date:   2019年2月21日 下午2:11:33   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public interface SerializationFactory {

    /**
     * 
     * @Title: getSerialization   
     * @Description: 获取序列化工具  
     * @author:陈军
     * @date 2019年2月22日 下午4:07:07 
     * @return      
     * Serialization      
     * @throws
     */
    Serialization getSerialization();

    <T extends Serialization> T getSerialization(Class<T> cl);

}
