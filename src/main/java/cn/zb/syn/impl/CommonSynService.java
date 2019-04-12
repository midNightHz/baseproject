package cn.zb.syn.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.http.HttpUtil;
import cn.zb.config.AppConfig;
import cn.zb.syn.constants.SynConstants;
import cn.zb.syn.interfaces.ICommonSynService;

/**
 * 同步的基础服务层
 * 
 * @author chen
 *
 */
@Service
public class CommonSynService implements ICommonSynService {

	private static Logger logger = LoggerFactory.getLogger(CommonSynService.class);

	@Autowired
	private AppConfig appConfig;

	private static String token;

	@Override
	public String getToken(boolean flush) {

		if (token == null || flush) {
			token = getToken();
		}

		return token;
	}

	private String getToken() {

		String appId = appConfig.getAppKey();

		String appSercret = appConfig.getAppSercret();

		String url = appConfig.getCloudUrl() + SynConstants.GET_TOKEN_URL;

		url = url.replace("APPID", appId).replace("APPSECRET", appSercret);

		if (appId == null || appSercret == null) {

			return null;
		}
		String result = null;
		try {

			result = HttpUtil.post(url, new HashMap<>());

			logger.info("result:{}", result);
			JSONObject jsonObject = JSON.parseObject(result);

			if (jsonObject.getIntValue("code") == 1) {
				return jsonObject.getString("msg");
			}
			return null;
		} catch (Exception e) {
			logger.error("获取token异常:{}", e.getMessage());

			return null;
		} finally {
			if (logger.isDebugEnabled()) {
				logger.debug("url:{},result:{}", url, result);
			}
		}

	}

	@Override
	public String sendToCloudPostBody(String url, String params, boolean retry) throws Exception {

		String token = getToken(!retry);

		url = url.replace("TOKEN", token);

		String result = HttpUtil.post(url, params);

		if (retry) {
			JSONObject json = JSONObject.parseObject(result);

			int code = json.getIntValue("code");
			if (code == 100) {
				if (retry)
					return sendToCloudPostBody(url, params, false);
			}
		}

		return result;
	}

	@Override
	public String sendToCloudGet(String url, Map<String, Object> params, boolean retry) throws Exception {
		String token = getToken(!retry);

		url = url.replace("TOKEN", token);

		String result = HttpUtil.get(url, params);

		if (retry) {
			JSONObject json = JSONObject.parseObject(result);

			int code = json.getIntValue("code");

			if (code == 100) {
				if (retry)
					return sendToCloudGet(url, params, false);
			}
		}

		return result;
	}

}
