package org.gescobar.wayra.controllers;

import org.jogger.http.Request;
import org.jogger.http.Response;

public class Pages {

	public void index(Request request, Response response) {
		response.render("index.ftl");
	}
	
}
