package cn.zb.sms.dayuimpl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.http.MethodType;

public class DayuCommonRequestBuilder extends CommonRequest {

    private DayuCommonRequestBuilder() {

    }
    

    public static DayuCommonRequestBuilder builder() {
        return new DayuCommonRequestBuilder().action("SendSms").requestMethod(MethodType.POST).version("2017-05-25");
    }

    public DayuCommonRequestBuilder requestMethod(MethodType method) {
        setMethod(method);
        return this;
    }

    public DayuCommonRequestBuilder action(String action) {

        setAction(action);
        return this;
    }

    public DayuCommonRequestBuilder version(String version) {
        setVersion(version);
        return this;
    }

    public DayuCommonRequestBuilder domain(String domain) {
        setDomain(domain);
        return this;
    }

    public DayuCommonRequestBuilder phoneNum(String phoneNum) {

        putQueryParameter("PhoneNumbers", phoneNum);

        return this;
    }

    public DayuCommonRequestBuilder regionId(String regionId) {
        putQueryParameter("RegionId", regionId);
        return this;
    }

    public DayuCommonRequestBuilder signName(String sign) {
        putQueryParameter("SignName", sign);

        return this;
    }

    public DayuCommonRequestBuilder templateCode(String templateCode) {
        putQueryParameter("TemplateCode", templateCode);
        return this;
    }

    public DayuCommonRequestBuilder templateParam(String params) {
        putQueryParameter("TemplateParam", params);
        return this;
    }

    public DayuCommonRequestBuilder smsUpExtendCode(String smsUpExtendCode) {
        putQueryParameter("SmsUpExtendCode", smsUpExtendCode);
        return this;
    }

    public DayuCommonRequestBuilder outId(String outId) {
        putQueryParameter("OutId", outId);
        return this;
    }

}
