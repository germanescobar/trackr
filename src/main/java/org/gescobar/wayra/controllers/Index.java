package org.gescobar.wayra.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpResponseException;
import org.gescobar.wayra.entity.User;
import org.gescobar.wayra.service.UserStore;
import org.gescobar.wayra.service.impl.StatsServiceHelper;
import org.jogger.http.Cookie;
import org.jogger.http.Request;
import org.jogger.http.Response;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Controller. Renders the index/dashboard screen.
 * 
 * @author German Escobar
 */
public class Index {
	
	/**
	 * Used to create and retrieve users.
	 */
	private UserStore userStore;

	/**
	 * Controller method. If the user is authenticated, it renders the 'dashboard.ftl' template which shows the stats. 
	 * Otherwise, it renders the 'index.ftl' template that allows the user to login.
	 * 
	 * @param request
	 * @param response
	 * 
	 * @throws JSONException
	 * @throws IOException
	 */
	public void show(Request request, Response response) throws JSONException, IOException {
		
		// get the user 
		User user = getUser( request.getCookie("accessToken") );
		if (user == null ) {
			
			// no user, render index
			response.render( "index.ftl" );
			return;
		}
		
		Map<String,Object> root = new HashMap<String,Object>();
		root.put( "xLabels", getLastDaysForChart() );
		root.put( "user", user );
		
		response.render( "dashboard.ftl", root );
		
	}
	
	/**
	 * Helper method. Retrieves the last 7 days as a string JSON array with format 'dd MMM'. 
	 * 
	 * @return a String object holding a JSON array.
	 */
	private String getLastDaysForChart() {
		
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
		
		return strDays;
	}
	
	/**
	 * Helper method. Retrieves the {@link User} from the cookie that is received as argument. If the user is not 
	 * found in the store it is created.
	 * 
	 * @param cookie the cookie that holds the access token of the user.
	 * 
	 * @return the User if the cookie is not null, null otherwise.
	 * @throws JSONException
	 */
	private User getUser(Cookie cookie) throws JSONException {
		
		if (cookie == null) {
			return null;
		}
		
		String accessToken = cookie.getValue();
		
		try {
			String jsonString = StatsServiceHelper.getHTML("https://graph.facebook.com/me?access_token=" + accessToken);
			
			JSONObject json = new JSONObject( jsonString );
			
			long userId = json.getLong("id");
			User user = userStore.load(userId);
			
			if (user == null) {
				user = new User();
				user.setId( userId );
				user.setName( json.getString("name") );
				
				userStore.create(user);
			}
			
			return user;
			
		} catch (HttpResponseException e) {
			// the token expired
		} catch (IOException e) {
			System.err.println("IOException retrieving info from Facebook: " + e.getMessage());
			e.printStackTrace(System.err);
		}
		
		return null;
		
	}

	public void setUserStore(UserStore userStore) {
		this.userStore = userStore;
	}
	
}
