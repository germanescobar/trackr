package org.gescobar.wayra.interceptors;

import org.gescobar.wayra.util.Jogger;
import org.jogger.Interceptor;
import org.jogger.InterceptorChain;
import org.jogger.http.Request;
import org.jogger.http.Response;

public class FacebookInterceptor implements Interceptor {

	@Override
	public void intercept(Request request, Response response, InterceptorChain execution) throws Exception {
		
		String key = "367771223287679";
		if ( Jogger.isDevEnv() ) {
			key = "230795040357143";
		}
		
		response.setAttribute("fb_api_key", key);
		
		execution.proceed();
	}

}
