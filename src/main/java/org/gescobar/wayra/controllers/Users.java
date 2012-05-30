package org.gescobar.wayra.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.gescobar.wayra.entity.User;
import org.gescobar.wayra.entity.UserService;
import org.gescobar.wayra.service.StatisticsService;
import org.gescobar.wayra.service.StatisticsServiceFactory;
import org.gescobar.wayra.service.StatsDTO;
import org.gescobar.wayra.service.UserStore;
import org.jogger.http.Request;
import org.jogger.http.Response;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Controller. Handles requests related to users.
 * 
 * @author German Escobar
 */
public class Users {
	
	/**
	 * User to retrieve users and add services to users.
	 */
	private UserStore userStore;
	
	/**
	 * Used to retrieve the stats.
	 */
	private StatisticsServiceFactory statisticsServiceFactory;

	/**
	 * Controller method. Adds a service to the user.
	 * 
	 * @param request the Jogger HTTP request.
	 * @param response the Jogger HTTP response.
	 * 
	 * @throws JSONException
	 */
	public void addService(Request request, Response response) throws JSONException {
		
		// convert the body to JSON
		JSONObject json = new JSONObject( request.getBody().asString() );
		
		// get parameters
		long userId = json.getLong("userId");
		String service = json.getString("service");
		Object data = json.get("data");
		
		// create the UserService object
		UserService userService = new UserService();
		userService.setUserId( userId );
		userService.setName( service );
		userService.setData( data.toString() );
		
		// activate it
		userStore.activate(userService);
		
	}
	
	/**
	 * Controller method. Retrieves the stats of a given user and service.
	 * 
	 * @param request the Jogger request.
	 * @param responset the Jogger response.
	 * 
	 * @throws JSONException
	 * @throws URISyntaxException
	 */
	public void stats(Request request, Response response) throws JSONException, URISyntaxException {
		
		response.contentType("application/json");
		
		// get the user and service
		long userId = Long.parseLong( getParameter(request.getQueryString(), "user") );
		String serviceName = getParameter(request.getQueryString(), "name");
		
		// retrieve the statistics service
		StatisticsService statsService = statisticsServiceFactory.get(serviceName);
		
		// retreive the data that we are passing to to the statistics services
		String data = request.getCookie("accessToken").getValue();
		if (!"facebook".equals(serviceName)) {
			
			User user = userStore.load(userId);
			UserService userService = user.getService(serviceName);
			
			data = userService.getData();
		}
		
		// get the statistics
		StatsDTO stats = statsService.buildStats(data);
		
		// build the response and print it in the response
		JSONObject jsonResponse = new JSONObject()
			.put("today", stats.getToday())
			.put("yesterday", stats.getYesterday())
			.put("data", stats.getStats());
		
		response.print( jsonResponse.toString() );
		
	}
	
	/**
	 * Helper method. Retrieves the parameter of a query string. Definitely a functionality that Jogger should include. 
	 * 
	 * @param queryString the query string of the URL.
	 * @param parameter the parameter that we need to retrieve.
	 * 
	 * @return the value of the parameter or null if not found.
	 * @throws URISyntaxException
	 */
	private String getParameter(String queryString, String parameter) throws URISyntaxException {
		
		List<NameValuePair> pairs = URLEncodedUtils.parse(new URI("http://localhost/?" + queryString), "UTF-8");
		
		for (NameValuePair pair : pairs) {
			if (pair.getName().equals(parameter)) {
				return pair.getValue();
			}
		}
		
		return null;
	}

	public void setUserStore(UserStore userStore) {
		this.userStore = userStore;
	}

	public void setStatisticsServiceFactory(StatisticsServiceFactory statisticsServiceFactory) {
		this.statisticsServiceFactory = statisticsServiceFactory;
	}
	
}
