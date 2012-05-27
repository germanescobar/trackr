package org.gescobar.wayra.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.gescobar.wayra.service.StatisticsService;
import org.gescobar.wayra.service.StatsDTO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TwitterStatsService implements StatisticsService {

	@Override
	public StatsDTO buildStats(String data) {
		
		try {
			
			String jsonString = getHTML("http://api.twitter.com/1/statuses/user_timeline.json?screen_name=germanescobar");
			JSONArray tweets = new JSONArray(jsonString);
			
			Collection<Date> updateDates = getTweetsDates(tweets);
			
			int[] stats = new int[7];
			Calendar cal = Calendar.getInstance();
			for (int i=0; i < 7; i++) {
				
				int matches = matchesDate(updateDates, cal);
				stats[6-i] = matches;
				
				cal.add(Calendar.DAY_OF_MONTH, -1);
			}
			
			return new StatsDTO(stats);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		return new StatsDTO(0,0,0,0,0,0,0);
	}
	
	private int matchesDate(Collection<Date> updateDates, Calendar cal) {
		
		int matches = 0;
		for (Date updateDate : updateDates) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(updateDate);
			
			boolean sameDay = cal.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
            	cal.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR);
			if (sameDay) {
				matches++;
			}
		}
		
		return matches;
		
	}
	
	private Collection<Date> getTweetsDates(JSONArray tweets) throws JSONException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
		
		Collection<Date> ret = new ArrayList<Date>();
		
		for (int i=0; i < tweets.length(); i++) {
			JSONObject tweet = tweets.getJSONObject(i);
			
			try {
				String strCreatedAt = tweet.getString("created_at");
				ret.add( sdf.parse(strCreatedAt) );
				
			} catch (ParseException e) {
				System.err.println("ParseException retrieving date: " + e.getMessage());
				e.printStackTrace(System.err);
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
