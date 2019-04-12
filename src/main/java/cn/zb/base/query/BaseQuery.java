package cn.zb.base.query;

import cn.zb.base.controller.CallContext;
import cn.zb.page.Pageable;

public abstract class BaseQuery {

    protected Integer pageSize;

    protected Long totalCount;

    protected Integer pageNo;

    protected Long offset;

    protected Long limit;
    // 取出的记录条数，用来填sql server分页的坑
    protected Integer fetchCount;

    public void initFetch() {
        if (pageSize * pageNo < totalCount) {
            fetchCount = pageSize;
        } else {
            // 2866-144*20 肯定是负数啊,有没有搞错
            fetchCount = (int) (totalCount - pageSize * (pageNo - 1));
            if (fetchCount < 0) {
                fetchCount = 0;
            }
        }

    }

    public void init(Pageable pageable) {

        if (pageable == null) {
            return;
        }
        int pagesize = pageable.getPageSize();
        int pageno = pageable.getPageNo();
        setPageNo(pageno);
        setPageSize(pagesize);
        setTotalCount(pageable.getTotalCount());
        setOffset((long) (pageno - 1) * pagesize);
        setLimit((long) (pageno * pagesize));
    }

    public abstract void init(Pageable pageable, CallContext callContext);

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Integer getFetchCount() {
        return fetchCount;
    }

    public void setFetchCount(Integer fetchCount) {
        this.fetchCount = fetchCount;
    }

}
