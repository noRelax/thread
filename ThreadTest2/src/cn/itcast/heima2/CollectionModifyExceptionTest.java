package cn.itcast.heima2;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionModifyExceptionTest {
	@SuppressWarnings("all")
	public static void main(String[] args) {
		// Collection users = new ArrayList();
		List<User> users = new CopyOnWriteArrayList<User>();
		users.add(new User("wusong", 26));
		users.add(new User("zhang", 25));
		users.add(new User("norelax", 27));
		// Iterator iterator = users.iterator();
		//
		// while (iterator.hasNext()) {
		// User user = (User) iterator.next();
		// if ("wusong".equals(user.getName())) { // iterator.remove();
		// users.remove(user);
		// } else {
		// System.out.println(user.toString());
		// }
		// }
		for (User user : users) {
			if ("wusong".equals(user.getName())) {
				users.remove(user);
			} else {
				System.out.println(user.toString());
			}
		}
	}

}
