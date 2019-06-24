package cn.zb.base.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.zb.base.controller.CallContext;
import cn.zb.base.entity.BaseEntity;
import cn.zb.base.model.BaseModel;
import cn.zb.base.repository.BaseJpaRepository;
import cn.zb.cache.annotation.ZBCacheable;
import cn.zb.operlogger.annotation.OperType;
import cn.zb.operlogger.constants.*;
import cn.zb.page.PageData;
import cn.zb.page.PageDataImpl;
import cn.zb.page.Pageable;
import cn.zb.utils.Assert;

/**
 * 基础业务层，实现基本的功能,如果业务层接口集成该接口，则需要继承该接口的抽象实现类或者重写方法
 * 
 * @author chen
 *
 */
public interface BaseService<T extends BaseEntity<ID>, ID extends Serializable> {

	/**
	 * 日志
	 */
	Logger logger = LoggerFactory.getLogger(BaseService.class);

	default Logger getLogger() {
		return logger;
	}

	BaseJpaRepository<T, ID> getJpaRepository();

	/**
	 * 实体类的保存，如果该实体类存在
	 * 
	 * @param t
	 * @throws Exception
	 */
	default T save(T t, CallContext callContext) throws Exception {
		if (t == null) {
			throw new Exception("表单不存在");
		}

		ID id = t.getId();

		if (id != null) {
			T source = findById(id);
			t.cloneFromOtherNotNull(source);
			return update(t, source, callContext);
		}

		return insert(t, callContext);
	}

	/**
	 * 
	 * @Title: saveBatch @Description: 批量保存 @author:陈军 @date 2019年2月18日
	 *         下午1:40:33 @param list @param callContext @return @throws Exception
	 *         List<T> @throws
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	default List<T> saveBatch(List<T> list, CallContext callContext) throws Exception {
		List<T> result = new ArrayList<>();
		for (T t : list) {
			result.add(save(t, callContext));
		}
		return result;
	}

	/**
	 * 实体类的新增
	 * 
	 * @param t
	 * @throws Exception
	 */
	@OperType(type = OperTypeValue.INSERT)
	default T insert(T t, CallContext callContext) throws Exception {
		initInsert(t, callContext);
		// ID 初始化
		t.defaultValue();
		if (getLogger().isDebugEnabled()) {
			getLogger().debug("保存的数据：{}", JSONObject.toJSON(t));
		}
		return getJpaRepository().save(t);

	}

	/**
	 * 修改的初始化数据的业务逻辑放这里
	 * 
	 * @param t
	 * @param callContext
	 * @throws Exception
	 */
	default void initUpdate(T t, CallContext callContext) throws Exception {

	}

	/**
	 * 保存时的初始化业务逻辑放这里
	 * 
	 * @param t
	 * @param callContext
	 * @throws Exception
	 */
	default void initInsert(T t, CallContext callContext) throws Exception {

	}

	/**
	 * 校验当前用户是否有删除当前实例的权限，返回true为有权限删除，如果为false则无权限删除,可以通过重写该方法来修改删除权限
	 * 
	 * @param t
	 * @param callContext
	 * @return
	 * @throws Exception
	 */
	default boolean checkDeleteAuth(T t, CallContext callContext) throws Exception {

		return true;
	}

	/**
	 * 
	 * @Title: checkModifyAuth
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param t
	 *            修改后的数据
	 * @param origin
	 *            源数据
	 * @param callContext
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @author 陈军
	 * @date 2019年6月24日 下午3:46:01
	 */
	default boolean checkModifyAuth(T t, T origin, CallContext callContext) throws Exception {
		return true;
	}

	/**
	 * 插入权限,默认为true,可以通过重写此方法修改插入权限判断
	 * 
	 * @param callContext
	 * @return
	 * @throws Exception
	 */
	default boolean checkInsertAuth(T t, CallContext callContext) throws Exception {
		return true;
	}

	/**
	 * 
	 * @Title: update @Description: 数据库的修改业务 @author:陈军 @date 2019年1月4日
	 *         下午4:25:21 @param t 修改前的对象 @param oringe 修改后的目标对象 @param
	 *         callContext @return @throws Exception T @throws
	 */
	@OperType(type = OperTypeValue.UPDATE)
	@ZBCacheable(key = "#0.id", operType = OperTypeValue.UPDATE)
	default T update(T t, T oringe, CallContext callContext) throws Exception {
		// 初始化默认的修改字段
		initUpdate(t, callContext);

		if (getLogger().isDebugEnabled()) {
			getLogger().debug("保存的数据：{}", JSONObject.toJSON(t));
			getLogger().debug("源数据：{}", JSONObject.toJSON(oringe));
		}
		return getJpaRepository().save(t);

	}

	@OperType(type = OperTypeValue.SOFTDELETE)
	@ZBCacheable(key = "#0.id", operType = OperTypeValue.UPDATE)
	default T softDelete(T t, T oringe, CallContext callContext) throws Exception {
		return update(t, oringe, callContext);
	}

	/**
	 * 判断当前实体类是否存在
	 * 
	 * @param t
	 * @return
	 * @throws Exception
	 */
	default Boolean exist(T t) throws Exception {
		if (t == null) {
			return false;
		}
		return getJpaRepository().findOne(t.getId()) != null;
	}

	/**
	 * 判断当前实体类是否存在
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	default Boolean exist(ID id) throws Exception {
		return getJpaRepository().findOne(id) != null;
	}

	/**
	 * 根据id查询实体类
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * 
	 */
	@ZBCacheable(key = "#0", operType = OperTypeValue.SELECT)
	default T findById(ID id) throws Exception {
		return getJpaRepository().findOne(id);
	}

	/**
	 * 
	 * @Title: entityClass @Description: 获取当前服务层实体类的类型
	 *         ,获取该实体类的类型可能会消耗0.1ms左右的时间 @author:陈军 @date 2019年1月22日
	 *         上午8:53:00 @return Class<?> @throws
	 */
	@SuppressWarnings("unchecked")
	default Class<T> entityClass() {
		Class<?>[] types = this.getClass().getInterfaces();
		// System.out.println(Arrays.toString(types));
		Type[] interfaces = types[0].getGenericInterfaces();

		for (int i = 0; i < interfaces.length; i++) {
			Type interfacec = interfaces[i];
			Type[] ts = ((ParameterizedType) interfacec).getActualTypeArguments();
			for (int j = 0; j < ts.length; j++) {

				if (ts[j].getClass().equals(Class.class)) {
					Class<?> objClass = (Class<?>) ts[j];
					// 因为basemodel是继承与baseEntity的，所以要将这两者区分开
					if (BaseEntity.class.isAssignableFrom(objClass) && (!BaseModel.class.isAssignableFrom(objClass)))
						return (Class<T>) objClass;
				}

			}

		}
		return null;

	}

	/**
	 * 实体对象的批量插入
	 * 
	 * @param list
	 * @throws Exception
	 */

	default List<T> insertBatch(List<T> list, CallContext callContext) throws Exception {
		for (T t : list) {
			initInsert(t, callContext);
		}
		return getJpaRepository().save(list);
	}

	/**
	 * 删除单条记录
	 * 
	 * @param id
	 * @throws Exception
	 */

	default void delete(ID id, CallContext callContext) throws Exception {
		T t = findById(id);
		if (t == null) {
			return;
		}
		delete(t, callContext);
	}

	/**
	 * 
	 * @Title: flushDelete @Description: 刷新删除状态 @author:陈军 @date 2019年1月7日
	 *         下午1:51:22 @param id @param callContext @throws Exception void @throws
	 */
	@ZBCacheable(key = "#0.id", operType = OperTypeValue.DELETE)
	default void flushDelete(BaseEntity<ID> entity, CallContext callContext) throws Exception {

		T t = findById(entity.getId());
		if (t == null) {
			throw new Exception("ID错误");
		}
		getJpaRepository().delete(t);
	}

	/**
	 * 
	 * @Title: flushSave @Description: 刷新并保存--不会触发保存的切面 @author:陈军 @date 2019年1月8日
	 *         上午11:01:12 @param t @param callContext @return @throws Exception
	 *         T @throws
	 */
	@SuppressWarnings("unchecked")
	@ZBCacheable(key = "#0.id", operType = OperTypeValue.UPDATE)
	default T flushSave(BaseEntity<ID> baseEntity, CallContext callContext) throws Exception {
		ID id = baseEntity.getId();
		if (id == null) {
			throw new Exception("错误的id");
		}
		T o = findById(id);
		if (o == null) {
			throw new Exception("查询不到ID为" + id + "的记录");
		}
		baseEntity.cloneFromOtherNotNull(o);

		initUpdate((T) baseEntity, callContext);
		return getJpaRepository().save((T) baseEntity);

	}

	/**
	 * 删除单条记录
	 * 
	 * @param t
	 * @throws Exception
	 */
	@OperType(type = OperTypeValue.DELETE)
	@ZBCacheable(key = "#0.id", operType = OperTypeValue.DELETE)
	default void delete(T t, CallContext callContext) throws Exception {
		getJpaRepository().delete(t);
	}

	/**
	 * 批量删除
	 * 
	 * @param list
	 * @throws Exception
	 */
	default void deleteBatch(List<T> list, CallContext callContext) throws Exception {
		for (T t : list) {
			delete(t, callContext);
		}
	}

	/**
	 * 
	 * @Title: findRepetitionEntity @Description: 查询重复的对象 @author:陈军 @date
	 *         2019年4月21日 上午8:32:20 @param entity 当前的实例 @param fields 查询的字段名 @return
	 *         T @throws
	 */
	@SuppressWarnings("unchecked")
	default List<T> findRepetitionEntity(T entity, String... fields) throws Exception {
		if (entity == null) {
			return new ArrayList<>();
		}
		T exampleEntity = null;
		if (fields == null || fields.length == 0) {
			exampleEntity = entity;
		} else {

			exampleEntity = (T) entity.cloneEntity(fields);
		}

		if (getLogger().isDebugEnabled()) {
			getLogger().debug("example:{},fields:{},searchEntity:{}", JSONObject.toJSON(exampleEntity),
					JSONObject.toJSON(fields), JSONObject.toJSON(entity));
		}
		Example<T> example = Example.of(exampleEntity);

		return getJpaRepository().findAll(example);
	}

	/**
	 * 基于JPA来实现的简单分页查询
	 * 
	 * @param temp
	 * @param pageable
	 * @return
	 * @throws Exception
	 */
	default PageData<T> simpleList(T temp, Pageable pageable, CallContext callContext) throws Exception {
		Example<T> example = Example.of(temp);
		org.springframework.data.domain.Pageable page = new org.springframework.data.domain.PageRequest(
				pageable.getPageNo(), pageable.getPageSize());
		Page<T> pages = getJpaRepository().findAll(example, page);

		List<T> list = pages.getContent();
		long count = pages.getTotalElements();
		pageable.setTotalCount(count);
		return new PageDataImpl<>(list, pageable);
	}

	default T findByExample(T entity, String... fields) throws Exception {
		List<T> list = findRepetitionEntity(entity, fields);

		if (list == null || list.size() == 0) {
			return null;

		}
		if (list.size() == 1) {
			return list.get(0);
		}
		throw new Exception("查询多条记录");

	}

	default List<T> findByExample(Map<String, Object> params) {

		Assert.isNull(params, "查询参数不能为空");

		T t = JSON.parseObject(JSONObject.toJSONString(params), entityClass());

		Example<T> example = Example.of(t);
		return getJpaRepository().findAll(example);

	}

	/**
	 * 
	 * @Title: checkRepetition @Description: 新增是排重， @author:陈军 @date 2019年5月15日
	 *         上午8:52:19 @param t//需要新增的实体类 @param callContex @return @throws
	 *         Exception boolean ture 有重复 false 无重复 @throws
	 */
	default boolean checkRepetition(T t, CallContext callContext) throws Exception {
		return false;

	}

	@SuppressWarnings("unchecked")
	default boolean exist(T entity, String... fields) throws Exception {
		Assert.isNull(entity, "模板不能为空");
		T exampleEntity = null;
		if (fields == null || fields.length == 0) {
			exampleEntity = entity;
		} else {

			exampleEntity = (T) entity.cloneEntity(fields);
		}

		if (getLogger().isDebugEnabled()) {
			getLogger().debug("example:{},fields:{},searchEntity:{}", JSONObject.toJSON(exampleEntity),
					JSONObject.toJSON(fields), JSONObject.toJSON(entity));
		}
		Example<T> example = Example.of(exampleEntity);

		return getJpaRepository().exists(example);
	}
}
