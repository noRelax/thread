package cn.itcast.heima2;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionModifyExceptionTest {
	@SuppressWarnings("all")
	public static void main(String[] args) {
		// Collection users = new ArrayList();
		Collection users = new CopyOnWriteArrayList<>();
		users.add(new User("wusong", 26));
		users.add(new User("zhang", 25));
		users.add(new User("norelax", 27));
		Iterator iterator = users.iterator();
		while (iterator.hasNext()) {
			User user = (User) iterator.next();
			if ("norelax".equals(user.getName())) {
				// iterator.remove();
				users.remove(user);
			} else {
				System.out.println(user.toString());
			}
		}

	}

}
