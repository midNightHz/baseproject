package cn.zb.base.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.NativeWebRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.zb.base.factory.ICallContextFactory;
import cn.zb.base.result.JsonResult;
import cn.zb.utils.BeanFactory;

/**
 * 带部分业务逻辑的控制层
 * 
 * @author chen
 *
 */

@Deprecated
public abstract class ServiceController extends EventPublisherController {

    private static Logger logger = LoggerFactory.getLogger(ServiceController.class);

    /**
     * 获取HttpServletRequest对象
     *
     * @param request
     *            客户请求
     * @return
     */
    @Override
    public HttpServletRequest getHttpServletRequest(NativeWebRequest request) {
        if (request instanceof HttpServletRequest) {
            return (HttpServletRequest) request;
        }

        HttpServletRequest servletRequest = request.getNativeRequest(HttpServletRequest.class);

        return servletRequest;
    }

    private ICallContextFactory callContextFactory;

    private ICallContextFactory getCallContextFactory() {

        if (callContextFactory == null) {

            callContextFactory = BeanFactory.getBean(ICallContextFactory.class);
        }

        return callContextFactory;
    }

    /**
     * 从客户端请求中解析登陆客户信息
     *
     * @param request
     *            客户请求
     * @return
     */
    public CallContext getCallContext(HttpServletRequest request) {

        ICallContextFactory factory = getCallContextFactory();
        if (factory == null) {
            throw new RuntimeException("获取callContext对象失败,没有获取callContext的业务代码");
        }
        return getCallContextFactory().getCallContext(request);

    }

    public String getRequestString(NativeWebRequest request) throws Exception {
        return this.getRequestString(this.getHttpServletRequest(request));
    }

    public String getRequestString(HttpServletRequest request) {
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

    public <E> E fromInputJson(NativeWebRequest request, Class<E> eClass) {
        return this.fromInputJson(this.getHttpServletRequest(request), eClass);
    }

    public <E> E fromInputJson(HttpServletRequest request, Class<E> eClass) {
        String json = this.getRequestString(request);

        logger.trace("接收到客户端上传JSON：{}", json);

        E e = JSON.parseObject(json, eClass);

        return e;
    }

    public <E> E fromInputJson(HttpServletRequest request, TypeReference<E> typeReference) {
        String json = this.getRequestString(request);
       // processExtFields(json, typeReference);
        logger.trace("接收到客户端上传JSON：{}", json);

        E e = JSON.parseObject(json, typeReference);

        return e;
    }

    public <E> List<E> fromInputJsonToList(HttpServletRequest request, Class<E> eClass) {
        String json = this.getRequestString(request);

        logger.trace("接收到客户端上传JSON：{}", json);

        return JSON.parseArray(json, eClass);
    }

    public <E> String toOutputJson(E e) {

        return JSON.toJSONString(e, SerializerFeature.DisableCircularReferenceDetect);
    }

    public <E> void writeJsonToClient(HttpServletResponse response, E e) {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String json = this.toOutputJson(e);

        logger.trace("向客户端输出JSON对象:{}", json);
        try {
            response.getWriter().print(json);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public <E> void writeJsonToClient(HttpServletResponse response, Integer errCode, String errMessage, E data) {
        if (errCode == null) {
            throw new NullPointerException("wirteJsonToClient - param errCode is null.");
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        JsonResult<E> jsonResult = JsonResult.getSuccessJsonResult(data);
        jsonResult.setCode(errCode);
        jsonResult.setErrmsg(errMessage);

        String json = this.toOutputJson(jsonResult);

        logger.trace("向客户端输出JSON对象:{}", json);
        try {
            response.getWriter().print(json);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public <E> void writeJsonToClient(HttpServletResponse response, Integer errCode, String errMessage) {
        this.writeJsonToClient(response, errCode, errMessage, null);
    }

    public <E> void writeSuccessJsonToClient(HttpServletResponse response, E model) {
        this.writeJsonToClient(response, JsonResult.getSuccessJsonResult(model));
    }

    public <E> void writePermissionDeniedJsonToClient(HttpServletResponse response, E model) {
        this.writeJsonToClient(response, JsonResult.getPermissionDeniedJsonResult(model));
    }

    public <E> void writeFailDataJsonToClient(HttpServletResponse response, E model) {
        this.writeJsonToClient(response, JsonResult.getFailJsonResult(model));
    }

    public void writeFailJsonToClient(HttpServletResponse response) {
        this.writeJsonToClient(response, JsonResult.getFailJsonResult(null));
    }

    /**
     * 
     * @Title: fromInputParams   
     * @Description: 从请求参数中获取参数并实例化成对象--用spring默认的方式会有一些坑，比如说属性是数组或者需要将字符串转换成日期类型时会抛出异常  
     * @author:陈军
     * @date 2019年2月15日 上午8:15:39 
     * @param request
     * @param eClass
     * @return      
     * E      
     * @throws
     */
    public <E> E fromInputParams(HttpServletRequest request, Class<E> eClass) {

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

    // TODO 额外字段的特殊处理？

    protected String processExtFields(String json, Class<?> cl) {
        return json;
    }

}
