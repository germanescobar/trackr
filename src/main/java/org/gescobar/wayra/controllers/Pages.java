package org.gescobar.wayra.controllers;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.gescobar.wayra.entity.User;
import org.jogger.http.Cookie;
import org.jogger.http.Request;
import org.jogger.http.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class Pages {

	public void index(Request request, Response response) throws JSONException {
		
		User user = getUser( request.getCookie("accessToken") );
		if (user == null ) {
			response.render("index.ftl");
			return;
		}
		
		response.render("dashboard.ftl");
		
	}
	
	private User getUser(Cookie cookie) throws JSONException {
		
		if (cookie == null) {
			return null;
		}
		
		String accessToken = cookie.getValue();
		
		try {
			String html = getHTML("https://graph.facebook.com/me?access_token=" + accessToken);
			
			JSONObject json = new JSONObject(html);
			
			User user = new User();
			user.setUid( json.getLong("id") );
			user.setName( json.getString("name") );
			
			return user;
			
		} catch (HttpResponseException e) {
			// the token expired
		} catch (IOException e) {
			System.err.println("IOException retrieving info from Facebook: " + e.getMessage());
			e.printStackTrace(System.err);
		}
		
		return null;
		
	}
	
	private String getHTML(String urlToRead) throws IOException {
		
		HttpClient httpclient = new DefaultHttpClient();
		
		try {
			HttpGet httpget = new HttpGet( urlToRead );
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			
			return httpclient.execute(httpget, responseHandler);
			
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	public static void main(String[] args) throws IOException {
		Pages pages = new Pages();
		String html = pages.getHTML("https://graph.facebook.com/me?access_token=sdf");
		System.out.println(html);
	}
	
}
