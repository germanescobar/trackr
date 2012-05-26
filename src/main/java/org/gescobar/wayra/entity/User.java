package org.gescobar.wayra.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class User {

	private long id;
	
	private String name;
	
	private Date creationTime;
	
	private Collection<Service> services = new ArrayList<Service>();

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

	public Collection<Service> getServices() {
		return services;
	}

	public void setServices(Collection<Service> services) {
		this.services = services;
	}
	
	public boolean hasService(String name) {
		boolean ret = false;
		
		for (Service service : services) {
			if (service.getName().equals(name)) {
				ret = true;
			}
		}
		
		return ret;
	}
	
}
