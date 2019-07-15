package cn.zb.base.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;

import cn.zb.base.annotation.DefaultValue;
import cn.zb.base.annotation.FieldName;
import cn.zb.base.annotation.FieldUncheckDiff;
import cn.zb.base.annotation.NotNull;
import cn.zb.base.annotation.RegCheck;
import cn.zb.base.model.ObjectDifference;
import cn.zb.utils.Assert;
import cn.zb.utils.ClassUtils;

/**
 * 
 * @ClassName: BaseEntity
 * @Description:所有实体类的接口，基于JPA
 * @author: 陈军
 * @date: 2019年2月18日 下午5:13:03
 * 
 * @param <E>
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved.
 *
 */
@MappedSuperclass
public interface BaseEntity<E extends Serializable> extends Serializable, Cloneable {

	E getId();

	void setId(E id);

	/**
	 * 
	 * @Title: cloneEntity @Description: @author:陈军 @date 2019年4月21日
	 *         上午9:43:35 @param cloneFields 复制的字段，为空时复制全部字段 @return @throws
	 *         Exception Object @throws
	 */
	default Object cloneEntity(String... cloneFields) throws Exception {
		Class<?> cl = this.getClass();
		BaseEntity<?> cloneObject = (BaseEntity<?>) cl.newInstance();

		List<Field> fields = ClassUtils.getFields(cl);

		Set<String> cloneFieldsSet = new HashSet<>();
		if (cloneFields != null && cloneFields.length > 0) {
			Arrays.asList(cloneFields).forEach(e -> cloneFieldsSet.add(e));
		}

		for (Field f : fields) {
			int modifer = f.getModifiers();
			// 修饰词为public private protected 默认的情况)
			if (modifer == 0 || modifer == 1 || modifer == 2 || modifer == 4) {
				if (cloneFieldsSet.size() > 0 && (!cloneFieldsSet.contains(f.getName()))) {
					continue;
				}
				Object o = getField(f);
				cloneObject.setField(f, o);

			}
		}
		return cloneObject;
	}

	/**
	 * 
	 * @Title: cloneToOther @Description:
	 *         将目标对象的参数复制到当前对象，保留当前对象的非空参数 @author:陈军 @date 2018年12月29日
	 *         下午1:10:37 @param other @throws Exception void @throws
	 */
	default void cloneFromOtherNotNull(BaseEntity<?> source, String... immunitys) throws Exception {
		if (source == null) {
			return;
		}
		// System.out.println(this.getClass());
		// System.out.println(source.getClass());
		Class<?> thisClass = this.getClass();

		Class<?> target = source.getClass();

		// Field[] thisFields = thisClass.getDeclaredFields();
		List<Field> thisFields = ClassUtils.getFields(thisClass);

		Set<String> imm = new HashSet<>();
		if (immunitys != null && immunitys.length > 0) {
			for (int i = 0; i < immunitys.length; i++) {
				imm.add(immunitys[i]);
			}
		}

		for (int i = 0; i < thisFields.size(); i++) {
			Field field = thisFields.get(i);
			if (imm.contains(field.getName())) {
				continue;
			}
			int modifer = field.getModifiers();
			// 修饰词为public private protected 默认的情况)
			if (modifer == 0 || modifer == 1 || modifer == 2 || modifer == 4) {

				field.setAccessible(true);
				Object value = field.get(this);
				if (value != null) {
					continue;
				}

				Field targetField = ClassUtils.getField(target, field.getName());
				if (targetField == null) {
					continue;
				}
				targetField.setAccessible(true);

				Object otherValue = targetField.get(source);
				field.set(this, otherValue);
			}
		}

	}

	/**
	 * 
	 * @Title: cloneOtherToThis @Description:
	 *         从其他不同类型的对象中复制属性的类型到当前对象 @author:陈军 @date 2018年12月29日 下午1:57:16 @param
	 *         other @param unCopyFieldname void @throws
	 */
	default void cloneOtherToThis(Object other, String... unCopyFieldname) {
		// 原始对象
		Class<?> objClass = other.getClass();
		// 当前对象的类型
		Class<?> targetClass = this.getClass();

		List<Field> objFields = ClassUtils.getFields(objClass);

		List<Field> targerFields = ClassUtils.getFields(targetClass);

		Map<String, Field> fieldMap = new HashMap<>();

		Set<String> fieldSet = new HashSet<>();

		if (unCopyFieldname != null && unCopyFieldname.length > 0) {
			for (int i = 0; i < unCopyFieldname.length; i++) {
				fieldSet.add(unCopyFieldname[i]);
			}
		}
		// 目标对象MAP
		for (int i = 0; i < targerFields.size(); i++) {
			Field f = targerFields.get(i);
			fieldMap.put(f.getName(), f);
		}

		for (int i = 0; i < objFields.size(); i++) {
			// 源对象字段
			Field f = objFields.get(i);

			if (fieldSet.contains(f.getName())) {
				continue;
			}
			int modifer = f.getModifiers();
			// 修饰词为public private protected 默认的情况，避免复制静态变量
			if (!(modifer == 0 || modifer == 1 || modifer == 2 || modifer == 4)) {
				continue;
			}
			// 目标对象
			Field tf = fieldMap.get(f.getName());
			if (tf != null) {
				// 源字段类型
				Class<?> typeA = f.getType();
				// 目标对象类型
				Class<?> typeB = tf.getType();
				if (typeA.equals(typeB)) {
					try {
						f.setAccessible(true);
						// 有时候获取字段的值会出错,明明是有值的，结果返回为空
						// String
						// methodName="get"+f.getName().toUpperCase().charAt(0)+(f.getName().length()>1?f.getName().substring(1):"");
						// Method m=objClass.getDeclaredMethod(methodName);
						// Object a = m.invoke(other);
						Object a = f.get(other);
						tf.setAccessible(true);
						tf.set(this, a);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}

		}

	}

	/**
	 * 
	 * @Title: checkDiffFromOther @Description: 比较两个对象的差异值，比较策略 当前对象为源对象 other为目标对象
	 *         如果目标对象中的字段为空的话，则跳过比较 如果两个值的字段是一样的话则 不认为是差异值
	 *         如果两个值不一样则认为是差异值 @author:陈军 @date 2019年1月4日 下午4:48:12 @param other
	 *         目标对象 -- @return List<ObjectDifference> @throws
	 */
	default List<ObjectDifference> checkDiffFromOther(Object other) {
		try {
			if (other == null) {
				return null;
			}

			Class<?> objClass = this.getClass();

			Class<?> targetClass = other.getClass();

			if (!objClass.equals(targetClass)) {
				return null;
			}

			// Field[] objFields = targetClass.getDeclaredFields();
			List<Field> objFields = ClassUtils.getFields(targetClass);
			List<ObjectDifference> list = new ArrayList<>();

			for (int i = 0; i < objFields.size(); i++) {
				Field sf = objFields.get(i);
				FieldUncheckDiff uncheck = sf.getAnnotation(FieldUncheckDiff.class);
				if (uncheck != null) {
					continue;
				}
				sf.setAccessible(true);
				Object targetObj = sf.get(other);
				/*
				 * if (targetObj == null) { continue; }
				 */
				// 获取字段名
				String fieldName = getFieldName(sf);
				Object soureObj = sf.get(this);
				if (targetObj == null && soureObj == null) {
					continue;
				}
				if (targetObj != null) {
					if (targetObj.equals(soureObj)) {
						continue;
					}
				}
				ObjectDifference diff = new ObjectDifference(sf.getName(), soureObj, targetObj, fieldName);
				list.add(diff);
			}
			return list;
		} catch (

		Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @Title: getFieldName @Description: 获取字段名 @author:陈军 @date 2019年2月18日
	 *         下午5:12:08 @param sf @return String @throws
	 */
	default String getFieldName(Field sf) {
		FieldName fieldNameAnnotation = sf.getAnnotation(FieldName.class);
		if (fieldNameAnnotation != null) {
			String fValue = fieldNameAnnotation.value();
			if (StringUtils.isNotBlank(fValue)) {
				return fValue;
			}
		}
		return sf.getName();
	}

	/**
	 * 
	 * @Title: setField @Description: 设置属性 @author:陈军 @date 2019年1月10日
	 *         下午5:16:26 @param field @param value @throws Exception void @throws
	 */
	default void setField(Field field, Object value) throws Exception {
		field.setAccessible(true);
		field.set(this, value);
	}

	/**
	 * 
	 * @Title: getField @Description: 获取对应字段的值 @author:陈军 @date 2019年2月18日
	 *         下午5:11:41 @param field @return @throws Exception Object @throws
	 */
	default Object getField(Field field) throws Exception {
		field.setAccessible(true);
		return field.get(this);
	}

	/**
	 * 
	 * @Title: notNull @Description: 用来校验对象中那些不允许为空的字段是否为空 @author:陈军 @date
	 *         2019年1月11日 上午9:43:02 @throws Exception void @throws
	 */
	default void notNull() throws Exception {
		List<Field> fields = EntityUtil.findAnnotionFields(NotNull.class, this.getClass());
		// List<Field> fields = ClassUtils.getFields(this.getClass());
		if (fields == null) {
			return;
		}
		for (int i = 0; i < fields.size(); i++) {
			Field f = fields.get(i);
			NotNull notNull = f.getAnnotation(NotNull.class);
			if (notNull == null) {
				continue;
			}
			Object obj = getField(f);
			if (obj == null) {
				throw new Exception(getFieldName(f) + "不允许为空");
			}

		}
	}

	/**
	 * @throws Exception
	 * 
	 * @Title: defaultValue @Description: 设置数据库中的默认值 如果字段为空 则设置默认值 @author:陈军 @date
	 *         2019年1月24日 下午3:55:45 void @throws
	 */
	default void defaultValue() throws Exception {
		List<Field> fields = EntityUtil.findAnnotionFields(DefaultValue.class, this.getClass());
		// List<Field> fields = ClassUtils.getFields(this.getClass());
		if (fields == null) {
			return;
		}
		for (Field f : fields) {

			DefaultValue dv = f.getAnnotation(DefaultValue.class);
			if (dv == null) {
				continue;
			}
			Object value = getField(f);
			if (value != null) {
				continue;
			}
			// 配置的默认值
			String v = dv.value();
			if (v == null) {
				throw new Exception("默认值不能为空");
			}

			Class<?> fType = f.getType();

			Object defaultValue = null;
			if (fType.isAssignableFrom(String.class)) {
				defaultValue = v;
			} else {
				if (StringUtils.isBlank(v)) {
					throw new Exception("默认值不能为空");
				}
				if (fType.isAssignableFrom(Integer.class)) {
					defaultValue = new Integer(v);
				}
				if (fType.isAssignableFrom(Short.class)) {
					defaultValue = new Short(v);
				}
				if (fType.isAssignableFrom(Double.class)) {
					defaultValue = new Double(v);
				}
				if (fType.isAssignableFrom(Float.class)) {
					defaultValue = new Float(v);
				}
				if (fType.isAssignableFrom(Character.class)) {
					defaultValue = v.trim().charAt(0);
				}
				if (fType.isAssignableFrom(Long.class)) {
					defaultValue = new Long(v);
				}
				if (fType.isAssignableFrom(Boolean.class)) {
					defaultValue = new Boolean(v);
				}
				if (fType.isAssignableFrom(Byte.class)) {
					defaultValue = new Byte(v);
				}
			}

			setField(f, defaultValue);

		}

	}

	/**
	 * 
	 * @Title: checkStringReg
	 * @Description: 校验字段类型是否符合规范 可以校验string or number类型
	 * @Description:校验时会先检索实体类上是否有注解再去校验字段上是否有注解;常用的正则@see cn.zb.utils
	 * @return void
	 * @author 陈军
	 * @date 2019年5月27日 下午5:33:50
	 */
	default void checkStringReg() throws Exception {

		List<Field> fields = EntityUtil.findAnnotionFields(RegCheck.class, this.getClass());
		// List<Field> fields = ClassUtils.getFields(this.getClass());
		if (fields == null) {
			return;
		}

		for (Field f : fields) {

			Object value = getField(f);
			if (value == null) {
				return;
			}

			// 字段类型的判断
			Class<?> fType = f.getType();

			if (Number.class.isAssignableFrom(fType) || String.class.isAssignableFrom(fType)) {

				// 是否需要校验格式
				RegCheck fr = f.getAnnotation(RegCheck.class);

				String reg = fr == null ? null : fr.reg();
				if (StringUtils.isBlank(reg)) {

					continue;

				}

				String str = value.toString();

				Assert.isMatch(reg, str, getFieldName(f) + "格式不正确");

			}

		}

	}

}
