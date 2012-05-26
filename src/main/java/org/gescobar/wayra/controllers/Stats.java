package org.gescobar.wayra.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.gescobar.wayra.service.FacebookStatsService;
import org.gescobar.wayra.service.StatsDTO;
import org.gescobar.wayra.service.StatsService;
import org.gescobar.wayra.service.TwitterStatsService;
import org.jogger.http.Request;
import org.jogger.http.Response;
import org.json.JSONException;
import org.json.JSONObject;

public class Stats {

	public void show(Request request, Response response) throws JSONException, URISyntaxException {
		
		response.contentType("application/json");
		
		String userId = getParameter(request.getQueryString(), "user");
		String serviceName = getParameter(request.getQueryString(), "name");
		
		StatsService statsService = buildStatsService(serviceName);
		String data = request.getCookie("accessToken").getValue();
		if (!"facebook".equals(serviceName)) {
			data = "germanescobar";
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
	
	private StatsService buildStatsService(String name) {
		
		if ("facebook".equals(name)) {
			return new FacebookStatsService();
		} else if ("twitter".equals(name)) {
			return new TwitterStatsService();
		}
		
		return null;
		
	}
}
