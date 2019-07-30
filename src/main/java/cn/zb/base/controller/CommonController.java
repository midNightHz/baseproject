package cn.zb.base.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.zb.base.entity.BaseEntity;
import cn.zb.base.factory.ICallContextFactory;
import cn.zb.base.model.Encryptable;
import cn.zb.base.result.JsonResult;
import cn.zb.utils.Assert;
import cn.zb.utils.BeanFactory;
import cn.zb.utils.ClassUtils;
import cn.zb.utils.HttpUtils;

/**
 * 
 * @ClassName: CommonController
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: 陈军
 * @date: 2019年1月23日 上午8:41:23
 * 
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved.
 *
 */
public interface CommonController {

	/**
	 * 
	 * @Title: getLogger @Description: 日志的获取 @author:陈军 @date 2019年1月23日
	 *         上午8:41:32 @return Logger @throws
	 */

	Logger getLogger();

	/**
	 * 
	 * @Title: getCallContext
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param request
	 * @return
	 * @return CallContext
	 * @author 陈军
	 * @date 2019年6月5日 下午2:33:27
	 */
	default CallContext getCallContext(HttpServletRequest request) {

		ICallContextFactory factory = BeanFactory.getBean(ICallContextFactory.class);

		Assert.isNull(factory, "获取callContext对象失败,没有获取callContext的业务代码");
		return factory.getCallContext(request);

	}

	/**
	 * 
	 * @Title: toSucessResult
	 * @Description: 构造返回的结果
	 * @param t
	 * @return
	 * @return JsonResult<T>
	 * @author 陈军
	 * @date 2019年6月5日 下午2:40:32
	 */
	default <T> JsonResult<T> toSucessResult(T t) {

		return JsonResult.getSuccessJsonResult(t);

	}

	/**
	 * 
	 * @Title: toFailResult
	 * @Description: 构造返回结果
	 * @param t
	 * @return
	 * @return JsonResult<T>
	 * @author 陈军
	 * @date 2019年6月5日 下午2:40:55
	 */
	default <T> JsonResult<T> toFailResult(T t) {
		return JsonResult.getFailJsonResult(t);
	}

	default <T> JsonResult<T> toResult(T t, int status, String msg) {
		return JsonResult.getJsonResult(t, msg, status);
	}

	/**
	 * 
	 * @Title: fromInputParams
	 * @Description: 根据请求参数实例化对象
	 * @param request
	 * @param eClass
	 *            实例化对象类名
	 * @return
	 * @return E
	 * @author 陈军
	 * @date 2019年6月5日 下午2:39:51
	 */
	default <E> E fromInputParams(HttpServletRequest request, Class<E> eClass) {

		Map<String, String> params = HttpUtils.getParams(request);

		Map<String, Object> paramsObj = new HashMap<>();

		Set<Entry<String, String>> entrySet = params.entrySet();

		for (Entry<String, String> entry : entrySet) {

			String value = entry.getValue();

			String key = entry.getKey();

			if (StringUtils.isNotBlank(value)) {

				if (value.startsWith("[") && value.endsWith("]")) {

					paramsObj.put(key, JSONArray.parse(value));

				} else if (value.startsWith("{") && value.endsWith("}")) {
					paramsObj.put(key, JSONObject.parse(value));
				}

				paramsObj.put(key, value);
			} else {
				paramsObj.put(key, null);
			}
		}
		String json = JSONObject.toJSONString(paramsObj);
		E e = JSON.parseObject(json, eClass);
		decryptId(e);
		return e;
	}

	default <E> E fromInputJson(HttpServletRequest request, Class<E> eClass) {
		String json = this.getRequestString(request);

		getLogger().trace("接收到客户端上传JSON：{}", json);

		E e = JSON.parseObject(json, eClass);
		decryptId(e);
		return e;
	}

	default String getRequestString(HttpServletRequest request) {
		StringBuilder callBackXmlBuilder = new StringBuilder();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			String line = null;

			while ((line = br.readLine()) != null) {
				callBackXmlBuilder.append(line);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return callBackXmlBuilder.toString();
	}

	/**
	 * 
	 * @Title: fromInputJsonToList
	 * @Description: 获取参数-转换成list对象
	 * @param request
	 * @param eClass
	 * @return
	 * @return List<E>
	 * @author 陈军
	 * @date 2019年6月5日 下午2:32:18
	 */
	default <E> List<E> fromInputJsonToList(HttpServletRequest request, Class<E> eClass) {
		String json = this.getRequestString(request);

		getLogger().trace("接收到客户端上传JSON：{}", json);

		List<E> list = JSON.parseArray(json, eClass);

		decryptId(list);

		return list;
	}

	default <E> E decryptId(E e) {

		if (e == null) {
			return null;
		}

		if (e instanceof Iterable) {

			Iterable<?> it = (Iterable<?>) e;

			Iterator<?> itor = it.iterator();
			while (itor.hasNext()) {
				decryptId(itor.next());
			}
		}
		if (e instanceof Encryptable) {
			Encryptable encrypt = (Encryptable) e;
			encrypt.decryptId();
		}
		if (e instanceof BaseEntity) {
			List<Field> fs = ClassUtils.getFields(e.getClass());

			fs.forEach(f -> {
				try {
					f.setAccessible(true);
					Object obj = f.get(e);

					decryptId(obj);
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			});
		}

		return e;
	}

}
