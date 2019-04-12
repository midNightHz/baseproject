package cn.zb.base.entity;

import java.io.Serializable;

/**
 * 带状态的表单
 * 
 * @author chen
 *
 */
public interface HasStatusEntity<ID extends Serializable>  extends BaseEntity<ID>{
	/**
	 * 获取订单状态
	 * 
	 * @return
	 */
	Integer getStatus();

	/**
	 * 设置订单状态
	 * 
	 * @param status
	 */
	void setStatus(Integer status);

}
