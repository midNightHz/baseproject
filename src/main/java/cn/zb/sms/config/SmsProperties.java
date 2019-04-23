package cn.zb.sms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {

    private String smsServiceClass = "cn.zb.sms.impl.DayuSmsService";

    public String getSmsServiceClass() {
        return smsServiceClass;
    }

    public void setSmsServiceClass(String smsServiceClass) {
        this.smsServiceClass = smsServiceClass;
    }
    
    

}
