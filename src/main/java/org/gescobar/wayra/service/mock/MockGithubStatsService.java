package org.gescobar.wayra.service.mock;

import org.gescobar.wayra.service.StatisticsService;
import org.gescobar.wayra.service.StatsDTO;

public class MockGithubStatsService implements StatisticsService {

	@Override
	public StatsDTO buildStats(String data) {
		return new StatsDTO(13, 0, 1, 0, 8, 7, 0);
	}

}
