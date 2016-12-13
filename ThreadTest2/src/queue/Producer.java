/**
 * Project Name:ThreadTest2
 * File Name:Producer.java
 * Package Name:queue
 * Date:2016年7月13日下午5:15:39
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/
/**
 * 海风app在线学习平台
 * @author: no_relax
 * @Title: Producer.java 
 * @Package: queue
 * @date: 2016年7月13日-下午5:15:39
 * @version: Vphone1.3.0
 * @copyright: 2016上海风创信息咨询有限公司-版权所有
 * 
 */

package queue;

import java.util.concurrent.ArrayBlockingQueue;

/** 
* @ClassName: Producer 
* @Description: TODO(生产者) 
* @author no_relax 
* @date 2016年7月13日 下午5:15:39 
*  
*/
public class Producer implements Runnable{
	//声明一个阻塞队列
	private ArrayBlockingQueue<? super Number> queue;
	//构造函数初始化时，传入这个队列
	public Producer(ArrayBlockingQueue<? super Number> queue) {
		this.queue=queue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				producer();                 
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				
			}
		}
		
	}
	
	void producer(){
		/** 
         * put()方法是如果容器满了的话就会把当前线程挂起 
         * offer()方法是容器如果满的话就会返回false，也正是我在前一篇中实现的那种策略。 
         */ 
		try {
//			queue.put(123);
			boolean offer = queue.offer(123);
			System.out.println("producer:"+123+">>>>and return:"+offer);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	public static void main(String[] args) {
		int capacity = 10;  
        ArrayBlockingQueue queue = new ArrayBlockingQueue<>(1);
        new Thread(new Producer(queue)).start();
        new Thread(new Producer(queue)).start();
        new Thread(new Consumer(queue)).start();
//        new Thread(new Consumer(queue)).start();
	}

}

