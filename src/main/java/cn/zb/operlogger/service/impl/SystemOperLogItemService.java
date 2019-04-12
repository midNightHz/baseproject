package cn.zb.operlogger.service.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.zb.base.controller.CallContext;
import cn.zb.base.entity.BaseEntity;
import cn.zb.base.entity.EntityUtil;
import cn.zb.base.model.ObjectDifference;
import cn.zb.base.repository.BaseJpaRepository;
import cn.zb.base.service.BaseService;
import cn.zb.operlogger.Interface.IBaseLogerService;
import cn.zb.operlogger.annotation.AuditSubmitable;
import cn.zb.operlogger.constants.LogAuditConstants;
import cn.zb.operlogger.constants.OperTypeValue;
import cn.zb.operlogger.entity.BaseOperLogger;
import cn.zb.operlogger.entity.LogItemsKey;
import cn.zb.operlogger.entity.SystemOperLogItem;
import cn.zb.operlogger.repository.SystemOperLogItemJpaRepository;
import cn.zb.operlogger.service.ISystemOperLogItemService;
import cn.zb.utils.BeanFactory;

/**
 * 
 * @ClassName:  SystemOperLogItemService   
 * @Description:日志详情实现类   
 * @author: 陈军
 * @date:   2019年1月7日 下午3:27:17   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Service
public class SystemOperLogItemService implements ISystemOperLogItemService {

    private static Logger logger = LoggerFactory.getLogger(SystemOperLogItemService.class);

    @Autowired
    private SystemOperLogItemJpaRepository systemOperLogItemRepository;

    @Override
    public BaseJpaRepository<SystemOperLogItem, LogItemsKey> getJpaRepository() {
        return systemOperLogItemRepository;
    }

    @Override
    public void initUpdate(SystemOperLogItem t, CallContext callContext) throws Exception {

    }

    @Override
    public void initInsert(SystemOperLogItem t, CallContext callContext) throws Exception {

    }

    @Override
    public List<SystemOperLogItem> saveLogItem(BaseEntity<?> source, BaseEntity<?> target, CallContext callContext,
            BaseOperLogger operLogger, List<ObjectDifference> diffs) throws Exception {
        // 日志记录 debug类型
        if (logger.isDebugEnabled()) {
            logger.debug("source:" + JSONObject.toJSONString(source));
            logger.debug("target:" + JSONObject.toJSONString(target));

            logger.debug("operlog：" + JSONObject.toJSONString(operLogger));

            logger.debug("diffrence:" + JSONObject.toJSONString(diffs));

        }
        // 操作类型
        int operType = operLogger.getOpertype();
        if (operType == OperTypeValue.UPDATE.getOperType()) {
            return saveUpdateLogItems(source, target, callContext, operLogger, diffs,
                    OperTypeValue.UPDATE.getOperType());
        }
        if (operType == OperTypeValue.DELETE.getOperType()) {
            return saveDeleteLogItems(target, callContext, operLogger);
        }
        if (operType == OperTypeValue.SOFTDELETE.getOperType()) {
            return saveUpdateLogItems(source, target, callContext, operLogger, diffs,
                    OperTypeValue.SOFTDELETE.getOperType());

        }
        return null;
    }

    /**
     * 
     * @Title: saveUpdateLogItems   
     * @Description: 保存修改日志详情 
     * @author:陈军
     * @date 2019年1月7日 下午3:27:49 
     * @param source 源数据
     * @param target    修改后的数据
     * @param callContext
     * @param operLogger
     * @return
     * @throws Exception      
     * List<SystemOperLogItem>      
     * @throws
     */
    private List<SystemOperLogItem> saveUpdateLogItems(BaseEntity<?> source, BaseEntity<?> target,
            CallContext callContext, BaseOperLogger operLogger, List<ObjectDifference> differences, Integer targetType)
            throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("开始记录修改日志详情");
        }
        if (differences == null || differences.size() == 0) {
            return null;
        }
        String tableName = getTableName(target);
        if (tableName == null) {
            return null;
        }
        String idName = EntityUtil.GetEntityIdFieldName(target.getClass());// getIdName(target);
        if (idName == null) {
            return null;
        }
        List<SystemOperLogItem> list = new ArrayList<>();
        // debug日志 1

        for (int i = 0; i < differences.size(); i++) {
            ObjectDifference diff = differences.get(i);
            SystemOperLogItem logItem = new SystemOperLogItem();

            LogItemsKey key = new LogItemsKey(operLogger.getId(), i + 1, tableName);
            logItem.setId(key);
            logItem.setOptype(targetType);
            Object id = target.getId();
            // 如果是联合主键的情况下，需要将id统一转换成json对象
            if ((id.getClass().getAnnotation(EmbeddedId.class)) != null) {
                id = JSONObject.toJSON(id);
            }
            logItem.setKeyid(id.toString());
            logItem.setPricolumn(idName);
            logItem.setOpcolumn(diff.getField());
            logItem.setOpvalue(diff.getTarget().toString());
            logItem.setLoginfo(diff.toString());
            list.add(logItem);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("operlogitems:" + JSONObject.toJSONString(list));
        }
        return insertBatch(list, callContext);
    }

    /**
     * 
     * @Title: saveDeleteUpdateLogdItems   
     * @Description: 保存删除日志
     * @author:陈军
     * @date 2019年1月7日 下午3:28:28 
     * @param target
     * @param callContext
     * @param operLogger
     * @return
     * @throws Exception      
     * List<SystemOperLogItem>      
     * @throws
     */
    private List<SystemOperLogItem> saveDeleteLogItems(BaseEntity<?> target, CallContext callContext,
            BaseOperLogger operLogger) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("开始记录修改日志详情");
        }

        String tableName = getTableName(target);
        if (tableName == null) {
            return null;
        }
        String idName = EntityUtil.GetEntityIdFieldName(target.getClass());// getIdName(target);
        if (idName == null) {
            return null;
        }
        List<SystemOperLogItem> list = new ArrayList<>();
        SystemOperLogItem logItem = new SystemOperLogItem();

        LogItemsKey key = new LogItemsKey(operLogger.getId(), 1, tableName);
        logItem.setId(key);
        logItem.setOptype(OperTypeValue.DELETE.getOperType());
        Object id = target.getId();
        // 如果是联合主键的情况下，需要将id统一转换成json对象
        if ((id.getClass().getAnnotation(EmbeddedId.class)) != null) {
            id = JSONObject.toJSON(id);
        }
        logItem.setKeyid(JSONObject.toJSONString(id));
        logItem.setPricolumn(idName);
        logItem.setOpcolumn(idName);
        logItem.setOpvalue("");
        logItem.setLoginfo("删除");
        list.add(logItem);

        return insertBatch(list, callContext);
    }

    /**
     * 
     * @Title: getTableName   
     * @Description: 获得实体对象的数据库表名  
     * @author:陈军
     * @date 2019年1月7日 下午3:28:47 
     * @param target
     * @return      
     * String      
     * @throws
     */
    private String getTableName(BaseEntity<?> target) {

        Class<?> objClass = target.getClass();
        return getTableName(objClass);

    }

    private static String getTableName(Class<?> c) {
        try {

            Entity entityAnnotation = c.getAnnotation(Entity.class);
            if (entityAnnotation == null) {
                return null;
            }
            Table table = c.getAnnotation(Table.class);
            if (table == null || StringUtils.isEmpty(table.name())) {
                // TODO 待处理
                return null;
            }

            return table.name().toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Transactional(rollbackFor = { Exception.class, RuntimeException.class })
    @Override
    public void auditLogItem(Integer logid, String tableName, CallContext callContext, Integer auditStatus)
            throws Exception {
        Class<? extends BaseEntity> entityClass = EntityUtil.getTableEntity(tableName);
        // 获取类名
        AuditSubmitable subimitable = entityClass.getAnnotation(AuditSubmitable.class);
        if (subimitable == null) {
            throw new Exception("系统异常,该表不需要审核");
        }
        // 根据类名构造实例对象
        Class<? extends BaseService<?, ?>> objServiceClass = subimitable.serviceClass();
        Class<? extends IBaseLogerService> logServiceClass = subimitable.logServiceClass();
        // 获取实例对应的service
        IBaseLogerService loggerService = BeanFactory.getBean(logServiceClass);
        if (loggerService == null) {
            throw new Exception("系统异常");
        }
        BaseOperLogger log = (BaseOperLogger) loggerService.findById(logid);
        if (log == null) {
            throw new Exception("日志审核异常");
        }

        if (log.getStatus() != LogAuditConstants.INIT) {
            throw new Exception("日志审核异常-只有初始状态才能进行审核");
        }
        BaseService<?, ?> service = BeanFactory.getBean(objServiceClass);
        if (service == null) {
            throw new Exception("系统异常");
        }
        if (auditStatus == LogAuditConstants.AUDITED) {
            // 保存实例/删除实例
            List<SystemOperLogItem> logitems = systemOperLogItemRepository.findByLogidAndTablename(logid, tableName);
            if (logitems == null || logitems.size() == 0) {
                throw new Exception("获取审核详情失败");
            }
            OperTypeValue operTypeValue = getOperType(logitems);
            if (operTypeValue == null) {
                throw new Exception("获取操作类型失败");
            }
            Object entity = instance(entityClass, logitems);

            if (entity == null) {
                throw new Exception("对象实例化失败");
            }
            switch (operTypeValue) {
            case DELETE:
                service.flushDelete(entityClass.cast(entity), callContext);
                break;
            case UPDATE:
            case SOFTDELETE:

                service.flushSave(entityClass.cast(entity), callContext);
                break;

            default:
                break;
            }

        }
        // 修改日志记录
        loggerService.auditLog(logid, auditStatus, callContext);

    }

    public OperTypeValue getOperType(List<SystemOperLogItem> logitems) {
        if (logitems == null || logitems.size() == 0) {
            return null;
        }
        Integer operType = logitems.get(0).getOptype();
        if (operType == null) {
            return null;
        }
        return OperTypeValue.getType(operType);
    }

    /**
     * 
     * @Title: instance   
     * @Description: 用系统日志详情表中的数据初始化对象  
     * @author:陈军
     * @date 2019年1月8日 上午11:26:46 
     * @param cl
     * @param logitems
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     * @throws SecurityException      
     * Object      
     * @throws
     */
    private Object instance(Class<?> cl, List<SystemOperLogItem> logitems)
            throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
        if (logitems.size() == 0) {
            return null;
        }
        JSONObject json = new JSONObject();

        for (SystemOperLogItem e : logitems) {
            json.put(e.getOpcolumn(), e.getOpvalue());

            String idField = e.getPricolumn();
            String keyValue = e.getKeyid();

            Field idKey = cl.getDeclaredField(idField);
            // 联合主键的情况
            if (idKey.getAnnotation(EmbeddedId.class) != null) {
                json.put("id", JSON.parse(keyValue));
            } else {
                json.put("id", keyValue);
            }

        }
        return JSON.parseObject(json.toString(), cl);
    }

}
