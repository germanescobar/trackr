package org.gescobar.wayra.entity;

import java.util.Date;

/**
 * Represents a user service in the persistence store.
 * 
 * @author German Escobar
 */
public class UserService {
	
	private long id;
	
	private long userId;
	
	private String name;
	
	private String data;
	
	private Date creationTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	
	public String getLabel() {
		if ("twitter".equals(name)) {
			return "Tweets";
		} else if ("github".equals(name)) {
			return "Github Commits";
		} else if ("flickr".equals(name)) {
			return "Flickr Photos";
		}
		
		return "Unknown";
	}

}
