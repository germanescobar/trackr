package org.gescobar.wayra.service.mock;

import java.util.HashMap;
import java.util.Map;

import org.gescobar.wayra.entity.User;
import org.gescobar.wayra.entity.UserService;
import org.gescobar.wayra.service.UserStore;

public class MockUserStore implements UserStore {
	
	private Map<Long,User> users = new HashMap<Long,User>();

	@Override
	public void create(User user) {
		users.put(user.getId(), user);
	}

	@Override
	public User load(long id) {
		return users.get(id);
	}

	@Override
	public void activate(UserService service) {
		
		User user = users.get( service.getUserId() );
		user.addService(service);
		
	}

}
