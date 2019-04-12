package cn.zb.base.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.zb.base.controller.CallContext;
import cn.zb.base.model.BaseModel;
import cn.zb.base.query.BaseQuery;
import cn.zb.base.repository.BaseMybatisRepository;
import cn.zb.base.repository.PageableRepository;
import cn.zb.page.PageData;
import cn.zb.page.PageDataImpl;
import cn.zb.page.Pageable;

public interface PageableService<T extends BaseModel<ID>, ID extends Serializable, Q extends BaseQuery>
        extends IBaseMybatisService<T, ID, Q> {

    PageableRepository<T, ID, Q> getPageableRepository();

    @Override
    default BaseMybatisRepository<T, ID, Q> getBaseMybatisRepository() {
        return getPageableRepository();
    }

    /**
     * 分页查询 要先初始化好pageable对象
     * 
     * @param q
     * @param pageable
     * @return
     */
    default PageData<T> getPageData(Q q, Pageable pageable, CallContext callContext) throws Exception {
        // 初始化query对象
        init(pageable, q, callContext);
        // 采用pageHelper进行物理分页
        Page<T> page = PageHelper.startPage(pageable.getPageNo(), pageable.getPageSize());
        // 查询列表
        List<T> list = getPageableRepository().findAll(q);
        Long totalCount = page.getTotal();

        pageable.setTotalCount(totalCount);
        // 为保证返回前端的数据接口不变
        return new PageDataImpl<>(list, pageable);
    }

    default PageData<T> getPageData0(Q q, Pageable pageable, CallContext callContext) throws Exception {
        // 初始化query对象
        init(pageable, q, callContext);

        List<T> list = new ArrayList<>();

        Long totalCount = getPageableRepository().count(q);

        if (totalCount != null && totalCount > 0) {
            // SQL SERVER分页在最后一页的时候可能会出现意想不到的情况的问题，取出记录的数量和期望中不一样的问题，因此要计算取出需要取出多少条记录的问题
            q.setTotalCount(totalCount);
            q.initFetch();
            list = getPageableRepository().getList(q);
        }

        pageable.setTotalCount(totalCount);

        return new PageDataImpl<>(list, pageable);
    }

    /**
     * 初始化查询数据
     * 
     * @param pageable
     * @param q
     * @param callContext
     * @throws Exception
     */
    default void init(Pageable pageable, Q q, CallContext callContext) throws Exception {
        q.init(pageable, callContext);
    }

}
