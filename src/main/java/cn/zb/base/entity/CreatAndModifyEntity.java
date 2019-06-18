package cn.zb.base.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 带创建时间和修改时间的实体类
 * 
 * @author chen
 *
 * @param <ID>
 */
/**
 * 
 * @title:CreatAndModifyEntity.java
 * @ClassName: CreatAndModifyEntity
 * @Description:
 * @author: 陈军
 * @date: 2019年6月18日 上午10:31:00
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved.
 *
 */
public interface CreatAndModifyEntity<ID extends Serializable> extends BaseEntity<ID> {

	Date getCreateTime();

	Long getCreateUser();

	void setCreateTime(Date createTime);

	void setCreateUser(Long createUser);

	Date getLastModifyTime();

	Long getLastModifyUser();

	void setLastModifyTime(Date lastModifyTime);

	void setLastModifyUser(Long lastModifyUser);

}
