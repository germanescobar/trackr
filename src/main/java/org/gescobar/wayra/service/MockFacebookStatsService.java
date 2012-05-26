package org.gescobar.wayra.service;

public class MockFacebookStatsService implements StatsService {

	@Override
	public StatsDTO buildStats(String data) {
		
		return new StatsDTO(3, 5, 8, 2, 5, 2, 1);
	}

}
