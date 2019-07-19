package cn.zb.base.service;

import java.io.Serializable;

import cn.zb.base.controller.CallContext;
import cn.zb.base.entity.BaseEntity;
import cn.zb.cache.annotation.ZBCacheable;
import cn.zb.operlogger.annotation.OperType;
import cn.zb.operlogger.constants.OperTypeValue;

/**
 * 
 * @title:BaseAuditService.java
 * @ClassName: BaseAuditService
 * @Description:基础表单审核的接口 提供审核的接口
 * @author: 陈军
 * @date: 2019年7月19日 上午10:09:06
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved.
 *
 */
public interface BaseAuditService<T extends BaseEntity<ID>, ID extends Serializable> extends BaseService<T, ID> {

	/**
	 * 
	 * @Title: audit
	 * @Description: 表单审核
	 * @param t
	 *            审核后的表单 修改状态
	 * @param oringe
	 *            原表单
	 * @param callContext
	 * @return
	 * @throws Exception
	 * @return T
	 * @author 陈军
	 * @date 2019年7月19日 上午10:03:03
	 */
	@OperType(type = OperTypeValue.AUDIT)
	@ZBCacheable(key = "#0.id", operType = OperTypeValue.UPDATE)
	default T audit(T t, T oringe, CallContext callContext) throws Exception {
		// TODO
		return null;
	}

	/**
	 * 
	 * @Title: checkAuditAuth
	 * @Description: 表单的审核权限
	 * @param t
	 *            修改后的表单
	 * @param oringe
	 * @param callContext
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author 陈军
	 * @date 2019年7月19日 上午10:04:53
	 */
	default boolean checkAuditAuth(T t, T oringe, CallContext callContext) throws Exception {
		return true;

	}

}
