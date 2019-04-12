package cn.zb.syn.interfaces;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import cn.zb.base.entity.BaseEntity;
import cn.zb.base.repository.BaseJpaRepository;

@NoRepositoryBean
public interface BaseSynJpaRepository<T extends BaseEntity<ID>, ID extends Serializable>
		extends BaseJpaRepository<T, ID> {
	List<T> findAllByIsSyn(Short isSyn,Pageable pageable);
	
	
	//void deleteByIsSyn(Short isSyn);
	
}
