package cn.zb.commoms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import cn.zb.base.controller.CallContext;
import cn.zb.base.factory.ICallContextFactory;
import cn.zb.commoms.user.entity.User;
import cn.zb.utils.ScretUtil;

@Component
public class CallContextFactoryImpl implements ICallContextFactory {

    @Override
    public CallContext getCallContext(HttpServletRequest request) {
        CallContext callContext = new CallContext();
        // ID 秘钥解密
        String dataKey = request.getParameter("datakey");
        String appId = ScretUtil.decrypt(dataKey);
        callContext.setAppId(appId);
        User user = (User) request.getAttribute("user");
        if (user != null) {
            callContext.setUserName(user.getpName());
            callContext.setCorpId(user.getRid());
            callContext.setUserId(user.getpLsm());
        } else {
            callContext.setCorpId(0);
        }
        return callContext;
    }

}
