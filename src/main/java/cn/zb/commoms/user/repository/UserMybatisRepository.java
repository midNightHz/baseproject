package cn.zb.commoms.user.repository;

import org.apache.ibatis.annotations.Mapper;

import cn.zb.base.repository.PageableRepository;
import cn.zb.commoms.user.entity.User;
import cn.zb.commoms.user.model.UserModel;
import cn.zb.commoms.user.query.UserQuery;

@Mapper
public interface UserMybatisRepository extends PageableRepository<UserModel, Integer, UserQuery> {
	
    void updateLoginTime(User user);
	
}
