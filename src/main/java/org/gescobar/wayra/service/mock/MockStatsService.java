package org.gescobar.wayra.service.mock;

import java.util.Random;

import org.gescobar.wayra.service.StatisticsService;
import org.gescobar.wayra.service.StatsDTO;

/**
 * 
 * @author German Escobar
 */
public class MockStatsService implements StatisticsService {

	@Override
	public StatsDTO buildStats(String data) {
		
		int[] stats = new int[7]; 
		
		Random rg = new Random();
		for (int i=0; i < 7; i++){
			stats[i] = rg.nextInt(20);
			
	    }
		
		return new StatsDTO(stats);
	}

}
