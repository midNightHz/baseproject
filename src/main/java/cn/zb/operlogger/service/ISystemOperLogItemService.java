package cn.zb.operlogger.service;

import java.util.List;

import cn.zb.base.controller.CallContext;
import cn.zb.base.entity.BaseEntity;
import cn.zb.base.model.ObjectDifference;
import cn.zb.base.service.BaseService;
import cn.zb.operlogger.entity.BaseOperLogger;
import cn.zb.operlogger.entity.LogItemsKey;
import cn.zb.operlogger.entity.SystemOperLogItem;

/**
 * 
 * @ClassName:  ISystemOperLogItemService   
 * @Description:TODO 系统日志详情服务层   
 * @author: 陈军
 * @date:   2019年1月4日 下午5:39:18   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public interface ISystemOperLogItemService extends BaseService<SystemOperLogItem, LogItemsKey> {
    /**
     * 
     * @Title: saveLogItem   
     * @Description: 系统日志详情的记录  
     * @author:陈军
     * @date 2019年1月7日 下午1:21:49 
     * @param source
     * @param target
     * @param callContext
     * @param operLogger
     * @return
     * @throws Exception      
     * List<SystemOperLogItem>      
     * @throws
     */
    List<SystemOperLogItem> saveLogItem(BaseEntity<?> source, BaseEntity<?> target, CallContext callContext,
            BaseOperLogger operLogger,List<ObjectDifference> diffs) throws Exception;

    /**
     * 
     * @Title: auditLogItem   
     * @Description: 日志详情审核  
     * @author:陈军
     * @date 2019年1月7日 下午1:22:24 
     * @param logid     日志id
     * @param tableName 表名
     * @param callContext
     * @param auditStatus 审核状态 --通过-不通过
     * @throws Exception      
     * void      
     * @throws
     */
    void auditLogItem(Integer logid, String tableName, CallContext callContext, Integer auditStatus) throws Exception;

}
