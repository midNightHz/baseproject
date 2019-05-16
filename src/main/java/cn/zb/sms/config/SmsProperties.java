package cn.zb.sms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {

    private String sendServiceClass = "cn.zb.sms.dayuimpl.DayuSmsService";

    /**
     * 验证码长度
     */
    private int codeLenth = 4;

    /**
     * 验证码发送时间间隔
     */
    private int codeSendInterval = 3;

    /**
     * 验证码存在时间
     */
    private int codeUsefulLife = 15;
    /**
     * 验证码模板
     */
    private String codeTemplate;

    public String getSendServiceClass() {
        return sendServiceClass;
    }

    public void setSendServiceClass(String sendServiceClass) {
        this.sendServiceClass = sendServiceClass;
    }

    public int getCodeLenth() {
        return codeLenth;
    }

    public int getCodeSendInterval() {
        return codeSendInterval;
    }

    public int getCodeUsefulLife() {
        return codeUsefulLife;
    }

    public void setCodeLenth(int codeLenth) {
        this.codeLenth = codeLenth;
    }

    public void setCodeSendInterval(int codeSendInterval) {
        this.codeSendInterval = codeSendInterval;
    }

    public void setCodeUsefulLife(int codeUsefulLife) {
        this.codeUsefulLife = codeUsefulLife;
    }

    public String getCodeTemplate() {
        return codeTemplate;
    }

    public void setCodeTemplate(String codeTemplate) {
        this.codeTemplate = codeTemplate;
    }

}
