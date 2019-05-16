package cn.zb.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadFactory {

	/**
	 * 线程池
	 */
	public static final ExecutorService EXCUTOR = new ThreadPoolExecutor(50, 500, 0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<>(500));

	public static void excute(Runnable r) {
		EXCUTOR.execute(r);
	}

}
