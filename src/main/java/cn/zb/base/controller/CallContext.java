package cn.zb.base.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName:  CallContext   
 * @Description:上下文 用于controll层和service层之间的数据传输   
 * @author: 陈军
 * @date:   2019年1月18日 下午4:20:57   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public class CallContext {

    private Integer userId;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 公司id
     */
    private Integer corpId = 0;

    // 通过datakey解密而来的数据
    private String appId;
    /**
     * callContext的扩展，允许自定义字段，通过getAttribute和setAttribute来设置和获取参数字段
     */
    private Map<String, Object> attributes = new HashMap<>();

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getCorpId() {
        return corpId;
    }

    public void setCorpId(Integer corpId) {
        this.corpId = corpId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
