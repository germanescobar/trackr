package org.gescobar.wayra.service;

import org.gescobar.wayra.entity.UserService;
import org.gescobar.wayra.entity.User;

/**
 * Implemented by classes that provide a persistence mechanism for users.
 * 
 * @author German Escobar
 */
public interface UserStore {

	/**
	 * Creates a user in the persistence store. If the user exists, it overrides it. If a problem occurs just log the 
	 * problem and do nothing.
	 * 
	 * @param user the {@link User} object to be persisted.
	 */
	void create(User user);
	
	/**
	 * Loads a user from the persistence store.
	 * 
	 * @param id the id of the user to load.
	 * @return a {@link User} object if found, null otherwise.
	 */
	User load(long id);
	
	/**
	 * Adds a service to the user. If the service is already activated, just override it. 
	 * 
	 * @param userService the service to be activated. 
	 */
	void activate(UserService userService);
	
}
