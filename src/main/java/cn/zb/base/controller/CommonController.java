package cn.zb.base.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.zb.base.factory.ICallContextFactory;
import cn.zb.base.result.JsonResult;
import cn.zb.utils.BeanFactory;

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
	 * @Title: getServiceController @Description:
	 *         TODO(这里用一句话描述这个方法的作用) @author:陈军 @date 2019年1月30日 下午1:04:36 @return
	 *         ServiceController @throws
	 */
	default CallContext getCallContext(HttpServletRequest request) {

		ICallContextFactory factory = BeanFactory.getBean(ICallContextFactory.class);
		if (factory == null) {
			throw new RuntimeException("获取callContext对象失败,没有获取callContext的业务代码");
		}
		return factory.getCallContext(request);

	}

	default <T> JsonResult<T> toSucessResult(T t) {

		return JsonResult.getSuccessJsonResult(t);

	}

	default <T> JsonResult<T> toFailResult(T t) {
		return JsonResult.getFailJsonResult(t);
	}

	default <T> JsonResult<T> toResult(T t, int status, String msg) {
		return JsonResult.getJsonResult(t, msg, status);
	}
	
	default <E> E fromInputParams(HttpServletRequest request, Class<E> eClass) {

        Map<String, String[]> params = request.getParameterMap();

        Map<String, Object> paramsObj = new HashMap<>();

        Set<Entry<String, String[]>> entrySet = params.entrySet();

        for (Entry<String, String[]> entry : entrySet) {

            String[] strValues = entry.getValue();

            String key = entry.getKey();

            if (strValues != null && strValues.length > 0) {

                String value = strValues[0].trim();

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
        return e;
    }
	
	
	default <E> E fromInputJson(HttpServletRequest request, Class<E> eClass) {
        String json = this.getRequestString(request);

        getLogger().trace("接收到客户端上传JSON：{}", json);

        E e = JSON.parseObject(json, eClass);

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
	
	
	default <E> List<E> fromInputJsonToList(HttpServletRequest request, Class<E> eClass) {
        String json = this.getRequestString(request);

        getLogger().trace("接收到客户端上传JSON：{}", json);

        return JSON.parseArray(json, eClass);
    }

}
