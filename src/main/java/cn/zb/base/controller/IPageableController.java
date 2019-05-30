package cn.zb.base.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;

import cn.zb.base.model.BaseModel;
import cn.zb.base.query.BaseQuery;

/**
 * 
 * @ClassName: IPageableController
 * @Description:分页列表查询的基础公共控制层接口。
 * @author: 陈军
 * @date: 2019年1月30日 下午1:29:49
 * 
 * @param <T>
 * @param <ID>
 * @param <Q>
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved.
 *
 */
public interface IPageableController<T extends BaseModel<ID>, ID extends Serializable, Q extends BaseQuery>
		extends IPageableController0<T, ID, Q> {

	@GetMapping("list")
	default Object entityList(HttpServletRequest request) throws Exception{

		return IPageableController0.super.entityList(request);

	}

}
