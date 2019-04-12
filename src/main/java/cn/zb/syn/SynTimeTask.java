package cn.zb.syn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.zb.syn.constants.SynTaskGroup;
import cn.zb.syn.interfaces.ISynService;
import cn.zb.utils.BeanFactory;
import cn.zb.utils.ThreadFactory;

@Service
public class SynTimeTask {

	private static Logger logger = LoggerFactory.getLogger(SynTimeTask.class);

	static {

		Runnable r = new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				while (true) {
					sendToCloudTask();
					try {
						Thread.sleep(60 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		};
		ThreadFactory.excute(r);

	}

	@SuppressWarnings("rawtypes")

	// @Scheduled(cron = "0 0/1 * * * ?")
	public static void sendToCloudTask() {

		/**
		 * 2019年3月16日同步策略修改 1、商品等同步资料有限同步 2、订单另外起一个线程同步，只有基础资料同步完成以后才进行同步
		 * 3、订单有进行同步时，跳过当前线程的订单同步
		 */

		// logger.info("开始同步信息");

		Map<String, ISynService> synServices = BeanFactory.getApplicationContext().getBeansOfType(ISynService.class);

		if (synServices.isEmpty()) {
			logger.warn("未找到需要同步的调度任务");
			return;
		}
		Set<Entry<String, ISynService>> entrys = synServices.entrySet();

		List<ISynService> services = new ArrayList<>();

		for (Entry<String, ISynService> entry : entrys) {

			if (entry == null) {
				continue;
			}
			ISynService synService = entry.getValue();

			if (synService == null) {
				continue;
			}
			services.add(synService);

		}
		// 进行排序 按顺序同步
		services.sort((e1, e2) -> e1.order().compareTo(e2.order()));

		sendToClousTaskPrimary(services);

		sendToClousTaskSecondary(services);
	}

	private static void sendToClousTaskPrimary(@SuppressWarnings("rawtypes") List<ISynService> services) {
		services.forEach(synService -> {
			try {
				if (synService.group() == SynTaskGroup.PRIMARY) {
					if (synService.needRun())
						synService.sendToCloud();
					// synService.getLogger().info("同步成功 target:{}",
					// synService.getClass().getSimpleName());
				}
			} catch (Exception e) {
				e.printStackTrace();
				synService.getLogger().error("同步数据失败：{},target:{}", e.getMessage(),
						synService.getClass().getSimpleName());
			}

		});
	}

	private static Boolean secondaryIsRunning = false;;

	private static void sendToClousTaskSecondary(@SuppressWarnings("rawtypes") List<ISynService> services) {
		synchronized (secondaryIsRunning) {

			if (secondaryIsRunning) {
				return;
			} else {
				secondaryIsRunning = true;
			}

		}
		Runnable r = new Runnable() {

			@Override
			public void run() {
				try {
					services.forEach(synService -> {
						try {
							if (synService.group() == SynTaskGroup.SECONDARY) {
								if (synService.needRun())
									synService.sendToCloud();
							}
						} catch (Exception e) {
							synService.getLogger().error("同步数据失败：{},target:{}", e.getMessage(),
									synService.getClass().getSimpleName());
						}
					});
				} finally {

					secondaryIsRunning = false;
				}

			}
		};
		ThreadFactory.excute(r);

	}

}
