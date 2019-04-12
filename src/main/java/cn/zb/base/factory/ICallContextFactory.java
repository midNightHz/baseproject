package cn.zb.base.factory;

import javax.servlet.http.HttpServletRequest;

import cn.zb.base.controller.CallContext;

/**
 * 
 * @ClassName:  ICallContextFactory   
 * @Description:上下文获取工厂类接口
 * @author: 陈军
 * @date:   2019年1月18日 下午4:21:41   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public interface ICallContextFactory {

    CallContext getCallContext(HttpServletRequest request);

}
