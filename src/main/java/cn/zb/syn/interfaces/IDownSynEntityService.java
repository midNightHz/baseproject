package cn.zb.syn.interfaces;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.zb.base.entity.EntityUtil;
import cn.zb.base.repository.BaseJpaRepository;
import cn.zb.base.service.BaseService;
import cn.zb.config.ISystemConfig;
import cn.zb.syn.constants.SynTaskConstants;
import cn.zb.syn.entity.DownEntity;
import cn.zb.syn.repository.DownEntityJpaRepository;
import cn.zb.syn.repository.SynStatusRepository;
import cn.zb.utils.BeanFactory;
import cn.zb.utils.DateUtil;

/**
 * 同步的基础类，从云平台上更新数据
 * 
 * @author chen
 *
 * @param <T>
 * @param <ID>
 */
public interface IDownSynEntityService<T extends DownEntity<ID>, ID extends Serializable>
		extends BaseService<T, ID>, IBaseSynService {

	DownEntityJpaRepository<T, ID> getDownEntityJpaRepository();

	@Override
	default BaseJpaRepository<T, ID> getJpaRepository() {
		return getDownEntityJpaRepository();
	}

	int DEFAULT_RETRY_TIME = 8;

	/**
	 * 下载实体类的url
	 * 
	 * @return
	 */
	String getDownUrl();

	/**
	 * 同步完成后上传同步状态的url要求格式 *******
	 * 
	 * @return
	 */
	String getUpSynStatusUrl();

	/**
	 * 下载实体类的业务逻辑
	 * 
	 * @throws Exception
	 */
	default void down() throws Exception {
		String url = getDownUrl();
		Date lastSynDate = getLastSynTime();
		if (lastSynDate == null) {
			lastSynDate = DateUtil.processTime(new Date(), "1M", true);
		} else {
			lastSynDate = DateUtil.processTime(lastSynDate, "1m", true);
		}

		url = url.replace("DATE", lastSynDate.getTime() + "");
		String result = getCommonSynService().sendToCloudGet(url, new HashMap<>(), true);

		JSONObject json = JSONObject.parseObject(result);

		int code = json.getIntValue("code");
		if (getLogger().isDebugEnabled()) {
			getLogger().debug("url:{},result:{}", url, result);
		}
		if (code != 1) {
			throw new Exception(json.getString("msg"));
		}
		List<T> list = JSON.parseArray(json.getString("msg"), entityClass());

		saveDownEntity(list);

		sendSynStatus(lastSynDate);

	}

	default void sendSynStatus(Date lastSynDate) {

		String url = getUpSynStatusUrl();

		if (StringUtils.isBlank(url)) {
			return;
		}
		List<T> list = getDownEntityJpaRepository().findBySynTimeGreaterThan(lastSynDate);

		if (list == null || list.size() == 0) {
			return;
		}
		int retryTime = retryTime();
		String body = JSONArray.toJSONString(list);
		for (int i = 0; i < retryTime; i++) {
			try {
				String result = getCommonSynService().sendToCloudPostBody(url, body, true);
				JSONObject json = JSONObject.parseObject(result);
				int code = json.getIntValue("code");
				if (code == 1) {
					break;
				}
				getLogger().warn("第{}次同步数据状态失败：{}", (i + 1), json.getString("msg"));
			} catch (Exception e) {
				getLogger().warn("第{}次同步数据状态失败：{}", (i + 1), e.getMessage());
			}
		}

	}

	default int retryTime() {
		return DEFAULT_RETRY_TIME;
	}

	/**
	 * 保存的业务逻辑
	 * 
	 * @param t
	 */
	void saveDownEntity(List<T> list);

	/**
	 * 获取最后一次更新数据的时间
	 * 
	 * @return
	 */
	default Date getLastSynTime() throws Exception {
		SynStatusRepository repository = BeanFactory.getBean(SynStatusRepository.class);
		Class<?> entityClass = entityClass();
		String tableName = EntityUtil.getTableName(entityClass);
		return repository.getLastSynDate(tableName, "syn_time");

	}

	default ICommonSynService getCommonSynService() throws Exception {
		return BeanFactory.getBean(ICommonSynService.class);
	}

	default boolean needRun() {
		ISystemConfig config = null;
		try {
			config = BeanFactory.getBean(ISystemConfig.class);
			if (config == null) {
				return true;
			}

			String tableName = EntityUtil.getTableName(entityClass()).toUpperCase();

			return config.getBoolean(tableName + SynTaskConstants.DOWN_TASK_ID_SUFFIX, true);
		} catch (Exception e) {
			getLogger().warn("获取是否需要同步配置错误：{}", e.getMessage());
			return true;
		}

	}
}
