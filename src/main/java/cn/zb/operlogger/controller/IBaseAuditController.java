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
 * @ClassName: BaseAuditController
 * @Description:日志操作基础controller层
 * @author: 陈军
 * @date: 2019年1月9日 下午12:37:45
 * 
 * @param <S>
 * @param <L>
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved.
 *
 */
public interface IBaseAuditController<S extends IBaseLogerService<L>, L extends BaseOperLogger>
		extends BaseController<S, L, Integer> {

	@GetMapping("audit")
    default Object auditLog(HttpServletRequest request, HttpServletResponse response, @RequestParam("logid") Integer id,
            @RequestParam("auditStatus") Integer status)throws Exception {
            S s = getService();
            CallContext callContext = getCallContext(request);

            L l = s.findById(id);
            if (l == null) {
                return toFailResult("不存在id为" + id + "的审核记录");
            }
            if (!s.auditOperLogAuth(l, callContext)) {
               return toFailResult("你没有审核的权限");
            }
            s.auditOperLog(l, status, callContext);
            return toSucessResult("审核成功");
    }

	@Override
	default Object save(HttpServletRequest request, L e) {
		return toFailResult("不允许用户新增/修改日志");
	}

	@Override
	default Object deleteById(HttpServletRequest request,  Integer id) {
		return toFailResult("不允许用户删除日志");
	}

}
