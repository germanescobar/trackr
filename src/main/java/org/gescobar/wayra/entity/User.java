package org.gescobar.wayra.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Represents a user in the persistence store.
 * 
 * @author German Escobar
 */
public class User {

	private long id;
	
	private String name;
	
	private Date creationTime;
	
	private Collection<UserService> services = new ArrayList<UserService>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Collection<UserService> getServices() {
		return services;
	}

	public void setServices(Collection<UserService> services) {
		this.services = services;
	}
	
	public void addService(UserService service) {
		services.add(service);
	}
	
	public boolean hasService(String name) {
		boolean ret = false;
		
		for (UserService service : services) {
			if (service.getName().equals(name)) {
				ret = true;
			}
		}
		
		return ret;
	}
	
	public UserService getService(String name) {
		
		for (UserService service : services) {
			if (service.getName().equals(name)) {
				return service;
			}
		}
		
		return null;
		
	}
	
}
