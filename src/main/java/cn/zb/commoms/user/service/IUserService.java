package cn.zb.commoms.user.service;

import cn.zb.base.service.BaseService;
import cn.zb.base.service.PageableService;
import cn.zb.commoms.user.entity.User;
import cn.zb.commoms.user.model.UserModel;
import cn.zb.commoms.user.query.UserQuery;
import cn.zb.syn.interfaces.ISynService;

public interface IUserService
		extends BaseService<User, Integer>, PageableService<UserModel, Integer, UserQuery>, ISynService<User, Integer> {
}
