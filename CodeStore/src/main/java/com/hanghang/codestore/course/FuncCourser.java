package com.hanghang.codestore.course;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FuncCourser {

	private FuncCourser() {
	}

	private static ExecutorService executor = Executors.newCachedThreadPool(
			new ThreadFactory() {
				int nCount = 0;
				public Thread newThread(Runnable task) {
					nCount++;
					Thread invokeThread = new Thread(task);
					invokeThread.setName("Invoker-thread-" + nCount);
					invokeThread.setDaemon(true);
					return invokeThread;
				}
			}
		);

	/***************************************************************************
	 * 目标方法无返回值使用该方法调用
	 * @param task 调用代码
	 * @param unit 超时时间类
	 * @param timeout 时间
	 * @throws TimeoutException 调用超过指定的时间抛出此异常
	 */
	public static void call(Runnable task, TimeUnit unit, long timeout) throws TimeoutException {
		Future<?> futureResult = executor.submit(task);
		try {
			futureResult.get(timeout, unit);
		} catch (TimeoutException e) {
			throw new TimeoutException("invoke timeout!");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/***************************************************************************
	 * 目标方法有返回值时使用该方法调用
	 * @param <T>
	 * @param task 调用代码
	 * @param unit 超时时间类型
	 * @param timeout 时间
	 * @return 被调用函数的返回值
	 * @throws TimeoutException 调用超过指定时间时抛出此异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> T call(Callable<?> task, TimeUnit unit, long timeout)
			throws TimeoutException {
		Future<?> futureResult = executor.submit(task);
		Object callRet = null;
		try {
			callRet = futureResult.get(timeout, unit);
		} catch (TimeoutException e) {
			throw new TimeoutException("invoke timeout!");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return (T) callRet;
	}

	public static void main(String[] args) throws TimeoutException {
		FuncCourser.call(new Runnable() {

			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}

		}, TimeUnit.MILLISECONDS, 1000);

		String te = FuncCourser.call(new Callable<String>() {
			public String call() throws Exception {
				Thread.sleep(1000);
				return "tetssss";
			}
		}, TimeUnit.MILLISECONDS, 1000l);
		
		System.out.println(te);

	}

}
