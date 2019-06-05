package cn.zb.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 访问其他项目工具类
 * 
 * @author chen
 *
 */
public class HttpUtils {

	public static Map<String, String> getParams(HttpServletRequest request) {

		Map<String, String> params = new HashMap<>();

		Enumeration<String> paramsName = request.getParameterNames();

		while (paramsName.hasMoreElements()) {
			String paramName = paramsName.nextElement();
			params.put(paramName, request.getParameter(paramName));
		}

		return params;

	}

}
