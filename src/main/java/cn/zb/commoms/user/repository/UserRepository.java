package cn.zb.commoms.user.repository;


import cn.zb.commoms.user.entity.User;
import cn.zb.syn.interfaces.BaseSynJpaRepository;

public interface UserRepository extends BaseSynJpaRepository<User, Integer> {

	User findByWorkNoAndPassword(String workNo, String password);

	User findByWorkNo(String workNo);

}
