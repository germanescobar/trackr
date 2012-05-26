package org.gescobar.wayra.service;

public class MockTwitterStatsService implements StatsService {

	@Override
	public StatsDTO buildStats(String data) {
		return new StatsDTO(3, 12, 9, 2, 4, 7, 2);
	}

}
