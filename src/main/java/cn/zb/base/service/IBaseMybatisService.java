package cn.zb.base.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.zb.base.model.BaseModel;
import cn.zb.base.query.BaseQuery;
import cn.zb.base.repository.BaseMybatisRepository;

/**
 * 
 * @ClassName:  IBaseMybatisService   
 * @Description:   
 * @author: 陈军
 * @date:   2019年1月22日 上午10:44:02   
 *   
 * @param <T>
 * @param <ID>
 * @param <Q>  
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public interface IBaseMybatisService<T extends BaseModel<ID>, ID extends Serializable, Q extends BaseQuery> {

    BaseMybatisRepository<T, ID, Q> getBaseMybatisRepository();

    /**
     * 
     * @Title: modelClass   
     * @Description: 获取model的类型  
     * @author:陈军
     * @date 2019年1月22日 上午10:45:11 
     * @return      
     * Class<?>      
     * @throws
     */
    default Class<?> modelClass() {
        Class<?>[] types = this.getClass().getInterfaces();
        Type[] interfaces = types[0].getGenericInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            Type interfacec = interfaces[i];
            Type[] ts = ((ParameterizedType) interfacec).getActualTypeArguments();
            for (int j = 0; j < ts.length; j++) {

                if (ts[j].getClass().equals(Class.class)) {
                    Class<?> objClass = (Class<?>) ts[j];
                    // 因为basemodel是继承与baseEntity的，所以要将这两者区分开
                    if ((BaseModel.class.isAssignableFrom(objClass)))
                        return objClass;
                }
            }
        }
        return null;
    }

    /**
     * 
     * @Title: queryClass   
     * @Description: 获取query对象 
     * @author:陈军
     * @date 2019年1月30日 下午1:06:40 
     * @return      
     * Class<?>      
     * @throws
     */
    default Class<?> queryClass() {
        Class<?>[] types = this.getClass().getInterfaces();
        Type[] interfaces = types[0].getGenericInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            Type interfacec = interfaces[i];
            
            Type[] ts = ((ParameterizedType) interfacec).getActualTypeArguments();
            
            for (int j = 0; j < ts.length; j++) {

                if (ts[j].getClass().equals(Class.class)) {
                    Class<?> objClass = (Class<?>) ts[j];
                    if ((BaseQuery.class.isAssignableFrom(objClass)))
                        return objClass;
                }
            }
        }
        return null;
    }

}
