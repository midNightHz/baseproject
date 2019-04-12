package cn.zb.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadFactory {

	/**
	 * 线程池
	 */
	private static ExecutorService EXCUTOR;;

	private static final Object lock = new Object();

	public static ExecutorService getExcutor() {
		if (EXCUTOR == null) {
			synchronized (lock) {
				if (EXCUTOR == null)
					EXCUTOR = new ThreadPoolExecutor(50, 500, 0L, TimeUnit.MILLISECONDS,
							new LinkedBlockingQueue<>(500));
			}
		}
		return EXCUTOR;
	}

	public static void excute(Runnable r) {
		getExcutor().execute(r);
	}

}
