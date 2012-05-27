package org.gescobar.wayra.service.mock;

import org.gescobar.wayra.service.StatisticsService;
import org.gescobar.wayra.service.StatsDTO;

public class MockTwitterStatsService implements StatisticsService {

	@Override
	public StatsDTO buildStats(String data) {
		return new StatsDTO(3, 12, 9, 2, 4, 7, 2);
	}

}
