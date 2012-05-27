package org.gescobar.wayra.service;

import org.gescobar.wayra.entity.UserService;
import org.gescobar.wayra.entity.User;

public interface UserStore {

	void create(User user);
	
	User load(long id);
	
	void activate(UserService service);
	
}
