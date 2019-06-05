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
	 * 
	 * @Title: findById
	 * @Description: 查询
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 * @return
	 * @author 陈军
	 * @date 2019年6月5日 下午2:24:35
	 */
	@GetMapping("findbyid")
	default Object findById(HttpServletRequest request, @RequestParam() ID id) throws Exception {
		return IBaseController0.super.findById(request, id);
	}

	/**
	 * 
	 * @Title: deleteById
	 * @Description: 删除
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 * @return
	 * @author 陈军
	 * @date 2019年6月5日 下午2:24:07
	 */
	@GetMapping("delete")
	default Object deleteById(HttpServletRequest request, @RequestParam() ID id) throws Exception {
		return IBaseController0.super.deleteById(request, id);
	}

	/**
	 * 
	 * @Title: save
	 * @Description: 保存
	 * @param request
	 * @throws Exception
	 * @return void
	 * @author 陈军
	 * @date 2019年6月5日 下午2:23:44
	 */
	@SuppressWarnings("unchecked")
	@CrossOrigin()
	@PostMapping("save")
	default void save(HttpServletRequest request) throws Exception {
		Class<?> entityClass = getService().entityClass();
		E e = (E) fromInputJson(request, entityClass);
		save(request, e);
	}

	/**
	 * 
	 * @Title: saveGet
	 * @Description: 保存
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return void
	 * @author 陈军
	 * @date 2019年6月5日 下午2:23:27
	 */

	@SuppressWarnings("unchecked")
	@GetMapping("save")
	default void saveGet(HttpServletRequest request, HttpServletResponse response/* , E e */) throws Exception {
		Class<?> entityClass = getService().entityClass();
		E e = (E) fromInputParams(request, entityClass);
		save(request, e);

	}

}
