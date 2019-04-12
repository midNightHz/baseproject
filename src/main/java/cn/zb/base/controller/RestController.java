package cn.zb.base.controller;

import cn.zb.utils.NumberUtils;

/**
 * 
 * 
 * 
 * @param <S>
 *            控制器对应的主业务接口类
 */
public abstract class RestController extends ServiceController {

    /**
     * 校验callContext是否有内容
     * 
     * @param callContext
     * @return
     */
    protected boolean checkUser(CallContext callContext) {
        return callContext != null && callContext.getUserName() != null;
    }

    /**
     * 对加密的corpId进行解密
     * 
     * @param callContext
     * @return
     */
    protected Integer getCorpId(CallContext callContext) {
        String appId = callContext.getAppId();
        if (appId != null && NumberUtils.isInt(appId)) {
            return new Integer(appId);
        }
        return 0;
    }

}
