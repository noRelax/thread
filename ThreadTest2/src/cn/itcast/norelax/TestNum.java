package cn.itcast.norelax;

import java.util.HashMap;
import java.util.Map;

/*
 * 考察输出的结果信息，我们发现每个线程所产生的序号虽然都共享同一个TestNum实例，
 * 但它们并没有发生相互干扰的情况，而是各自产生独立的序列号，
 * 这是因为我们通过ThreadLocal为每一个线程提供了单独的副本。
 */

public class TestNum {
	// ①通过匿名内部类覆盖ThreadLocal的initialValue()方法，指定初始值
	private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {
		public Integer initialValue() {
			return 0;
		}
	};

	private static final ThreadLocal<Map<String, Object>> threadMap = new ThreadLocal<Map<String, Object>>();
	private static Map<String, Object> map;

	// ②获取下一个序列值
	public String getNextNum() {
		if (threadMap.get() == null) {
			map = new HashMap<String, Object>();
		} else {
			map = threadMap.get();
		}
		map.put(seqNum.get() + 1 + "", seqNum.get() + 1);
		seqNum.set(seqNum.get() + 1);
		threadMap.set(map);
		return String.join(",", map.keySet());
	}

	public static void main(String[] args) {
		TestNum sn = new TestNum();
		// ③ 3个线程共享sn，各自产生序列号
		TestClient t1 = new TestClient(sn);
		TestClient t2 = new TestClient(sn);
		TestClient t3 = new TestClient(sn);
		t1.start();
		t2.start();
		t3.start();
	}

	private static class TestClient extends Thread {
		private TestNum sn;

		public TestClient(TestNum sn) {
			this.sn = sn;
		}

		public void run() {
			for (int i = 0; i < 3; i++) {
				// ④每个线程打出3个序列值
				System.out.println("thread[" + Thread.currentThread().getName() + "] --> sn[" + sn.getNextNum() + "]");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();

				}
			}
		}
	}
}