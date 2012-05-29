package org.gescobar.wayra.interceptors;

import javax.servlet.ServletConfig;

import org.jogger.config.Interceptors;

public class AppInterceptors extends Interceptors {

	@Override
	public void initialize(ServletConfig servletConfig) {
		add( new FacebookInterceptor() );
	}

}
