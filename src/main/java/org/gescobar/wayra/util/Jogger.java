package org.gescobar.wayra.util;

public class Jogger {

	public static String env() {
		
		String env = System.getProperty("JOGGER_ENV");
		if (env == null) {
			env = System.getenv("JOGGER_ENV");
		}
		
		if (env == null) {
			return "dev";
		}
		
		return env;
	}
	
	public static boolean isTestEnv() {
		return env().equals("test");
	}
	
	public static boolean isDevEnv() {
		return env().equals("dev");
	}
	
}
