package cn.itcast.heima2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreTest {
	public static void main(String[] args) {
		final Lock lock = new ReentrantLock();
		ExecutorService service = Executors.newCachedThreadPool();
		final  Semaphore sp = new Semaphore(3);
		for(int i=0;i<10;i++){
			Runnable runnable = new Runnable(){
					public void run(){
					try {
						lock.lock();
						sp.acquire();//获取信号量后，才可以执行下面代码
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					System.out.println("线程" + Thread.currentThread().getName() + 
							"进入，当前已有" + (3-sp.availablePermits()) + "个并发");
					System.out.println("线程" + Thread.currentThread().getName() + 
							"即将离开");					
					sp.release();
					//下面代码有时候执行不准确，因为其没有和上面的代码合成原子单元
					System.out.println("线程" + Thread.currentThread().getName() + 
							"已离开，当前已有" + (3-sp.availablePermits()) + "个并发");					
					try {
						Thread.sleep((long)(Math.random()*10000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			service.execute(runnable);			
		}
	}

}
