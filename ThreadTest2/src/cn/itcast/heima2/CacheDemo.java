package cn.itcast.heima2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
//自定义缓存处理类
public class CacheDemo {

	private Map<String, Object> cache = new HashMap<String, Object>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private ReadWriteLock rwl = new ReentrantReadWriteLock();
	public  Object getData(String key){
		rwl.readLock().lock();
		Object value = null;
		try{
			value = cache.get(key);
			if(value == null){
				//将读锁释放，不让其进行读取操作
				rwl.readLock().unlock();
				//加写锁，进行写入操作
				rwl.writeLock().lock();
				try{
					//由于37恢复读锁，这在24行等待的读线程会抢占锁资源，进行读取操作，所以在这里多判断一层就减少了与数据库的交互，提升性能
					if(value==null){
						value = "aaaa";//查询数据库操作;
					}
				}finally{
					//写完就释放写锁
					rwl.writeLock().unlock();
				}
				//恢复读锁，让其继续进行读取操作
				rwl.readLock().lock();
			}
		}finally{
			rwl.readLock().unlock();
		}
		return value;
	}
}
