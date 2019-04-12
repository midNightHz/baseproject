package cn.zb.commoms.user.query;

import cn.zb.base.controller.CallContext;
import cn.zb.base.query.BaseQuery;
import cn.zb.page.Pageable;

public class UserQuery extends BaseQuery {
	// 用户名
	private String userName;
	// 单位名
	private String corpName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	@Override
	public void init(Pageable pageable, CallContext callContext) {
		super.init(pageable);
	}

}
