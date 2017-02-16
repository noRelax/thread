/**
 * 海风app在线学习平台
 * @author: no_relax
 * @Title: MyTraditionThreadCommunication.java 
 * @Package: cn.itcast.norelax
 * @date: 2017年2月16日-下午4:22:38
 * @version: Vpad1.2.0
 * @copyright: 2017上海风创信息咨询有限公司-版权所有
 * 
 */

package cn.itcast.norelax;

/**
 * @ClassName: MyTraditionThreadCommunication
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author no_relax
 * @date 2017年2月16日 下午4:22:38
 * 
 */
public class MyTraditionThreadCommunication {
	private static final OutPut outPut = new OutPut();

	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				while (true) {
					outPut.outPut1("wusong");
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				while (true) {
					outPut.outPut3("hangzou");
				}
			}
		}).start();

	}

	static class OutPut {

		public synchronized void outPut1(String str) {
			int length = str.length();
			for (int i = 0; i < length; i++) {
				System.out.print(str.charAt(i));
			}
			System.out.println();
		}

		public static synchronized void outPut2(String str) {
			int length = str.length();
			for (int i = 0; i < length; i++) {
				System.out.print(str.charAt(i));
			}
			System.out.println();
		}

		public void outPut3(String str) {
			/*
			 * outPut1、outPut3同步 锁用outPut；private static final OutPut outPut =new OutPut();
			 *  outPut2、outPut3同步 锁用OutPut.class
			 */
			synchronized (outPut) {
				int length = str.length();
				for (int i = 0; i < length; i++) {
					System.out.print(str.charAt(i));
				}
				System.out.println();
			}
		}

	}

}
