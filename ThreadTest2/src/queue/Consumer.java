/**
 * Project Name:ThreadTest2
 * File Name:Consumer.java
 * Package Name:queue
 * Date:2016年7月13日下午5:23:00
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/
/**
 * 海风app在线学习平台
 * @author: no_relax
 * @Title: Consumer.java 
 * @Package: queue
 * @date: 2016年7月13日-下午5:23:00
 * @version: Vphone1.3.0
 * @copyright: 2016上海风创信息咨询有限公司-版权所有
 * 
 */

package queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 
 * @ClassName: Consumer
 * @Description: TODO(消费者模型)
 * @author no_relax
 * @date 2016年7月13日 下午5:23:00
 * 
 */
public class Consumer implements Runnable {
	private ArrayBlockingQueue<? super Number> queue;

	public Consumer(ArrayBlockingQueue<? super Number> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while (true) {
			consumer();
		}

	}

	void consumer() {
		/**
		 * take()方法和put()方法是对应的，从中拿一个数据，如果拿不到线程挂起
		 * poll()方法和offer()方法是对应的，从中拿一个数据，如果没有直接返回null
		 */
		try {
			Integer take = (Integer) queue.take();
			System.out.println("Consumer:" + take);
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
	}

}
