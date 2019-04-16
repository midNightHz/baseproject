package cn.zb.base.repository;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.zb.base.model.BaseModel;
import cn.zb.base.query.BaseQuery;

/**
 * 
 * @ClassName:  BaseMybatisRepository   
 * @Description:基于mybatis的基础持久层  
 * @author: 陈军
 * @date:   2019年1月3日 下午1:01:56   
 *   
 * @param <T>
 * @param <ID>  
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public interface BaseMybatisRepository<T extends BaseModel<ID>, ID extends Serializable, Q extends BaseQuery> {
    /**
     * 
     * @Title: findById   
     * @Description: 单条记录查询  
     * @author:陈军
     * @date 2019年1月22日 上午10:47:19 
     * @param id
     * @return      
     * T      
     * @throws
     */
    T findById(@Param("id") ID id);

    /**
     * 
     * @Title: insert   
     * @Description: 新增  
     * @author:陈军
     * @date 2019年1月22日 上午10:47:36 
     * @param t      
     * void      
     * @throws
     */
    void insert(T t);

    /**
     * 
     * @Title: update   
     * @Description: 修改  
     * @author:陈军
     * @date 2019年1月22日 上午10:47:47 
     * @param t      
     * void      
     * @throws
     */
    void update(T t);

    /**
     * 
     * @Title: delete   
     * @Description: 删除 
     * @author:陈军
     * @date 2019年1月22日 上午10:47:58 
     * @param t      
     * void      
     * @throws
     */
    void delete(T t);

    /**
     * 
     * @Title: count   
     * @Description: 计数  
     * @author:陈军
     * @date 2019年1月22日 上午10:48:09 
     * @param query
     * @return      
     * Long      
     * @throws
     */
    Long count(Q query);

    /**
     * 
     * @Title: findAll   
     * @Description: 查询所有  
     * @author:陈军
     * @date 2019年1月22日 上午10:48:26 
     * @param query
     * @return      
     * List<T>      
     * @throws
     */
    List<T> findAll(Q query);

    /**
     * 
     * @Title: deleteByIds   
     * @Description: 批量删除  
     * @author:陈军
     * @date 2019年1月22日 上午10:48:44 
     * @param ids      
     * void      
     * @throws
     */

    void deleteByIds(List<ID> ids);

}
