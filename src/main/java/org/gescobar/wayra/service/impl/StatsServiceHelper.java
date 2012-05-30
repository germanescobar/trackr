package org.gescobar.wayra.service.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.gescobar.wayra.service.StatsDTO;

public class StatsServiceHelper {
	
	public static String getHTML(String urlToRead) throws IOException {
		
		HttpClient httpclient = new DefaultHttpClient();
		
		try {
			HttpGet httpget = new HttpGet( urlToRead );
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			
			return httpclient.execute(httpget, responseHandler);
			
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	public static StatsDTO buildStats(Collection<Date> dates) {
		
		int[] stats = new int[7];
		Calendar cal = Calendar.getInstance();
		for (int i=0; i < 7; i++) {
			
			int matches = StatsServiceHelper.matchesDate(dates, cal);
			stats[6-i] = matches;
			
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		
		return new StatsDTO(stats);
		
	}
	
	public static int matchesDate(Collection<Date> updateDates, Calendar cal) {
		
		int matches = 0;
		
		for (Date updateDate : updateDates) {
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(updateDate);
			
			boolean sameDay = cal.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
            	cal.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR);
			
			if ( sameDay ) {
				matches++;
			}
			
		}
		
		return matches;
		
	}

}
