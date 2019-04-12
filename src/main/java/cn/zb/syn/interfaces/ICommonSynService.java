package cn.zb.syn.interfaces;

import java.util.Map;

/**
 * 通用的同步接口
 * 
 * @author chen
 *
 */
public interface ICommonSynService {

	/**
	 * 获取平台的token
	 * 
	 * @param flush
	 *            是否需要更新
	 * @return
	 */
	String getToken(boolean flush);
	/**
	 * 发送同步请求 POST请求方式
	 * @param url
	 * @param params
	 * @param retry
	 * @return
	 * @throws Exception
	 */
	String sendToCloudPostBody(String url, String params, boolean retry) throws Exception;
	
	/**
	 * 
	 * @param url
	 * @param params
	 * @param retry
	 * @return
	 * @throws Exception
	 */
	String sendToCloudGet(String url, Map<String, Object> params, boolean retry) throws Exception;

}
