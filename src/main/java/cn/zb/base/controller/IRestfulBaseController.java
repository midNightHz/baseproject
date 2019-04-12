package cn.zb.base.controller;

import java.io.Serializable;


import cn.zb.base.entity.BaseEntity;
import cn.zb.base.service.BaseService;

/**
 * 
 * @ClassName:  IRestfulBaseController   
 * @Description:restful-api风格的控制层  
 * @author: 陈军
 * @date:   2019年2月19日 上午9:26:24   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public interface IRestfulBaseController<S extends BaseService<T, ID>, T extends BaseEntity<ID>, ID extends Serializable>
        extends IBaseController0<S, T, ID> {
    
    
    
  

}
