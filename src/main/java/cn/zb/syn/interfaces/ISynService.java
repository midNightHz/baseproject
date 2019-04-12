package cn.zb.syn.interfaces;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.http.HttpUtil;
import cn.zb.base.constants.BaseConstants;
import cn.zb.base.entity.EntityUtil;
import cn.zb.base.entity.SynEntity;
import cn.zb.base.repository.BaseJpaRepository;
import cn.zb.base.service.BaseService;
import cn.zb.config.ISystemConfig;
import cn.zb.syn.constants.SynConstants;
import cn.zb.syn.constants.SynStrategy;
import cn.zb.syn.constants.SynTaskConstants;
import cn.zb.syn.constants.SynTaskGroup;
import cn.zb.syn.repository.SynStatusRepository;
import cn.zb.syn.synlog.entity.LogSyn;
import cn.zb.syn.synlog.service.SynLogService;
import cn.zb.utils.BeanFactory;
import cn.zb.utils.ThreadLocalUtils;
import cn.zb.utils.Time;

/**
 * 
 * @author chen 同步服务
 * @param <T>
 * @param <ID>
 */
public interface ISynService<T extends SynEntity<ID>, ID extends Serializable> extends BaseService<T, ID> ,IBaseSynService{

	BaseSynJpaRepository<T, ID> getBaseSynJpaRepository();

	@Override
	default BaseJpaRepository<T, ID> getJpaRepository() {
		return getBaseSynJpaRepository();
	}

	int SEGMENT_SIZE = 1000;

	/**
	 * 发送信息到云端
	 * 
	 * @throws Exception
	 */
	default void sendToCloud() throws Exception {

		SynStatusRepository synStatusRepository = BeanFactory.getBean(SynStatusRepository.class);
		if (strategy() == SynStrategy.DELETE)
			synStatusRepository.updateSynStatus(EntityUtil.getTableName(entityClass()), BaseConstants.YES);

		sendToCloud(0);
	}

	default void sendToCloud(int pageIndex) throws Exception {

		Time time = new Time();

		Integer pageSize = ThreadLocalUtils.getParam("synPageSize", Integer.class);

		if (pageSize == null) {
			pageSize = SEGMENT_SIZE;
		}

		Pageable page = new PageRequest(pageIndex, pageSize);

		time.start();

		List<T> list = null;
		// 同步策略 分为同步后删除和同步后修改状态
		if (strategy() == SynStrategy.DELETE)
			list = getBaseSynJpaRepository().findAllByIsSyn(BaseConstants.YES, page);
		else
			list = getBaseSynJpaRepository().findAllByIsSyn(BaseConstants.NO, page);

		time.stop();

		if (list == null || list.size() == 0) {
			getLogger().info("没有需同步的数据");
			return;
		}
		if (getLogger().isDebugEnabled()) {
			getLogger().debug("查询数据：{} ms,数据条数 :{}", time.getTime(), list.size());
		}
		try {

			String url = getSynUrl();
			time.start();
			// 发送数据到云平台
			sendToCloudPost(list, url, true);
			time.stop();
			// debug日志记录
			if (getLogger().isDebugEnabled()) {
				getLogger().debug("发送数据到云平台,运行时间：{}", time.getTime());
			}
			time.start();
			// 数据删除//修改状态
			if (strategy() == SynStrategy.DELETE) {
				getBaseSynJpaRepository().delete(list);
			} else {
				list.forEach(e -> {
					e.setIsSyn(BaseConstants.YES);
				});
				getBaseSynJpaRepository().save(list);
			}
			time.stop();
			if (getLogger().isDebugEnabled()) {
				getLogger().debug("更新/删除数据,运行时间：{}", time.getTime());
			}
		} catch (Exception e) {
			pageIndex++;
			getLogger().error("pageindex:{} ,error:{}", pageIndex, e.getMessage());
			// throw e;
		}
		sendToCloud(pageIndex);

	}

	// 同步的url
	String getSynUrl();

	/**
	 * 
	 * @param list
	 *            同步的列表
	 * @param url
	 *            同步的地址
	 * @param retry
	 *            是否允许重试
	 * @return
	 * @throws Exception
	 */
	default JSONObject sendToCloudPost(List<T> list, String url, boolean retry) throws Exception {

		String token = getToken(!retry);

		if (StringUtils.isBlank(token)) {
			throw new Exception("获取token异常");
		}

		url = url.replace("TOKEN", token);
		String body = JSONObject.toJSONString(list);
		LogSyn logSyn = new LogSyn(new Date(), url, body, SynConstants.SYN_FAILED, null);
		String result = null;
		try {
			if (getLogger().isDebugEnabled()) {
				getLogger().debug("请求路径：{}", url);
			}
			result = HttpUtil.post(url, body);
			if (getLogger().isDebugEnabled()) {
				getLogger().debug("请求结果数据：{}", result);
			}
			logSyn.setResultMessage(result);

			JSONObject json = JSON.parseObject(result);
			Integer state = json.getIntValue("code");
			// Token异常的时候重新获取一次token
			if (state == 100) {
				if (retry) {

					//getToken(true);

					return sendToCloudPost(list, url, false);
				}

				throw new Exception(json.getString("msg"));

			}

			if (state != 1) {
				throw new Exception(json.getString("msg"));
			}
			logSyn.setResultType(SynConstants.SYN_SUCCESS);
			return json;

		} catch (Exception e) {
			if (result == null)
				logSyn.setResultMessage(e.getMessage());
			throw e;
		} finally {

			SynLogService.saveSynLog(logSyn);
		}

	}

	/**
	 * 获取云平台token
	 * 
	 * @param flush
	 *            是否更新
	 * @return
	 * @throws Exception
	 */
	default String getToken(boolean flush) throws Exception {

		ICommonSynService commonSynService = BeanFactory.getBean(ICommonSynService.class);

		if (commonSynService == null) {
			throw new Exception("获取token异常");
		}
		String token = commonSynService.getToken(flush);

		if (token == null) {
			throw new Exception("获取token异常");
		}

		return token;
	}

	default void saveSynStatus(List<T> list) throws Exception {
		list.forEach(e -> {
			e.setIsSyn(BaseConstants.YES);
		});
		getJpaRepository().save(list);
	}

	/**
	 * 当前同步服务执行顺序
	 * 
	 * @return
	 */
	Integer order();

	/**
	 * 任务分组
	 * 
	 * @return
	 */
	default SynTaskGroup group() {
		return SynTaskGroup.PRIMARY;
	}
	/**
	 * 同步策略 ：DELETE 同步完成以后把同步完成的数据删除
	 * MODIFY_SYN_STATUS 同步完成后将同步状态修改成1
	 * @return
	 */
	default SynStrategy strategy() {
		return SynStrategy.DELETE;
	}
	
	default boolean needRun() {

		ISystemConfig config = null;
		try {
			config = BeanFactory.getBean(ISystemConfig.class);
			if (config == null) {
				return true;
			}

			String tableName = EntityUtil.getTableName(entityClass()).toUpperCase();

			return config.getBoolean(tableName + SynTaskConstants.SYN_TASK_ID_SUFFIX, true);
		} catch (Exception e) {
			getLogger().warn("获取是否需要同步配置错误：{}", e.getMessage());
			return true;
		}

	}

}
