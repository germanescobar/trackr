package org.gescobar.wayra.service;

import java.util.Map;

/**
 * Creates a {@link StatisticsService} based on an internal map.
 * 
 * @author German Escobar
 */
public class StatisticsServiceFactory {

	/**
	 * Holds the registered statistics services.
	 */
	private Map<String,StatisticsService> statisticsServices;
	
	/**
	 * Retrieves a {@link StatisticsService} based on the name argument.
	 * 
	 * @param name the name of the registered service that we are looking for.
	 * 
	 * @return a {@link StatisticsService} implementation or null if no match.
	 */
	public StatisticsService get(String name) {
		return statisticsServices.get(name);
	}

	public void setStatisticsServices(Map<String, StatisticsService> statisticsServices) {
		this.statisticsServices = statisticsServices;
	}
	
}
