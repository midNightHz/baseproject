package cn.zb.base.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

import cn.zb.base.model.BaseModel;
import cn.zb.base.query.BaseQuery;
import cn.zb.base.result.JsonResult;
import cn.zb.base.service.PageableService;
import cn.zb.page.PageData;
import cn.zb.page.Pageable;

public interface IPageableController0<T extends BaseModel<ID>, ID extends Serializable, Q extends BaseQuery>
		extends PageableController, CommonController {

	PageableService<T, ID, Q> getPageableService();

	/**
	 * 
	 * @Title: entityList @Description: 列表查询接口的通用方法。 @author:陈军 @date 2019年1月23日
	 *         上午8:44:57 @param request @param response @param q void @throws
	 */
	default Object entityList(HttpServletRequest request) throws Exception {

		Q q = getQuery(request);

		return entityList(request, q);

	}

	/**
	 * 
	 * @Title: entityList @Description: TODO(这里用一句话描述这个方法的作用) @author:陈军 @date
	 *         2019年1月30日 下午1:13:14 @param request @param response @param q
	 *         void @throws
	 */
	default JsonResult<?> entityList(HttpServletRequest request, Q q) throws Exception {

		CallContext callContext = getCallContext(request);

		Pageable pageable = this.getPageable(request);
		if (getLogger().isDebugEnabled()) {
			getLogger().debug("query:" + JSONObject.toJSONString(q));
			getLogger().debug("pageable:" + JSONObject.toJSONString(pageable));
		}
		PageData<T> datas = getPageableService().getPageData(q, pageable, callContext);

		return toSucessResult(datas);

	}

	/**
	 * 
	 * @Title: getQuery @Description: 获取查询对象 默认的实现方式是从请求参数中获取查询对象 @author:陈军 @date
	 *         2019年1月30日 上午10:51:48 @param request @return Q @throws
	 */

	@SuppressWarnings("unchecked")
	default Q getQuery(HttpServletRequest request) throws Exception {

		Class<?> queryClass = getPageableService().queryClass();
		if (queryClass == null) {
			throw new Exception("系统异常");
		}

		Q q = (Q) fromInputParams(request, queryClass);
		if (q == null) {
			return (Q) queryClass.newInstance();
		}
		return q;
	}

}
