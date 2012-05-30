package org.gescobar.wayra.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.gescobar.wayra.service.StatisticsService;
import org.gescobar.wayra.service.StatsDTO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link StatisticsService} implementation using the Twitter API.
 * 
 * @author German Escobar
 */
public class TwitterStatsService implements StatisticsService {

	@Override
	public StatsDTO buildStats(String data) {
		
		try {
			
			// retrieve the dates of last tweets
			Collection<Date> tweetsDates = getTweetsDates( data );
			
			// build the StatsDTO object 
			return StatsServiceHelper.buildStats( tweetsDates );
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		// something went wrong, return zeros
		return new StatsDTO(0,0,0,0,0,0,0);
	}
	
	/**
	 * Helper method. Retrieves the date of lasts post.
	 * 
	 * @param username the Twitter username.
	 * 
	 * @return a collection of Date objects.
	 * @throws JSONException
	 * @throws IOException
	 */
	private Collection<Date> getTweetsDates(String username) throws JSONException, IOException {
		
		Collection<Date> ret = new ArrayList<Date>(); // this is what we are returning
		
		// get the tweets
		String jsonString = StatsServiceHelper.getHTML("http://api.twitter.com/1/statuses/user_timeline.json?screen_name=" + username);
		JSONArray tweets = new JSONArray(jsonString);
		
		
		for (int i=0; i < tweets.length(); i++) {
			
			JSONObject tweet = tweets.getJSONObject(i);
			
			try {
				
				String strCreatedAt = tweet.getString("created_at");
				ret.add( parse(strCreatedAt) );
				
			} catch (ParseException e) {
				System.err.println("ParseException retrieving date: " + e.getMessage());
				e.printStackTrace(System.err);
			}
	
		}
		
		return ret;
		
	}
	
	/**
	 * Helper method. Parses a date using the specific Twitter API format. 
	 * 
	 * @param strDate the String that we are going to parse.
	 * 
	 * @return a Date object parsed from the strDate argument.
	 * @throws ParseException if there is a problem parsing the strDate argument.
	 */
	private Date parse(String strDate) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
		return sdf.parse(strDate);
		
	}
	
}
