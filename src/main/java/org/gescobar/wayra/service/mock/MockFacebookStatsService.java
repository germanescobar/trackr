package org.gescobar.wayra.service.mock;

import org.gescobar.wayra.service.StatisticsService;
import org.gescobar.wayra.service.StatsDTO;

public class MockFacebookStatsService implements StatisticsService {

	@Override
	public StatsDTO buildStats(String data) {
		
		return new StatsDTO(3, 5, 8, 2, 5, 2, 1);
	}

}
