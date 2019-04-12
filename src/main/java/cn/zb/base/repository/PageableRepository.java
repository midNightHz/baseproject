package cn.zb.base.repository;

import java.io.Serializable;
import java.util.List;

import cn.zb.base.model.BaseModel;
import cn.zb.base.query.BaseQuery;

/**
 * 带分页的持久层接口，继承该接口是需应用Mybatis来实现mapper
 * @author chen
 *
 * @param <T>
 * @param <ID>
 * @param <Q>
 */
public interface PageableRepository<T extends BaseModel<ID>, ID extends Serializable, Q extends BaseQuery>
        extends BaseMybatisRepository<T, ID, Q> {

    /**
     * 列表查询
     * 
     * @param q
     * @return
     */
    List<T> getList(Q q);
}
