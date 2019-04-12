package cn.zb.base.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * @ClassName:  SoftDeleteable   
 * @Description:添加实体类服务层上及删除方法上，在删除操作是允许进行软删除   
 * @author: 陈军
 * @date:   2019年1月8日 上午8:34:48   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RUNTIME)
public @interface SoftDeleteable {
    /**
     * 
     * @Title: value   
     * @Description: 操作实体类的类名  
     * @author:陈军
     * @date 2019年1月10日 下午2:33:10 
     * @return      
     * String      
     * @throws
     */
    String value();

    /**
     * 
     * @Title: fieldName   
     * @Description: 软删除状态的字段名 
     * @author:陈军
     * @date 2019年1月10日 下午2:33:49 
     * @return      
     * String      
     * @throws
     */
    String fieldName();

    /**
     * 
     * @Title: softDelete   
     * @Description: 是否允许进行软删除
     * @author:陈军
     * @date 2019年1月8日 上午8:37:49 
     * @return      
     * boolean      
     * @throws
     */
    boolean softDelete() default true;

    /**
     * 
     * @Title: softDeleteParam   
     * @Description: 数据库中配置是否允许逻辑删除 
     * @author:陈军
     * @date 2019年1月14日 上午11:23:11 
     * @return      
     * int      
     * @throws
     */
    int softDeleteParam() default 0;

    /**
     * 
     * @Title: delsteStatus   
     * @Description: 删除状态  
     * @author:陈军
     * @date 2019年1月8日 上午8:37:31 
     * @return      
     * int      
     * @throws
     */
    int deleteStatus();

}
