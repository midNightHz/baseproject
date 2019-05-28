package cn.zb.log;

import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @title:MybatisLoggerImpl.java
 * @ClassName: MybatisLoggerImpl
 * @Description: 自定义的mybatislogger 日志打印
 * @author: 陈军
 * @date: 2019年5月28日 上午10:47:17
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved.
 *
 */
public class MybatisLoggerImpl extends StdOutImpl {

	public MybatisLoggerImpl(String clazz) {
		super(clazz);
	}

	private static final Logger logger = LoggerFactory.getLogger(MybatisLoggerImpl.class);

	

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	@Override
	public void error(String s, Throwable e) {
		logger.error("msg:{};error:{}", s, e.getMessage());
	}

	@Override
	public void error(String s) {
		logger.error(s);
	}

	@Override
	public void debug(String s) {
		logger.debug(s);
	}

	@Override
	public void trace(String s) {
		logger.trace(s);
	}

	@Override
	public void warn(String s) {
		logger.warn(s);
	}

}