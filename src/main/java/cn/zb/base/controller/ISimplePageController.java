package cn.zb.base.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;

import cn.zb.base.entity.BaseEntity;
import cn.zb.base.service.BaseService;
import cn.zb.page.PageData;
import cn.zb.page.Pageable;

/**
 * 一个简单的分页查询结口
 * 
 * @author chen
 *
 */
public interface ISimplePageController<S extends BaseService<E, ID>, E extends BaseEntity<ID>, ID extends Serializable>
		extends BaseController<S, E, ID>, CommonController, PageableController {

	/**
	 * 一个简单的查询列表接口
	 * 
	 * @param request
	 * @param response
	 */
	@GetMapping("simpleList")
	default void getSimpleList(HttpServletRequest request, HttpServletResponse response) {
		try {
			CallContext callContext = getServiceController().getCallContext(request);
			E temp = getServiceController().fromInputParams(request, getService().entityClass());

			Pageable pageable = getPageable(request);
			PageData<E> datas = getService().simpleList(temp, pageable, callContext);

			getServiceController().writeSuccessJsonToClient(response, datas);

		} catch (Exception e) {
			getLogger().error("查询失败：{}", e.getMessage());
			getServiceController().writeFailDataJsonToClient(response, "查询失败");
		}

	}

}
