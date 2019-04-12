package cn.zb.log.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.zb.base.repository.BaseJpaRepository;
import cn.zb.log.entity.LogItemsKey;
import cn.zb.log.entity.SystemOperLogItem;

/**
 * 
 * @ClassName:  SystemOperLogItemJpaRepository   
 * @Description:日志详情持久层
 * @author: 陈军
 * @date:   2019年1月25日 下午3:00:40   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public interface SystemOperLogItemJpaRepository extends BaseJpaRepository<SystemOperLogItem, LogItemsKey> {

    /**
     * 
     * @Title: findByLogidAndTablename   
     * @Description: 日志详情列表 
     * @author:陈军
     * @date 2019年1月25日 下午3:01:20 
     * @param logid
     * @param tableName
     * @return      
     * List<SystemOperLogItem>      
     * @throws
     */
    @Query(value = "select logid,itemid,optype,tablename,pricolumn,keyid,loginfo,opcolumn,opvalue from SystemOperLogItems   where logid=?1 and tablename=?2", nativeQuery = true)
    List<SystemOperLogItem> findByLogidAndTablename(Integer logid, String tableName);

}
