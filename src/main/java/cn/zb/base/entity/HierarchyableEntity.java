package cn.zb.base.entity;

import java.io.Serializable;

/**
 * 具有层级的实体类
 * 
 * @author chen
 *
 */

public interface HierarchyableEntity<ID extends Serializable> extends BaseEntity<ID> {

	public ID getParentId();

	public void setParentId(ID parentId);

}
