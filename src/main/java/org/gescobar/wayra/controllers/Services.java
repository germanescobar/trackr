package org.gescobar.wayra.controllers;

import org.jogger.http.Request;
import org.jogger.http.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class Services {

	public void activate(Request request, Response response) throws JSONException {
		
		JSONObject json = new JSONObject(request.getBody().asString());
		
		long userId = json.getLong("userId");
		Object data = json.get("data");
		
		
	}
	
}
