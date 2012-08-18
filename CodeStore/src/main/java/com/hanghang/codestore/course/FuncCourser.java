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
	 * Ŀ�귽���޷���ֵʹ�ø÷�������
	 * @param task ���ô���
	 * @param unit ��ʱʱ����
	 * @param timeout ʱ��
	 * @throws TimeoutException ���ó���ָ����ʱ���׳����쳣
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
	 * Ŀ�귽���з���ֵʱʹ�ø÷�������
	 * @param <T>
	 * @param task ���ô���
	 * @param unit ��ʱʱ������
	 * @param timeout ʱ��
	 * @return �����ú����ķ���ֵ
	 * @throws TimeoutException ���ó���ָ��ʱ��ʱ�׳����쳣
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
