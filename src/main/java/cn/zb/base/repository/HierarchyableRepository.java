package cn.zb.base.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Sort;

import cn.zb.base.entity.HierarchyableEntity;

/**
 * 
 * @author chen
 *
 * @param <T>
 * @param <ID>
 */
public interface HierarchyableRepository<T extends HierarchyableEntity<ID>, ID extends Serializable>
/* extends JpaRepository<T, ID> */ {
	/**
	 * 获取当前对象的子层级
	 * 
	 * @return
	 */
	List<T> getByParentId(ID id);

	/**
	 * 
	 * @param sort
	 * @param id
	 * @return
	 */
	List<T> getByParentId(Sort sort, ID id);
	

	T findOne(ID id);

}
