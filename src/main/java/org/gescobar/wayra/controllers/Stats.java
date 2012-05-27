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

public class Stats {
	
	private UserStore userStore;
	
	private StatisticsServiceFactory statisticsServiceFactory;

	public void show(Request request, Response response) throws JSONException, URISyntaxException {
		
		response.contentType("application/json");
		
		long userId = Long.parseLong( getParameter(request.getQueryString(), "user") );
		String serviceName = getParameter(request.getQueryString(), "name");
		
		StatisticsService statsService = statisticsServiceFactory.get(serviceName);
		String data = request.getCookie("accessToken").getValue();
		if (!"facebook".equals(serviceName)) {
			
			User user = userStore.load(userId);
			UserService userService = user.getService(serviceName);
			
			data = userService.getData();
		}
		StatsDTO stats = statsService.buildStats(data);
		
		JSONObject jsonResponse = new JSONObject()
			.put("today", stats.getToday())
			.put("yesterday", stats.getYesterday())
			.put("data", stats.getStats());
		
		response.print( jsonResponse.toString() );
		
	}
	
	private String getParameter(String queryString, String parameter) throws URISyntaxException {
		
		List<NameValuePair> pairs = URLEncodedUtils.parse(new URI("http://localhost/?" + queryString), "UTF-8");
		
		for (NameValuePair pair : pairs) {
			if (pair.getName().equals(parameter)) {
				return pair.getValue();
			}
		}
		
		return null;
	}

	public UserStore getUserStore() {
		return userStore;
	}

	public void setUserStore(UserStore userStore) {
		this.userStore = userStore;
	}

	public StatisticsServiceFactory getStatisticsServiceFactory() {
		return statisticsServiceFactory;
	}

	public void setStatisticsServiceFactory(StatisticsServiceFactory statisticsServiceFactory) {
		this.statisticsServiceFactory = statisticsServiceFactory;
	}
	
}
