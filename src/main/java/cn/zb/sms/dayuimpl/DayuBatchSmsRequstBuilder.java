package cn.zb.sms.dayuimpl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.http.MethodType;

public class DayuBatchSmsRequstBuilder extends CommonRequest {

    private DayuBatchSmsRequstBuilder() {

    }

    public static DayuBatchSmsRequstBuilder builder() {
        return new DayuBatchSmsRequstBuilder().action("SendBatchSms").requestMethod(MethodType.POST)
                .version("2017-05-25");
    }

    public DayuBatchSmsRequstBuilder requestMethod(MethodType method) {
        setMethod(method);
        return this;
    }

    public DayuBatchSmsRequstBuilder action(String action) {

        setAction(action);
        return this;
    }

    public DayuBatchSmsRequstBuilder version(String version) {
        setVersion(version);
        return this;
    }

    public DayuBatchSmsRequstBuilder domain(String domain) {
        setDomain(domain);
        return this;
    }

    public DayuBatchSmsRequstBuilder phoneNum(String phoneNum) {

        putQueryParameter("PhoneNumbers", phoneNum);

        return this;
    }

    public DayuBatchSmsRequstBuilder regionId(String regionId) {
        putQueryParameter("RegionId", regionId);
        return this;
    }

    public DayuBatchSmsRequstBuilder templateCode(String templateCode) {
        putQueryParameter("TemplateCode", templateCode);
        return this;
    }

    public DayuBatchSmsRequstBuilder smsUpExtendCode(String smsUpExtendCode) {
        putQueryParameter("SmsUpExtendCode", smsUpExtendCode);
        return this;
    }

    public DayuBatchSmsRequstBuilder outId(String outId) {
        putQueryParameter("OutId", outId);
        return this;
    }

    public DayuBatchSmsRequstBuilder phoneNumberJson(String phoneNumberJson) {
        putQueryParameter("PhoneNumberJson", phoneNumberJson);
        return this;
    }

    public DayuBatchSmsRequstBuilder signNameJson(String signNameJson) {
        putQueryParameter("SignNameJson", signNameJson);
        return this;
    }

    public DayuBatchSmsRequstBuilder templateParamJson(String templateParamJson) {
        putQueryParameter("TemplateParamJson", templateParamJson);
        return this;
    }

}
