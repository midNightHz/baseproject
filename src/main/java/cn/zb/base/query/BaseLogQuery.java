package cn.zb.base.query;

import java.util.Calendar;
import java.util.Date;

import cn.zb.base.controller.CallContext;
import cn.zb.page.Pageable;

/**
 * 
 * @ClassName:  BaseLogQuery   
 * @Description:操作日志的基础查询对象   
 * @author: 陈军
 * @date:   2019年1月9日 下午1:40:33   
 *     
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved. 
 *
 */
public abstract class BaseLogQuery extends BaseQuery {

    protected Date startTime;

    protected Date endTime;

    protected Integer status;

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public void init(Pageable pageable, CallContext callContext) {

        if (endTime != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(endTime);
            c.add(Calendar.DATE, 1);
            c.set(Calendar.HOUR, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            endTime = c.getTime();
            // entTime=DateUtil.parseDate(dateStr, "yyyy-MM-dd");
        }
        super.init(pageable);
    }
   

}
