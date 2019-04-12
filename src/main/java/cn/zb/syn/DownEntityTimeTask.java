package cn.zb.syn;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.zb.syn.interfaces.IDownSynEntityService;
import cn.zb.utils.BeanFactory;

@Service
public class DownEntityTimeTask {

	private static final Logger logger = LoggerFactory.getLogger(DownEntityTimeTask.class);

	@SuppressWarnings("rawtypes")
	@Scheduled(cron = "0 0/1 * * * ?")
	public void downEntityTask() {

		Map<String, IDownSynEntityService> synServices = BeanFactory.getApplicationContext()
				.getBeansOfType(IDownSynEntityService.class);
		if (synServices.isEmpty()) {
			logger.warn("未找到需要同步的调度任务");
			return;
		}

		Set<Entry<String, IDownSynEntityService>> entrys = synServices.entrySet();

		entrys.forEach(entry -> {

			IDownSynEntityService service = entry.getValue();
			try {
				if (service.needRun())
					service.down();
			} catch (Exception e) {
				service.getLogger().error("下载数据失败：{}	", e.getMessage());
			}

		});

	}

}
