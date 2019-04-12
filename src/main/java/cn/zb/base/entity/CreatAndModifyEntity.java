package cn.zb.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;

/**
 * 带创建时间和修改时间的实体类
 * 
 * @author chen
 *
 * @param <ID>
 */
@MappedSuperclass
public abstract class CreatAndModifyEntity<ID extends Serializable> implements BaseEntity<ID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2771042335256161624L;

	/**
	 * 创建时间
	 */
	protected Date creatDate;

	protected String createUser;

	/**
	 * 修改时间
	 */
	protected Date modifyDate;

	protected String modifyUser;

	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
}
