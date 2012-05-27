package org.gescobar.wayra.controllers;

import org.gescobar.wayra.entity.UserService;
import org.gescobar.wayra.service.UserStore;
import org.jogger.http.Request;
import org.jogger.http.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class Services {
	
	private UserStore userStore;

	public void activate(Request request, Response response) throws JSONException {
		
		JSONObject json = new JSONObject(request.getBody().asString());
		
		long userId = json.getLong("userId");
		String service = json.getString("service");
		Object data = json.get("data");
		
		UserService userService = new UserService();
		userService.setUserId(userId);
		userService.setName(service);
		userService.setData(data.toString());
		
		userStore.activate(userService);
		
	}

	public UserStore getUserStore() {
		return userStore;
	}

	public void setUserStore(UserStore userStore) {
		this.userStore = userStore;
	}
	
}
