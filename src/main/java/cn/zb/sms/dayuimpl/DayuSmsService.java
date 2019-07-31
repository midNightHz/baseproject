package cn.zb.sms.dayuimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import cn.zb.sms.config.DayuSmsProperties;
import cn.zb.sms.interfaces.ISmsService;
import cn.zb.utils.Assert;

/**
 * 
 * @ClassName:  DayuSmsService   
 * @Description:阿里大鱼的SMS短信服务层   
 * @author: 陈军
 * @date:   2019年4月22日 下午1:51:22   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Service
public class DayuSmsService implements ISmsService {
    @Autowired
    private DayuSmsProperties dayuSmsProperties;

    @Override
    public void sendSms(String telephone, Map<String, Object> params, String tempId) throws Exception {
        // 一些参数的校验
        Assert.isEmpty(telephone, "电话号码不能为空");

        Assert.isEmpty(tempId, "短信模板不能为空");

        Assert.isNull(params, "参数不能为空");

        String keyid = dayuSmsProperties.getAccessKeyId();
        String keySecret = dayuSmsProperties.getAccessKeySecret();

        String smsProduct = dayuSmsProperties.getSmsProduct();
        String smsDomain = dayuSmsProperties.getSmsDomain();

        String signName = dayuSmsProperties.getSignName();

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", keyid, keySecret);

        DefaultProfile.addEndpoint("cn-hangzhou", smsProduct, smsDomain);

        IAcsClient acsClient = new DefaultAcsClient(profile);
        DayuCommonRequestBuilder request = DayuCommonRequestBuilder.builder()//
                .domain(smsDomain).regionId("cn-hangzhou").phoneNum(telephone)// 手机号
                .templateCode(tempId)// 模板
                .templateParam(JSONObject.toJSONString(params))// 参数
                .signName(signName);// 签名
        CommonResponse response = acsClient.getCommonResponse(request);

        String result = response.getData();
        /**
         * {
        "Message": "OK",
        "RequestId": "f50bb969-aaab-49f3-9b1d-e156089dc05b",
        "BizId": "607315751858423771^0",
        "Code": "OK"
        }
         */
        JSONObject jsonResult = JSONObject.parseObject(result);
        String code = jsonResult.getString("Code");
        if ("OK".equalsIgnoreCase(code)) {
            return;
        }
        throw new Exception(jsonResult.getString("Message"));

    }

    @Override
    public void sendSms(List<String> telephones, List<Map<String, Object>> params, String tempId) throws Exception {
        Assert.isNull(telephones, "电话号码不能为空");

        Assert.isEmpty(tempId, "短信模板不能为空");

        Assert.isNull(params, "参数不能为空");
        
        if(telephones.size()!=params.size()) {
            throw new Exception("参数的数量与电话号码的数量不一致");
        }

        String keyid = dayuSmsProperties.getAccessKeyId();
        String keySecret = dayuSmsProperties.getAccessKeySecret();

        String smsProduct = dayuSmsProperties.getSmsProduct();
        String smsDomain = dayuSmsProperties.getSmsDomain();

        String signName = dayuSmsProperties.getSignName();

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", keyid, keySecret);

        DefaultProfile.addEndpoint("cn-hangzhou", smsProduct, smsDomain);
        
        List<String> signs=new ArrayList<>();
        telephones.forEach(e->{
            signs.add(signName);
        });

        IAcsClient acsClient = new DefaultAcsClient(profile);
        DayuBatchSmsRequstBuilder request = DayuBatchSmsRequstBuilder.builder()//
                .domain(smsDomain).regionId("cn-hangzhou").phoneNumberJson(JSONObject.toJSONString(telephones))// 手机号
                .templateCode(tempId)// 模板
                .templateParamJson(JSONObject.toJSONString(params))// 参数
                .signNameJson(JSONObject.toJSONString(signs));// 签名
        CommonResponse response = acsClient.getCommonResponse(request);

        String result = response.getData();
        /**
         * {
        "Message": "OK",
        "RequestId": "f50bb969-aaab-49f3-9b1d-e156089dc05b",
        "BizId": "607315751858423771^0",
        "Code": "OK"
        }
         */
        JSONObject jsonResult = JSONObject.parseObject(result);
        String code = jsonResult.getString("Code");
        if ("OK".equalsIgnoreCase(code)) {
            return;
        }
        throw new Exception(jsonResult.getString("Message"));

    }

}
