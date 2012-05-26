package org.gescobar.wayra.service;

public class StatsDTO {

	private int[] stats = new int[7];
	
	public StatsDTO(int ... stats) {
		
		if (stats == null || stats.length != 7) {
			throw new IllegalArgumentException("Must supply seven values");
		}
		
		this.stats = stats;
		
	}
	
	public int[] getStats() {
		return stats;
	}

	public int getToday() {
		return stats[6];
	}
	
	public int getYesterday() {
		return stats[5];
	}
	
	public int getSum() {
		
		int sum = 0;
		for (int stat : stats) {
			sum += stat;
		}
		
		return sum;
	}
	
	public String getStringFormat() {
		
		String ret = "[";
		for (int stat : stats) {
			ret += " " + stat + ",";
		}
		
		ret = ret.substring(0, ret.length()-1) + " ]";
	
		return ret;
	}
}
