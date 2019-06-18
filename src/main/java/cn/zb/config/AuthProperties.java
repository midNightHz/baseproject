package cn.zb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/auth.properties")
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private String excludeUrl =
            "/zhongbao/user/login;/zhongbao/user/register/*;/zhongbao/loggers/*;/zhongbao/mid/*;/zhongbao/open/*;/zhongbao/ad/indexinfo;/zhongbao/image/*;/zhongbao/sms/*;/zhongbao/goodpic/list";

    private String signCheckExcluderUrl = "";
    
    private boolean checkSign=true;
    
    private String salt="9E1FCEEA02A";

    public String getExcludeUrl() {
        return excludeUrl;
    }

    public void setExcludeUrl(String excludeUrl) {
        this.excludeUrl = excludeUrl;
    }

    public String getSignCheckExcluderUrl() {
        return signCheckExcluderUrl;
    }

    public void setSignCheckExcluderUrl(String signCheckExcluderUrl) {
        this.signCheckExcluderUrl = signCheckExcluderUrl;
    }

    public boolean isCheckSign() {
        return checkSign;
    }

    public void setCheckSign(boolean checkSign) {
        this.checkSign = checkSign;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
