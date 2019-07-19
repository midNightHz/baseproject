package cn.zb.operlogger.Interface;

import java.util.Date;
import java.util.List;

import cn.zb.base.controller.CallContext;
import cn.zb.base.entity.BaseEntity;
import cn.zb.base.entity.EntityUtil;
import cn.zb.base.service.BaseService;
import cn.zb.operlogger.constants.LogAuditConstants;
import cn.zb.operlogger.constants.OperTypeValue;
import cn.zb.operlogger.entity.BaseOperLogger;

/**
 * 
 * @ClassName: IBaseLogerService
 * @Description:日志基础服务层
 * @author: 陈军
 * @date: 2019年1月7日 下午1:33:54
 * 
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved.
 *
 */
public interface IBaseLogerService<T extends BaseOperLogger> extends BaseService<T, Integer> {

	/**
	 * 
	 * @Title: saveOperLogger
	 * @Description: 保存日志
	 * @param target
	 *            修改后的表单
	 * @param source
	 *            库中的表单
	 * @param operType
	 *            操作类型
	 * @param callContext
	 * @param logDesc
	 *            日志描述
	 * @param auditLog
	 *            日志是否需要审核
	 * @return
	 * @throws Exception
	 * @return BaseOperLogger
	 * @author 陈军
	 * @date 2019年7月19日 上午10:16:21
	 */
	default BaseOperLogger saveOperLogger(BaseEntity<?> target, BaseEntity<?> source, OperTypeValue operType,
			CallContext callContext, String logDesc, boolean auditLog) throws Exception {
		T log = createLog(target, source, operType, callContext, logDesc);
		if (auditLog) {
			log.setStatus(LogAuditConstants.INIT);
		} else {
			log.setStatus(LogAuditConstants.AUDITED);
		}
		return insert(log, callContext);

	}

	/**
	 * 
	 * @Title: createLog
	 * @Description: 初始化操作日志实体类
	 * @param entity
	 * @param source
	 * @param operType
	 * @param callContext
	 * @param logDesc
	 * @return
	 * @throws Exception
	 * @return T
	 * @author 陈军
	 * @date 2019年7月19日 上午10:17:17
	 */
	default T createLog(BaseEntity<?> entity, BaseEntity<?> source, OperTypeValue operType, CallContext callContext,
			String logDesc) throws Exception {
		Class<?> Tclass = entityClass();
		@SuppressWarnings("unchecked")
		T log = (T) (Tclass.newInstance());
		// 将原来的数据拷贝到日志中
		if (operType == OperTypeValue.DELETE) {
			log.cloneOtherToThis(source);
		}
		log.cloneOtherToThis(entity);
		// 基础的信息
		log.setOpertype(operType.getOperType());
		log.setLogdescription(logDesc);
		log.setOpertime(new Date());
		return log;
	}


	/**
	 * 
	 * @Title: auditLog @Description: 日志审核 修改订单的审核状态 @author:陈军 @date 2019年1月7日
	 *         下午1:34:29 @param loggerid 日志id @param status 审核后状态 @param
	 *         callcontext @throws Exception void @throws
	 */
	default void auditLog(Integer loggerid, Integer status, CallContext callcontext) throws Exception {
		T t = findById(loggerid);
		if (t == null) {
			throw new Exception("审核失败");
		}
		t.setStatus(status);
		getJpaRepository().save(t);

	}

	/**
	 * 
	 * @Title: auditOperLog @Description: 日志审核。该方法不提供任何具体的业务，仅仅是为了日志审核切面能正常切入到该方法。
	 *         如果需要继续执行其他业务，可以重写该方法,但是切记不要写业务逻辑,否则可能会抛出异常 @author:陈军 @date 2019年1月9日
	 *         下午12:41:01 @param t @param callContext @throws Exception void @throws
	 */
	default void auditOperLog(T t, Integer status, CallContext callContext) throws Exception {
		throw new AbstractMethodError();
	}

	/**
	 * 
	 * @Title: auditOperLogAuth @Description: 是否有审核当前日志的权限 @author:陈军 @date
	 *         2019年1月9日 下午12:41:22 @param t @param callContext @return @throws
	 *         Exception boolean @throws
	 */
	boolean auditOperLogAuth(T t, CallContext callContext) throws Exception;

	/**
	 * 
	 * @Title: findbyObjectId @Description: 查询日志是否有未审/已审日志存在 @author:陈军 @date
	 *         2019年1月7日 下午1:34:53 @param id @param status @return
	 *         BaseOperLogger @throws
	 */
	List<? extends BaseOperLogger> findbyObjectId(String id, Integer status);

	/**
	 * 
	 * @Title: getOperTargetTableName @Description: 审核目标表的表名 @author:陈军 @date
	 *         2019年1月9日 下午12:55:47 @return String @throws
	 */
	default String getOperTargetTableName() {
		return EntityUtil.getTableName(entityClass());
	}

	// 日志是否需要审核
	boolean shouldAudit();

	/**
	 * 
	 * <p>
	 * Title: checkDeleteAuth
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 日志不允许删除
	 * 
	 * @param t
	 * @param callContext
	 * @return
	 * @throws Exception
	 * @see cn.zb.base.service.BaseService#checkDeleteAuth(cn.zb.base.entity.BaseEntity,
	 *      cn.zb.base.controller.CallContext)
	 */
	@Override
	default boolean checkDeleteAuth(T t, CallContext callContext) throws Exception {
		throw new Exception("日志不允许删除");
	}

	@Override
	default boolean checkModifyAuth(T t, T origin, CallContext callContext) throws Exception {
		throw new Exception("日志不允许修改");
	}

	@Override
	default void initInsert(T t, CallContext callContext) throws Exception {

		if (t.getOpertype() == OperTypeValue.INSERT.getOperType()) {
			t.setStatus(LogAuditConstants.AUDITED);
		}

		t.setOpertime(new Date());
		t.setOper(callContext.getUserId());
	}

}
