package cn.zb.syn.entity;

import java.io.Serializable;
import java.util.Date;


import cn.zb.base.entity.BaseEntity;

public  interface DownEntity<ID extends Serializable> extends BaseEntity<ID> {


	public Date getSynTime();


	public void setSynTime(Date synTime) ;
	

}
