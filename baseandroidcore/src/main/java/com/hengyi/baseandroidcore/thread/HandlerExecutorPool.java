package com.hengyi.baseandroidcore.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池操作类
 * @author 繁华
 */

/**
 *
 * 查看线程池更加详细的解释请看文章	http://blog.csdn.net/u012702547/article/details/52259529
 *
 public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue)

 corePoolSize  线程池中核心线程的数量
 maximumPoolSize  线程池中最大线程数量
 keepAliveTime 非核心线程的超时时长，当系统中非核心线程闲置时间超过keepAliveTime之后，则会被回收。如果ThreadPoolExecutor的allowCoreThreadTimeOut属性设置为true，则该参数也表示核心线程的超时时长
 unit 第三个参数的单位，有纳秒、微秒、毫秒、秒、分、时、天等
 workQueue 线程池中的任务队列，该队列主要用来存储已经被提交但是尚未执行的任务。存储在这里的任务是由ThreadPoolExecutor的execute方法提交来的。
 hreadFactory  为线程池提供创建新线程的功能，这个我们一般使用默认即可
 handler 拒绝策略，当线程无法执行新任务时（一般是由于线程池中的线程数量已经达到最大数或者线程池关闭导致的），默认情况下，当线程池无法处理新线程时，会抛出一个RejectedExecutionException。
 */
public class HandlerExecutorPool {
	private static HandlerExecutorPool instance = null;
	private ExecutorService executor;

	/**
	 * 单列模式
	 * @param maxPoolSize
	 * @param queueSize
	 * @return
	 */
	public static synchronized HandlerExecutorPool getInstance(int maxPoolSize, int queueSize){
		synchronized(HandlerExecutorPool.class){
			if(instance == null){
				instance = new HandlerExecutorPool(maxPoolSize,queueSize);
			}
		}
		return instance;
	}

	public HandlerExecutorPool(int maxPoolSize, int queueSize){
		this.executor = new ThreadPoolExecutor(
				Runtime.getRuntime().availableProcessors(),
				maxPoolSize,
				120L, 
				TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(queueSize));
	}

	/**
	 * 不需要回调结果
	 * @param task
	 */
	public void execute(Runnable task){
		executor.execute(task);
	}

	/**
	 * 会回调执行的结果
	 * @param task
	 */
	public void submit(Callable<?> task){
		executor.submit(task);
	}
	
	public void stutdown(){
		if(!executor.isShutdown())
		executor.shutdown();
	}

	public void stutdownNow(){
		if(!executor.isShutdown())
			executor.shutdownNow();
	}
}
