package cn.zb.syn.synlog.service;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.zb.base.repository.BaseJpaRepository;
import cn.zb.base.repository.PageableRepository;
import cn.zb.syn.synlog.entity.LogSyn;
import cn.zb.syn.synlog.model.LogSynModel;
import cn.zb.syn.synlog.query.LogSynQuery;
import cn.zb.syn.synlog.repository.LogRepository;
import cn.zb.syn.synlog.repository.LogSynMybatisRepository;
import cn.zb.utils.BeanFactory;
import cn.zb.utils.FileUtils;
import cn.zb.utils.ThreadFactory;

/**
 * 同步日志的保存，采用异步的方式保存日志
 * 
 * @author chen
 *
 */

@Service
public class SynLogService implements ISynLogService {

	private static Logger logger = LoggerFactory.getLogger(SynLogService.class);

	private static final Object lock = new Object();

	private static LogRepository logRepostiroy;

	private final static Queue<LogSyn> LOG_QUEUE = new LinkedBlockingQueue<>();

	public static LogRepository getLogRepository() {
		if (logRepostiroy == null) {
			synchronized (lock) {
				logRepostiroy = BeanFactory.getBean(LogRepository.class);
			}
		}
		return logRepostiroy;
	}

	static {
		Runnable r = new Runnable() {

			@Override
			public void run() {
				while (true) {
					flushLog();
				}
			}
		};
		ThreadFactory.excute(r);
	}

	public static void saveSynLog(LogSyn log) {
		if (log != null) {
			LOG_QUEUE.offer(log);
		}
	}

	private static void flushLog() {
		LogSyn log = LOG_QUEUE.poll();
		if (log == null) {
			return;
		}
		// int retryTime = 3;S
		// for (int i = 0; i < retryTime; i++) {
		try {

			String synPrams = log.getSynParams();

			String fileName = "../../synParams/" + UUID.randomUUID().toString().toUpperCase() + ".txt";
			File file = new File(fileName);
			FileUtils.createNewFile(fileName);
			FileUtils.writeStringToFile(synPrams, fileName, false, Charset.forName("utf-8"));
			log.setSynParams(file.getAbsolutePath());
			getLogRepository().save(log);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存日志失败：{},error{}", JSONObject.toJSON(log), e.getMessage());

		}

	}

	@Override
	public BaseJpaRepository<LogSyn, Integer> getJpaRepository() {
		return getLogRepository();
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Autowired
	private LogSynMybatisRepository logSynMybatisRepository;

	@Override
	public PageableRepository<LogSynModel, Integer, LogSynQuery> getPageableRepository() {
		return logSynMybatisRepository;
	}

}
