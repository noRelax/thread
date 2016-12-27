package cn.itcast.heima2;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//收麦子事例：种菜游戏
public class CallableAndFuture {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		Future<String> future = threadPool.submit(new Task());
		System.out.println("等待结果");
		try {
			System.out.println("拿到结果：" + future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
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
		ExecutorService threadPool2 = Executors.newFixedThreadPool(10);
		CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(threadPool2);
		for (int i = 1; i <= 10; i++) {
			final int seq = i;
			completionService.submit(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					Thread.sleep(new Random().nextInt(5000));
					return seq;
				}
			});
		}
		for (int i = 0; i < 10; i++) {
			try {
				System.out.println(completionService.take().get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	static class Task implements Callable<String> {
		@Override
		public String call() throws Exception {

			return "返回结果";
		}

	}

}
