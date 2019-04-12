package cn.zb.base.controller;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.zb.base.entity.BaseEntity;
import cn.zb.base.service.BaseService;

/**
 * 
 * 基础的控制层，可以实现一部分接口 实现该接口的实体类应该继承与serviceController,不然会出现意料之外的事情
 * 
 * @author chen
 *
 * @param <S>
 * @param <E>
 * @param <ID>
 */

public interface BaseController<S extends BaseService<E, ID>, E extends BaseEntity<ID>, ID extends Serializable>
		extends IBaseController0<S, E, ID> {

	/**
	 * 获取控制层对应的service
	 * 
	 * @return E S getService();
	 * 
	 * /**
	 * 
	 * @Title: findById @Description: 根据id查询，如果id是基本类型 如string
	 * int等的情况可以直接使用，如果id是一个对象/联合主键时，可以重写该方法，
	 * 将请求方式修改为post，id的注解修改为RequestBody，即可 @PostMapping("findbyid") public void
	 * findById(HttpServletRequest request, HttpServletResponse
	 * response, @RequestBody() CorpKey id) { BaseController.super.findById(request,
	 * response, id); } @author:陈军 @date 2019年1月3日 上午10:38:22 @param request @param
	 * response @param id void @throws
	 */
	@GetMapping("findbyid")
	default void findById(HttpServletRequest request, HttpServletResponse response, @RequestParam() ID id) {
		IBaseController0.super.findById(request, response, id);
	}

	/**
	 * 删除某一条记录,可以通过修改service层里的checkDeleteAuth方法来确认当前用户是否有删除该记录的权限,
	 * 如果删除的逻辑为软删除,可以重写delete方法进行逻辑删除操作,ID不是基本对象，为自定义对象的情况(联合主键的情况)下可以参考findById
	 * 
	 * @param request
	 * @param response
	 * @param id
	 */
	@GetMapping("delete")
	default void deleteById(HttpServletRequest request, HttpServletResponse response, @RequestParam() ID id) {
		IBaseController0.super.deleteById(request, response, id);
	}

	/**
	 * 
	 * @Title: save @Description: 单条记录保存 @author:陈军 @date 2019年1月3日 上午9:27:53 @param
	 * request @param response @param e void @throws
	 */
	@SuppressWarnings("unchecked")
	@CrossOrigin()
	@PostMapping("save")
	default void save(HttpServletRequest request, HttpServletResponse response/* , @RequestBody() E e */) {
		Class<?> entityClass = getService().entityClass();
		E e = (E) getServiceController().fromInputJson(request, entityClass);
		IBaseController0.super.save(request, response, e);
	}

	/**
	 * 
	 * @Title: saveGet @Description: 单条记录保存--get形式 @author:陈军 @date 2019年1月11日
	 * 上午10:26:53 @param request @param response @param e void @throws
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("save")
	default void saveGet(HttpServletRequest request, HttpServletResponse response/* , E e */) {
		Class<?> entityClass = getService().entityClass();
		E e = (E) getServiceController().fromInputParams(request, entityClass);
		save(request, response, e);

	}

}
