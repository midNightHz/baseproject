package cn.zb.base.entity;

import java.io.Serializable;

public interface SynEntity<ID extends Serializable> extends BaseEntity<ID> {

	Short getIsSyn();

	void setIsSyn(Short isSyn);

}
