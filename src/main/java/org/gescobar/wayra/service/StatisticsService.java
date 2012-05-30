package org.gescobar.wayra.service;

/**
 * This interface is implemented by classes that retrieve stats from a service (i.e. Facebook, Twitter, etc.)
 * 
 * @author German Escobar
 */
public interface StatisticsService {

	/**
	 * Creates and returns a {@link StatsDTO} that wraps an array of stats of the last 7 days. If a problem occurs log 
	 * the exception and return a {@link StatsDTO} initialized with zeros.
	 * 
	 * @param data the information that the service needs to retrieve the stats.
	 * 
	 * @return a filled {@link StatsDTO} object.
	 */
	StatsDTO buildStats(String data);
	
}
