package org.gescobar.wayra.util;

import java.net.URI;
import java.net.URISyntaxException;

public class DatabaseUri {
	
	private URI uri;

	public DatabaseUri(String uri) throws URISyntaxException {
		this.uri = new URI(uri);
	}
	
	public String getUrl() {
		String scheme = uri.getScheme();
		if ( scheme.contains("postgres") ) {
			scheme = "postgresql";
		}
		
		return "jdbc:" + scheme + "://" + uri.getHost() + getPort() + getPath();
	}
	
	private String getPort() {
		if (uri.getPort() != -1) {
			return ":" + uri.getPort();
		}
		
		return "";
	}
	
	private String getPath() {
		String path = uri.getPath();
		if (uri.getQuery() != null) {
			path += "?" + uri.getQuery();
		}
		
		return path;
	}
	
	public String getUsername() {
		return uri.getUserInfo().split(":")[0];
	}
	
	public String getPassword() {
		return uri.getUserInfo().split(":")[1];
	}
	
}
