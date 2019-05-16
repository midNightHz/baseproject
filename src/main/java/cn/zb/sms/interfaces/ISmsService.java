package cn.zb.sms.interfaces;

import java.util.List;
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
    
    /**
     * 
     * @Title: sendSms   
     * @Description: 短信发送
     * @author:陈军
     * @date 2019年5月8日 下午1:22:03 
     * @param telephone
     * @param params
     * @param tempId
     * @throws Exception      
     * void      
     * @throws
     */
    void sendSms(String telephone, Map<String, Object> params, String tempId) throws Exception;

   // void sendSms(String telephone, String content) throws Exception;
    /**
     * 
     * @Title: sendSms   
     * @Description: 批量发送短信  
     * @author:陈军
     * @date 2019年5月8日 下午1:22:14 
     * @param telephones
     * @param params
     * @param tempId
     * @throws Exception      
     * void      
     * @throws
     */
    void sendSms(List<String> telephones, List<Map<String, Object>> params, String tempId)throws Exception;
}
