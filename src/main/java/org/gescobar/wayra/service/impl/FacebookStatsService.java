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
 * An {@link StatisticsService} implementation using the Facebook API.
 * 
 * @author German Escobar
 */
public class FacebookStatsService implements StatisticsService {

	@Override
	public StatsDTO buildStats(String accessToken) {
		
		try {
			
			// retrieve the dates of last posts
			Collection<Date> postsDates = getPostsDates( accessToken );
			
			// build the StatsDTO object 
			return StatsServiceHelper.buildStats( postsDates );
			
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
	 * @param accessToken the Facebook access token.
	 * 
	 * @return a collection of Date objects.
	 * @throws JSONException
	 */
	private Collection<Date> getPostsDates(String accessToken) throws JSONException, IOException {
		
		Collection<Date> ret = new ArrayList<Date>(); // this is what we are returning
		
		// get the feed
		String jsonString = StatsServiceHelper.getHTML("https://graph.facebook.com/me/feed?access_token=" + accessToken);
		JSONArray feed = new JSONObject( jsonString ).getJSONArray("data");
		
		for (int i=0; i < feed.length(); i++) {
			
			JSONObject feedItem = feed.getJSONObject(i);
			
			// if this is a status feed item add it to the collection
			if ( feedItem.getString("type").equals("status") ) {

				try {
					ret.add( parse(feedItem.getString("created_time")) );
				} catch (ParseException e) {
					System.err.println("ParseException retrieving date: " + e.getMessage());
					e.printStackTrace(System.err);
				}
				
			}
		}
		
		return ret;
	}
	
	/**
	 * Helper method. Parses a date using the specific Facebook API format. 
	 * 
	 * @param strDate the String that we are going to parse.
	 * 
	 * @return a Date object parsed from the strDate argument.
	 * @throws ParseException if there is a problem parsing the strDate argument.
	 */
	private Date parse(String strDate) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		return sdf.parse(strDate);
		
	}

}
