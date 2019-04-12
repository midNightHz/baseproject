package cn.zb.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletResponse;

import com.alibaba.fastjson.JSONObject;

import cn.zb.config.AppConfig;
import cn.zb.utils.BeanFactory;

/**
 * 供filter读取配置信息,filter的加载早于其他bean，因此采用懒加载的方式来加载配置
 * 
 * @author chen
 *
 */
public abstract class BaseFilter {

    protected AppConfig appConfig;

    /**
     * 获取配置信息
     * 
     * @return
     */
    protected AppConfig getAppConfig() {
        if (appConfig == null) {
            appConfig = BeanFactory.getBean("appConfig", AppConfig.class);
        }
        return appConfig;
    }

    protected void setJsonResponseStr(ServletResponse response, String words) throws IOException {
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.append(words);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setJsonResponse(ServletResponse response, Object obj) throws IOException {
        try {
            setJsonResponseStr(response, JSONObject.toJSON(obj).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
