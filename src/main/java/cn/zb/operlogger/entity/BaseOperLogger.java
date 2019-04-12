package cn.zb.operlogger.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.alibaba.fastjson.annotation.JSONField;

import cn.zb.base.entity.BaseEntity;

/**
 * 
 * @ClassName:  BaseOperLogger   
 * @Description:所有操作日志实体的基类
 * @author: 陈军
 * @date:   2019年1月4日 上午9:07:47   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
@MappedSuperclass
public abstract class BaseOperLogger implements BaseEntity<Integer> {

    /**   
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
     */
    private static final long serialVersionUID = 1987230357405170820L;

    @Id
    @GeneratedValue
    protected Integer logid;
    // 操作时间
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    protected Date opertime;
    // 操作用户
    protected Integer oper;
    // 操作类型
    protected Integer opertype;

    protected String logdescription;

    protected Integer status ;

    public String getLogdescription() {
        return logdescription;
    }

    public Integer getStatus() {
        return status;
    }

    public void setLogdescription(String logdescription) {
        this.logdescription = logdescription;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public Integer getId() {
        return logid;
    }

    @Override
    public void setId(Integer id) {

    }

    public Date getOpertime() {
        return opertime;
    }

    public void setOpertime(Date opertime) {
        this.opertime = opertime;
    }

    public Integer getOper() {
        return oper;
    }

    public void setOper(Integer oper) {
        this.oper = oper;
    }

    public Integer getOpertype() {
        return opertype;
    }

    public void setOpertype(Integer opertype) {
        this.opertype = opertype;
    }

}
