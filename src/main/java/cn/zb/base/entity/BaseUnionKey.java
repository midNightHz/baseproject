package cn.zb.base.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import cn.zb.utils.ClassUtils;

/**
 * 
 * @ClassName:  BaseUnionKey   
 * @Description:联合主键   
 * @author: 陈军
 * @date:   2019年1月18日 上午8:33:18   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public interface BaseUnionKey extends Serializable {

    /**
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * 
     * @Title: isContainNull   
     * @Description: 联合主键中是否包含非空字段 
     * @author:陈军
     * @date 2019年3月7日 下午12:44:32 
     * @return      
     * boolean      
     * @throws
     */
    default void isContainNull() throws Exception {
        List<Field> fields = ClassUtils.getFields(this.getClass());
        for (int i = 0; i < fields.size(); i++) {
            Field f = fields.get(i);
            f.setAccessible(true);
            Object obj = f.get(this);
            if (obj == null) {
                throw new Exception(f.getName() + "不能为空");
            }

        }

    }

}
