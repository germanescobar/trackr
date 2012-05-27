package org.gescobar.wayra.service;

import java.util.Map;

public class StatisticsServiceFactory {

	private Map<String,StatisticsService> statisticsServices;
	
	public StatisticsService get(String name) {
		return statisticsServices.get(name);
	}

	public Map<String, StatisticsService> getStatisticsServices() {
		return statisticsServices;
	}

	public void setStatisticsServices(
			Map<String, StatisticsService> statisticsServices) {
		this.statisticsServices = statisticsServices;
	}
	
}
