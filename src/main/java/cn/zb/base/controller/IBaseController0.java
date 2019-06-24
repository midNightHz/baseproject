package cn.zb.base.controller;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.zb.base.annotation.SoftDeleteable;
import cn.zb.base.constants.ThreadLocalParamsName;
import cn.zb.base.entity.BaseEntity;
import cn.zb.base.entity.BaseUnionKey;
import cn.zb.base.entity.EntityUtil;
import cn.zb.base.model.Encryptable;
import cn.zb.base.model.ObjectDifference;
import cn.zb.base.result.ResultStatus;
import cn.zb.base.service.BaseService;
import cn.zb.exception.OperNeedAuditException;
import cn.zb.utils.ClassUtils;
import cn.zb.utils.ThreadLocalUtils;

/**
 * 
 * @ClassName: IBaseController0
 * @Description:controller 基础类的业务实现
 * @author: 陈军
 * @date: 2019年2月19日 上午9:51:34
 * 
 * @param <S>
 * @param <E>
 * @param <ID>
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved.
 *
 */
public interface IBaseController0<S extends BaseService<E, ID>, E extends BaseEntity<ID>, ID extends Serializable>
		extends CommonController {
	/**
	 * 获取控制层对应的service
	 * 
	 * @return
	 */
	S getService();

	/**
	 * 
	 * @Title: findById @Description: 根据id查询，如果id是基本类型 如string
	 *         int等的情况可以直接使用，如果id是一个对象/联合主键时，可以重写该方法，
	 *         将请求方式修改为post，id的注解修改为RequestBody，即可 @PostMapping("findbyid") public
	 *         void findById(HttpServletRequest request, HttpServletResponse
	 *         response, @RequestBody() CorpKey id) {
	 *         BaseController.super.findById(request, response, id);
	 *         } @author:陈军 @date 2019年1月3日 上午10:38:22 @param request @param
	 *         response @param id void @throws
	 */
	default Object findById(HttpServletRequest request, ID id) throws Exception {

		E t = getService().findById(id);
		if (getLogger().isDebugEnabled()) {
			getLogger().debug("result:{}", JSONObject.toJSON(t));
		}

		return toSucessResult(t);
	}

	/**
	 * 
	 * @Title: deleteById
	 * @Description: 删除的控制层业务
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 * @return Object
	 * @author 陈军
	 * @date 2019年6月5日 下午2:31:34
	 */
	@SuppressWarnings("unchecked")
	default Object deleteById(HttpServletRequest request, ID id) throws Exception {
		try {
			CallContext callcontext = getCallContext(request);

			E t = getService().findById(id);
			// 非空校验
			if (t == null) {
				getLogger().error("不存在id为{}的记录", id);
				return toFailResult("记录不存在");
			}
			E target = (E) t.cloneEntity();
			// 校验是否有删除的权限
			if (!getService().checkDeleteAuth(t, callcontext)) {
				getLogger().error("当前用户{}没有删除id为{}的权限", callcontext.getUserName(), id);
				return toFailResult("当前用户没有权限删除");

			}
			// 删除的业务逻辑

			SoftDeleteable softDeleteable = this.getClass().getAnnotation(SoftDeleteable.class);
			// System.out.println(softDeleteable);

			if (softDeleteable == null) {
				// throw new Exception();
				getService().delete(target, callcontext);

			} else {

				Class<?> tc = t.getClass();

				Field f = ClassUtils.getField(tc, softDeleteable.fieldName());

				Object status = t.getField(f);

				int deleteStatus = softDeleteable.deleteStatus();

				if (status != null && status.equals(deleteStatus)) {

					return toFailResult("已废弃的对象不能重复废弃");

				}
				target.setField(f, deleteStatus);

				getService().softDelete(target, t, callcontext);

			}

			return toSucessResult("删除成功");

		} catch (OperNeedAuditException oe) {
			return toResult("操作审核中", ResultStatus.NEEDAUDIT.getCode(), "操作成功，进入审核");
		}
	}

	/**
	 * 
	 * @Title: save
	 * @Description: 保存的主要业务逻辑
	 * @param request
	 * @param e
	 * @return
	 * @throws Exception
	 * @return Object
	 * @author 陈军
	 * @date 2019年6月5日 下午2:31:03
	 */
	default Object save(HttpServletRequest request, E e) throws Exception {
		try {
			CallContext callContext = getCallContext(request);

			if (getLogger().isDebugEnabled()) {
				getLogger().debug("result:{}", JSONObject.toJSON(e));
			}

			if (e == null) {
				getLogger().error("获取参数异常");
				return toFailResult("获取保存参数失败");
			}
			if (e instanceof Encryptable) {
				((Encryptable) e).decryptId();
			}
			ID id = e.getId();

			checkFields(e);

			boolean isUnionKey = false;

			Field idField = EntityUtil.getIdField(getService().entityClass());
			EmbeddedId embeddedId = idField.getAnnotation(EmbeddedId.class);

			Class<?> idClass = idField.getType();
			// 主键是联合主键
			isUnionKey = embeddedId != null || BaseUnionKey.class.isAssignableFrom(idClass);
			E result = null;
			if (id == null) {
				if (isUnionKey) {
					return toFailResult("必须初始化联合主键");
				}
				// 新增是非空字段的校验
				e.notNull();
				// 字段的合规校验
				// 新增权限的校验
				if (!getService().checkInsertAuth(e, callContext)) {

					getLogger().error("当前用户{}没有新增的权限", callContext.getUserName());
					return toFailResult("你没有新增的权限");
				}
				if (getService().checkRepetition(e, callContext)) {
					// getLogger().error("当前用户{}没有新增的权限", callContext.getUserName());
					return toFailResult("不能重复新增");
				}
				// 新增的业务逻辑
				result = getService().insert(e, callContext);
			} else {
				// 字段的合规校验

				E old = getService().findById(id);

				if (old == null) {
					if (isUnionKey) {
						e.notNull();
						// 字段的合规校验

						// 新增权限的校验
						if (!getService().checkInsertAuth(e, callContext)) {
							getLogger().error("当前用户{}没有新增的权限", callContext.getUserName());
							return toFailResult("你没有新增的权限");
						}
						// 新增的业务逻辑
						result = getService().insert(e, callContext);
					}
					// 这里要做下判断 看id的类型是否是 联合主键或者id上有@EmbeddedId注解
					getLogger().error("不存在id为{}的记录", id);
					return toFailResult("不存在id为" + id + "的记录");
				}
				e.cloneFromOtherNotNull(old);

				List<ObjectDifference> diffrence = e.checkDiffFromOther(old);

				if (getLogger().isDebugEnabled()) {
					getLogger().debug("saveentity:{},origin:{},differece:{}", JSONObject.toJSON(e),
							JSONObject.toJSON(old), JSONArray.toJSONString(diffrence));
				}

				/**
				 * 比较字段是否进行了修改
				 */
				if (diffrence.size() == 0) {
					return toFailResult("未进行修改");
				}

				// 修改权限的校验
				if (!getService().checkModifyAuth(e, old, callContext)) {
					getLogger().error("当前用户{}没有修改的权限的权限", callContext.getUserName());
					toFailResult("你没有修改的权限");
				}
				// 修改的业务逻辑
				result = getService().update(e, old, callContext);
			}
			Boolean returnEntity = ThreadLocalUtils.getParam(ThreadLocalParamsName.SAVE_RETURN_ENTITY, Boolean.class);

			if (returnEntity != null && returnEntity) {
				return toSucessResult(result);
			}

			return toSucessResult("保存成功");

		} catch (OperNeedAuditException oe) {
			return toResult("操作审核中", ResultStatus.NEEDAUDIT.getCode(), "操作成功，进入审核");
		}

	}

	/**
	 * 
	 * @Title: checkFields @Description: 用来校验接收参数中某些需要校验的字段是否合规, @author:陈军 @date
	 *         2019年2月18日 上午9:28:17 @param e @throws Exception void @throws
	 */
	default void checkFields(E e) throws Exception {
		e.checkStringReg();
	}

}
