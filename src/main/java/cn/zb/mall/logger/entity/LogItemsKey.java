package cn.zb.mall.logger.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * 
 * @ClassName:  LogItemsKey   
 * @Description:日志详情联合主键  
 * @author: 陈军
 * @date:   2019年1月25日 下午3:02:07   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@Embeddable
public class LogItemsKey implements Serializable {

    /**   
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
     */
    private static final long serialVersionUID = -4062600094126756939L;

    private Integer logid;

    private Integer itemid;

    private String tablename;

    public LogItemsKey() {

    }

    public LogItemsKey(Integer logid, Integer itemid, String tablename) {
        super();
        this.logid = logid;
        this.itemid = itemid;
        this.tablename = tablename;
    }

    public Integer getLogid() {
        return logid;
    }

    public Integer getItemid() {
        return itemid;
    }

    public String getTablename() {
        return tablename;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((itemid == null) ? 0 : itemid.hashCode());
        result = prime * result + ((logid == null) ? 0 : logid.hashCode());
        result = prime * result + ((tablename == null) ? 0 : tablename.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LogItemsKey other = (LogItemsKey) obj;
        if (itemid == null) {
            if (other.itemid != null)
                return false;
        } else if (!itemid.equals(other.itemid))
            return false;
        if (logid == null) {
            if (other.logid != null)
                return false;
        } else if (!logid.equals(other.logid))
            return false;
        if (tablename == null) {
            if (other.tablename != null)
                return false;
        } else if (!tablename.equals(other.tablename))
            return false;
        return true;
    }

}
