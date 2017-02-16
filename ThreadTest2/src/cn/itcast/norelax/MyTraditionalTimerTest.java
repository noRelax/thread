
package cn.itcast.norelax;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @ClassName: MyTraditionalTimerTest
 * @Description: TODO(传统定时器)
 * @author no_relax
 * @date 2016年11月23日 上午11:31:39
 * 相隔2秒钟和相隔4秒钟交替打印文字
 */
public class MyTraditionalTimerTest {
	private static volatile int count=0;
	//定义全局变量
	public static void main(String[] args) {
		/*new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("my name is norelax!");
			}
		}, 1000,1000);*/
		
		//继承TimerTask类，重写run方法的实现逻辑
		class MyTimerTask extends TimerTask{
			@Override
			public void run() {
				count=(count+1)%2;
				System.out.println("my name is norelax!");
				new Timer().schedule(new MyTimerTask(), 2000+2000*count);
			}
		}
		new Timer().schedule(new MyTimerTask(), 2000);
		
		while(true){
			System.out.println(new Date().getSeconds());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				
			}
		}
		
	}
	
	

}
