package org.gescobar.wayra.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.gescobar.wayra.entity.Service;
import org.gescobar.wayra.entity.User;
import org.jogger.http.Cookie;
import org.jogger.http.Request;
import org.jogger.http.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Pages {

	public void index(Request request, Response response) throws JSONException, IOException {
		
		User user = getUser( request.getCookie("accessToken") );
		if (user == null ) {
			response.render("index.ftl");
			return;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
		String[] days = new String[7];
		
		Calendar cal = Calendar.getInstance();
		days[6] = sdf.format(cal.getTime());
		
		for (int i=1; i < 7; i++) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
			days[6 - i] = sdf.format(cal.getTime());
		}
		
		String strDays = "[";
		for (String day : days) {
			strDays += " '" + day + "',";
		}
		strDays = strDays.substring(0, strDays.length()-1) + " ]";
		
		Map<String,Object> root = new HashMap<String,Object>();
		root.put("xLabels", strDays);
		root.put("user", user);
		
		response.render("dashboard.ftl", root);
		
	}
	
	private User getUser(Cookie cookie) throws JSONException {
		
		if (cookie == null) {
			return null;
		}
		
		String accessToken = cookie.getValue();
		
		try {
			String jsonString = getHTML("https://graph.facebook.com/me?access_token=" + accessToken);
			
			JSONObject json = new JSONObject( jsonString );
			
			User user = new User();
			user.setId( json.getLong("id") );
			user.setName( json.getString("name") );
			
			Service twitterService = new Service();
			twitterService.setId(1);
			twitterService.setName("twitter");
			twitterService.setLabel("Tweets");
			twitterService.setData("germanescobar");
			
			
			Collection<Service> services = new ArrayList<Service>();
			services.add(twitterService);
			
			user.setServices(services);
			
			return user;
			
		} catch (HttpResponseException e) {
			// the token expired
		} catch (IOException e) {
			System.err.println("IOException retrieving info from Facebook: " + e.getMessage());
			e.printStackTrace(System.err);
		}
		
		return null;
		
	}
	
	private int getFacebook(String accessToken) throws JSONException, IOException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		
		String jsonString = getHTML("https://graph.facebook.com/me/feed?access_token=" + accessToken);
		
		JSONObject json = new JSONObject( jsonString );
		JSONArray feed = json.getJSONArray("data");
		
		int ret = 0;

		for (int i=0; i < feed.length(); i++) {
			
			JSONObject feedItem = feed.getJSONObject(i);
			if ( feedItem.getString("type").equals("status") ) {
				
				try {
					String strCreatedAt = feedItem.getString("created_time");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime( sdf.parse(strCreatedAt) );
					
					Calendar actualCalendar = Calendar.getInstance();
					
					if (calendar.get(Calendar.MONTH) == actualCalendar.get(Calendar.MONTH) 
							&& calendar.get(Calendar.DAY_OF_MONTH) == actualCalendar.get(Calendar.DAY_OF_MONTH)) {
						ret++;
					}
					
				} catch (ParseException e) {
					System.err.println("ParseException retrieving date: " + e.getMessage());
					e.printStackTrace(System.err);
				}
				
			}
		}
		
		return ret;
	}
	
	private int getTweets(String username) throws JSONException, IOException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
		
		String jsonString = getHTML("http://api.twitter.com/1/statuses/user_timeline.json?screen_name=germanescobar");
		JSONArray tweets = new JSONArray(jsonString);
		
		int ret = 0;
		
		for (int i=0; i < tweets.length(); i++) {
			JSONObject tweet = tweets.getJSONObject(i);
			
			try {
				String strCreatedAt = tweet.getString("created_at");
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime( sdf.parse(strCreatedAt) );
				
				Calendar actualCalendar = Calendar.getInstance();
				
				if (calendar.get(Calendar.MONTH) == actualCalendar.get(Calendar.MONTH) 
						&& calendar.get(Calendar.DAY_OF_MONTH) == actualCalendar.get(Calendar.DAY_OF_MONTH)) {
					ret++;
				}
				
			} catch (ParseException e) {
				System.err.println("ParseException retrieving date: " + e.getMessage());
				e.printStackTrace(System.err);
			}
	
		}
		
		return ret;
	}
	
	private int getCommits(String username) throws JSONException, IOException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		List<String> repos = new ArrayList<String>();
		
		String jsonReposString = getHTML("https://api.github.com/users/germanescobar/repos");
		JSONArray jsonProjects = new JSONArray(jsonReposString);
		for (int i=0; i < jsonProjects.length(); i++) {
			JSONObject jsonProject = jsonProjects.getJSONObject(i);
			repos.add( jsonProject.getString("name") );
		}
		
		int ret = 0;
		
		for (String repo : repos) {
			String jsonCommitsString = getHTML("https://api.github.com/repos/germanescobar/" + repo + "/commits");
			JSONArray jsonCommits = new JSONArray(jsonCommitsString);
			for (int j=0; j < jsonCommits.length(); j++) {
				JSONObject jsonCommit = jsonCommits.getJSONObject(j);
				JSONObject jsonCommiter = jsonCommit.getJSONObject("commit").getJSONObject("committer");
				try {
					String strDate = jsonCommiter.getString("date");
					
					Calendar calendar = Calendar.getInstance();
					calendar.setTime( sdf.parse(strDate) );
					
					Calendar actualCalendar = Calendar.getInstance();
					
					if (calendar.get(Calendar.MONTH) == actualCalendar.get(Calendar.MONTH) 
							&& calendar.get(Calendar.DAY_OF_MONTH) == actualCalendar.get(Calendar.DAY_OF_MONTH)) {
						ret++;
					}
					
				} catch (ParseException e) {
					System.err.println("ParseException retrieving date: " + e.getMessage());
					e.printStackTrace(System.err);
				}
			}
			
		}
		
		return ret;
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
	
}
