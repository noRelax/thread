package cn.itcast.heima2;

import java.util.Random;

public class ThreadLocalTest {

	private static ThreadLocal<Integer> x = new ThreadLocal<Integer>();
	private static ThreadLocal<MyThreadScopeData> myThreadScopeData = new ThreadLocal<MyThreadScopeData>();

	public static void main(String[] args) {
		Thread ws = new ws();
		Runtime.getRuntime().addShutdownHook(ws);
		for (int i = 0; i < 2; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					int data = new Random().nextInt();
					System.out.println(Thread.currentThread().getName() + " has put data :" + data);
					x.set(data);
					/*
					 * MyThreadScopeData myData = new MyThreadScopeData();
					 * myData.setName("name" + data); myData.setAge(data);
					 * myThreadScopeData.set(myData);
					 */
					MyThreadScopeData.getThreadInstance().setName("name" + data);
					MyThreadScopeData.getThreadInstance().setAge(data);
					new A().get();
					new B().get();
				}
			}).start();
		}
	}

	static class A {
		public void get() {
			int data = x.get();
			System.out.println("A from " + Thread.currentThread().getName() + " get data :" + data);
			/*
			 * MyThreadScopeData myData = myThreadScopeData.get();;
			 * System.out.println("A from " + Thread.currentThread().getName() +
			 * " getMyData: " + myData.getName() + "," + myData.getAge());
			 */
			MyThreadScopeData myData = MyThreadScopeData.getThreadInstance();
			System.out.println("A from " + Thread.currentThread().getName() + " getMyData: " + myData.getName() + "," + myData.getAge());
		}
	}

	static class B {
		public void get() {
			int data = x.get();
			System.out.println("B from " + Thread.currentThread().getName() + " get data :" + data);
			MyThreadScopeData myData = MyThreadScopeData.getThreadInstance();
			System.out.println("B from " + Thread.currentThread().getName() + " getMyData: " + myData.getName() + "," + myData.getAge());
		}
	}
}

class ws extends Thread {
	@Override
	public void run() {
		System.out.println("A method excute...");;
	}
}

class MyThreadScopeData {
	// 声明无参构造函数，让其在外部不能根据new方法来创建实例
	private MyThreadScopeData() {
	}

	private static ThreadLocal<MyThreadScopeData> map = new ThreadLocal<MyThreadScopeData>();
	//获取线程范围内的实例，每个线程对应的实例不相同，同一个线程对应的实例相同
	public static /* synchronized */ MyThreadScopeData getThreadInstance() {
		MyThreadScopeData instance = map.get();
		if (instance == null) {
			instance = new MyThreadScopeData();
			map.set(instance);
		}
		return instance;
	}
	// private static MyThreadScopeData instance = null;//new
	// MyThreadScopeData();

	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
