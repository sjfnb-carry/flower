package com.mall.common.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTool {
	
	static ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 10, 300,
            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5),
            new ThreadPoolExecutor.CallerRunsPolicy());

	public static ThreadPoolExecutor getThreadPoolExecutorTool() {
		if(null==executor) {
			executor = new ThreadPoolExecutor(2, 10, 300,
					TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5),
					new ThreadPoolExecutor.CallerRunsPolicy());
		}
		return executor;
	}
	
}
