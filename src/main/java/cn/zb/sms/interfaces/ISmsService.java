package cn.zb.sms.interfaces;

import java.util.Map;

/**
 * 
 * @ClassName:  ISmsService   
 * @Description:短信息发送服务层  
 * @author: 陈军
 * @date:   2019年4月22日 下午1:33:39   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public interface ISmsService {

    void sendSms(String telephone, Map<String, Object> params, String tempId) throws Exception;

    void sendSms(String telephone, String content) throws Exception;
}
