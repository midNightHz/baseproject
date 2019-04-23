package cn.zb.commoms.user.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zb.base.repository.PageableRepository;
import cn.zb.commoms.user.entity.User;
import cn.zb.commoms.user.model.UserModel;
import cn.zb.commoms.user.query.UserQuery;
import cn.zb.commoms.user.repository.UserMybatisRepository;
import cn.zb.commoms.user.repository.UserRepository;
import cn.zb.commoms.user.service.IUserService;
import cn.zb.config.AppConfig;
import cn.zb.syn.constants.SynConstants;
import cn.zb.syn.interfaces.BaseSynJpaRepository;
import cn.zb.utils.ScretUtil;

@Service
public class UserService implements IUserService {

	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMybatisRepository userMybatisRepository;

	@SuppressWarnings("deprecation")
	@Autowired
	private AppConfig appConfig;

	@Override
	public Logger getLogger() {
		return logger;
	}

	public User getUser(String userName, String password) throws Exception {

		return userRepository.findByWorkNoAndPassword(userName, ScretUtil.encryPassword(password));

	}


	@Override
	public PageableRepository<UserModel, Integer, UserQuery> getPageableRepository() {
		return userMybatisRepository;
	}

	@Override
	public BaseSynJpaRepository<User, Integer> getBaseSynJpaRepository() {
		return userRepository;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String getSynUrl() {
		return appConfig.getCloudUrl() + SynConstants.PERSON_SYN_URL;
	}


	@Override
	public Integer order() {
		return 200;
	}

}
