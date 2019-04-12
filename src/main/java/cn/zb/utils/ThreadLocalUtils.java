package cn.zb.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName: ThreadLocalUtils
 * @Description:线程本地变量工具
 * @author: 陈军
 * @date: 2019年3月13日 下午4:52:25
 * 
 * @Copyright: 2019 www.zb-tech.com Inc. All rights reserved.
 *
 */
public class ThreadLocalUtils {

	private final static ThreadLocal<Map<String, Object>> THREAD_PARAMS = new ThreadLocal<>();

	/**
	 * 
	 * @Title: put @Description:添加参数到线程本地变量 @author:陈军 @date 2019年3月13日
	 *         下午4:52:41 @param paramsName 参数名 @param param void @throws
	 */
	public static void put(String paramsName, Object param) {
		Map<String, Object> map = THREAD_PARAMS.get();
		if (map == null) {
			map = new HashMap<>();
		}
		THREAD_PARAMS.set(map);
		map.put(paramsName, param);
	}

	public static void remove(String paramsName) {
		Map<String, Object> map = THREAD_PARAMS.get();
		if (map == null) {
			return;
		}
		map.remove(paramsName);
	}

	/**
	 * 
	 * @Title: getParam @Description: 从线程本地变量中获取参数 @author:陈军 @date 2019年3月13日
	 *         下午4:53:02 @param paramsName 参数名 @return Object @throws
	 */
	public static Object getParam(String paramsName) {
		Map<String, Object> map = THREAD_PARAMS.get();
		if (map == null) {
			return null;
		}
		return map.get(paramsName);
	}

	/**
	 * 
	 * @Title: getParam @Description:
	 *         从线程本地变量中获取参数，如果获取的参数类型和CL不一致时，则返回空 @author:陈军 @date 2019年3月13日
	 *         下午4:53:30 @param paramsName @param cl @return T @throws
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getParam(String paramsName, Class<T> cl) {
		Object obj = getParam(paramsName);
		if (obj != null) {
			if (obj.getClass().isAssignableFrom(cl)) {
				return (T) obj;
			}
			return null;
		}
		return null;
	}

	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			Runnable r = new Runnable() {

				@Override
				public void run() {

					Long id = Thread.currentThread().getId();

					Integer randInt = (int) (Math.random() * 1000);
					System.out.println(id + "------" + randInt);
					put("threadint", randInt);

					Integer result = (getParam("threadint", Integer.class));

					System.out.println("out" + "-----" + id + "----" + result);
				}
			};

			ThreadFactory.excute(r);

		}

	}

}
