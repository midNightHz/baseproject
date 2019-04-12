package cn.zb.operlogger.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.zb.base.controller.BaseController;
import cn.zb.base.controller.CallContext;
import cn.zb.operlogger.Interface.IBaseLogerService;
import cn.zb.operlogger.entity.BaseOperLogger;

/**
 * 
 * @ClassName:  BaseAuditController   
 * @Description:日志操作基础controller层  
 * @author: 陈军
 * @date:   2019年1月9日 下午12:37:45   
 *   
 * @param <S>
 * @param <L>  
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public interface IBaseAuditController<S extends IBaseLogerService<L>, L extends BaseOperLogger>
        extends BaseController<S, L, Integer> {

    @GetMapping("audit")
    default void auditLog(HttpServletRequest request, HttpServletResponse response, @RequestParam("logid") Integer id,
            @RequestParam("auditStatus") Integer status) {
        try {
            S s = getService();
            CallContext callContext = getServiceController().getCallContext(request);

            L l = s.findById(id);
            if (l == null) {
                getServiceController().writeFailDataJsonToClient(response, "不存在id为" + id + "的审核记录");
                return;
            }
            if (!s.auditOperLogAuth(l, callContext)) {
                getServiceController().writeFailDataJsonToClient(response, "你没有审核的权限");
                return;
            }
            s.auditOperLog(l, status, callContext);
            getServiceController().writeSuccessJsonToClient(response, "审核成功");

        } catch (Exception e) {
            getLogger().error("审核失败 msg{}", e.getMessage());
            getServiceController().writeFailDataJsonToClient(response, "审核失败：" + e.getMessage());
        }
    }

    @Override
    default void save(HttpServletRequest request, HttpServletResponse response, L e) {
        getServiceController().writeFailDataJsonToClient(response, "不允许用户新增/修改日志");
    }

    @Override
    default void deleteById(HttpServletRequest request, HttpServletResponse response, Integer id) {
        getServiceController().writeFailDataJsonToClient(response, "不允许用户删除日志");
    }
    
    

}
