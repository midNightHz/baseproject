package cn.zb.operlogger.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.zb.base.entity.BaseEntity;

/**
 * 
 * @ClassName:  SystemOperLogItem   
 * @Description:日志详情   
 * @author: 陈军
 * @date:   2019年1月25日 下午3:01:44   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Entity
@Table(name = "systemoperlogitems")
public class SystemOperLogItem implements BaseEntity<LogItemsKey> {

    /**   
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
     */
    private static final long serialVersionUID = 8368873157760516753L;
    @EmbeddedId
    protected LogItemsKey id;

    @Override
    public LogItemsKey getId() {
        return id;
    }

    @Override
    public void setId(LogItemsKey id) {
        this.id = id;
    }

    // 操作类型
    protected Integer optype;
    // 主键字段
    protected String pricolumn;
    // 主键id
    protected String keyid;
    // 更新信息
    protected String loginfo;
    // 操作字段
    protected String opcolumn;
    // 操作后的值
    protected String opvalue;
    // 操作对象的类名

    public Integer getOptype() {
        return optype;
    }

    public String getPricolumn() {
        return pricolumn;
    }

    public String getKeyid() {
        return keyid;
    }

    public String getLoginfo() {
        return loginfo;
    }

    public String getOpcolumn() {
        return opcolumn;
    }

    public String getOpvalue() {
        return opvalue;
    }

    public void setOptype(Integer optype) {
        this.optype = optype;
    }

    public void setPricolumn(String pricolumn) {
        this.pricolumn = pricolumn;
    }

    public void setKeyid(String keyid) {
        this.keyid = keyid;
    }

    public void setLoginfo(String loginfo) {
        this.loginfo = loginfo;
    }

    public void setOpcolumn(String opcolumn) {
        this.opcolumn = opcolumn;
    }

    public void setOpvalue(String opvalue) {
        this.opvalue = opvalue;
    }

}
