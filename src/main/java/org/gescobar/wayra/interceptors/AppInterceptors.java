package org.gescobar.wayra.interceptors;

import org.jogger.config.spring.SpringInterceptors;

/**
 * Defines the interceptors of the application. It is configured in the web.xml file.
 * 
 * @author German Escobar
 */
public class AppInterceptors extends SpringInterceptors {

	@Override
	public void initialize() {
		
		// sets the Facebook API key in the response properties
		add( "facebookInterceptor" );
	}

	

}
