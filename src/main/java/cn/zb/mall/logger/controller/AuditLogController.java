package cn.zb.mall.logger.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.zb.base.controller.CallContext;
import cn.zb.base.controller.RestController;
import cn.zb.mall.logger.service.ISystemOperLogItemService;

/**
 * 
 * @ClassName:  AuditLogController   
 * @Description:日志详情审核控制层  
 * @author: 陈军
 * @date:   2019年1月25日 下午3:03:37   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Controller
@RequestMapping("log")
public class AuditLogController extends RestController {
    @Autowired
    private ISystemOperLogItemService auditService;

    /**
     * 
     * @Title: logAutit   
     * @Description: 日志详情的审核  
     * @author:陈军
     * @date 2019年1月25日 下午3:04:09 
     * @param request
     * @param response
     * @param logid
     * @param tableName
     * @param auditStatus      
     * void      
     * @throws
     */
    @GetMapping("audit")
    public void logAutit(HttpServletRequest request, HttpServletResponse response, @RequestParam() Integer logid,
            @RequestParam() String tableName, @RequestParam() int auditStatus) {
        try {
            CallContext callContext = this.getCallContext(request);
            auditService.auditLogItem(logid, tableName, callContext, auditStatus);
            this.writeSuccessJsonToClient(response, "审核成功");
        } catch (Exception e) {
            //e.printStackTrace();
            this.writeFailDataJsonToClient(response, "审核失败:" + e.getMessage());
        }
    }

}
