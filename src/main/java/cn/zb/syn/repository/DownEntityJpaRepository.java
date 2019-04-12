package cn.zb.syn.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import cn.zb.base.repository.BaseJpaRepository;
import cn.zb.syn.entity.DownEntity;

@NoRepositoryBean
public interface DownEntityJpaRepository<T extends DownEntity<ID>, ID extends Serializable>
		extends BaseJpaRepository<T, ID> {

	List<T> findBySynTimeGreaterThan(Date synTime);

}
