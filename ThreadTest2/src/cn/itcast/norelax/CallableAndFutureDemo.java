/**
 * Project Name:ThreadTest2
 * File Name:CallableAndFutureDemo.java
 * Package Name:cn.itcast.norelax
 * Date:2016年6月1日下午3:58:43
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/
/**
 * 海风app在线学习平台
 * @author: no_relax
 * @Title: CallableAndFutureDemo.java 
 * @Package: cn.itcast.norelax
 * @date: 2016年6月1日-下午3:58:43
 * @version: Vphone1.3.0
 * @copyright: 2016上海风创信息咨询有限公司-版权所有
 * 
 */

package cn.itcast.norelax;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @ClassName: CallableAndFutureDemo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author no_relax
 * @date 2016年6月1日 下午3:58:43
 * 
 */
public class CallableAndFutureDemo {

	/**
	 * TODO(这里用一句话描述这个方法的作用)
	 * 
	 * @author: no_relax
	 * @Title: main
	 * @param args
	 *            void
	 * @since Vphone1.3.0
	 */
	public static void main(String[] args) {
		// 1:
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		Task task = new Task();
		System.out.println("begin...");
		Future<String> result = threadPool.submit(task);
		try {
			System.out.println(result.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * 当使用ExecutorService启动了多个Callable后，每个Callable会产生一个Future，
		 * 我们需要将多个Future存入一个线性表，用于之后处理数据。当然，还有更复杂的情况，有5个生产者线程，
		 * 每个生产者线程都会创建任务，所有任务的Future都存放到同一个线性表中。另有一个消费者线程，从线性表中取出Future进行处理。
		 * CompletionService正是为此而存在，它是一个更高级的ExecutorService，它本身自带一个线程安全的线性表，
		 * 无需用户额外创建。
		 * 它提供了2种方法从线性表中取出结果，poll()是非阻塞的，若目前无结果，返回一个null，线程继续运行不阻塞。take()是阻塞的，
		 * 若当前无结果，则线程阻塞，直到产生一个结果，被取出返回，线程才继续运行。
		 */
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
		ExecutorCompletionService<Integer> executorCompletionService = new ExecutorCompletionService<Integer>(newFixedThreadPool);
		for (int i = 0; i < 10; i++) {
			final Integer result1 = i;
			executorCompletionService.submit(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					// 提交数据
					return result1;
				}
			});
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}

		for (int i = 0; i < 10; i++) {
			try {
				// 拿数据
				Integer integer = executorCompletionService.take().get();
				System.out.println(integer);
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

	// 构建一个Task类，实现Callable接口，返回类型为String
	static class Task implements Callable<String> {
		@Override
		public String call() throws Exception {
			return "hello";
		}

	}

}