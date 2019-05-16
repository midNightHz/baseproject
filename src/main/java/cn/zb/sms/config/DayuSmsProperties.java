package cn.zb.sms.config;
/**
 * 
 * @ClassName:  DayuSmsProperties   
 * @Description:阿里大鱼短信接口配置   
 * @author: 陈军
 * @date:   2019年4月29日 上午10:51:31   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/dayu.properties")
@ConfigurationProperties(prefix = "dayu.sms")
public class DayuSmsProperties {
    
    /**
     *阿里大鱼短信接口key
     */
    private String accessKeyId = "";
    
    /**
     * 阿里大鱼短信接口secret
     */

    private String accessKeySecret = "";
    
    /**
     * 短信发送签名
     */
    private String signName="";
    /**
     * 短信发送产品
     */
    private String smsProduct="Dysmsapi";
    /**
     * 短信发送配置
     */
    private String smsDomain="dysmsapi.aliyuncs.com";
    public String getAccessKeyId() {
        return accessKeyId;
    }
    public String getAccessKeySecret() {
        return accessKeySecret;
    }
    public String getSignName() {
        return signName;
    }
    public String getSmsProduct() {
        return smsProduct;
    }
    public String getSmsDomain() {
        return smsDomain;
    }
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }
    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
    public void setSignName(String signName) {
        this.signName = signName;
    }
    public void setSmsProduct(String smsProduct) {
        this.smsProduct = smsProduct;
    }
    public void setSmsDomain(String smsDomain) {
        this.smsDomain = smsDomain;
    }
    
    
    

}
