package org.gescobar.wayra.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.gescobar.wayra.service.StatisticsService;
import org.gescobar.wayra.service.StatsDTO;
import org.json.JSONArray;
import org.json.JSONObject;

public class GithubStatsService implements StatisticsService {

	@Override
	public StatsDTO buildStats(String data) {
		
		try {
			
			List<String> repos = getRepos(data);
			Collection<Date> commitsDates = getCommitsDates(data, repos);
			
			int[] stats = new int[7];
			Calendar cal = Calendar.getInstance();
			for (int i=0; i < 7; i++) {
				
				int matches = StatsServiceHelper.matchesDate(commitsDates, cal);
				stats[6-i] = matches;
				
				cal.add(Calendar.DAY_OF_MONTH, -1);
			}
			
			return new StatsDTO(stats);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		return new StatsDTO(0,0,0,0,0,0,0);
	}
	
	private List<String> getRepos(String username) throws Exception {
		
		List<String> repos = new ArrayList<String>();
		
		// retrieve github projects
		String jsonReposString = StatsServiceHelper.getHTML("https://api.github.com/users/" + username + "/repos");
		JSONArray jsonProjects = new JSONArray(jsonReposString);
		
		// iterate through the projects and add the names to the list
		for (int i=0; i < jsonProjects.length(); i++) {
			JSONObject jsonProject = jsonProjects.getJSONObject(i);
			repos.add( jsonProject.getString("name") );
		}
		
		return repos;
		
	}
	
	private Collection<Date> getCommitsDates(String username, Collection<String> repos) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Collection<Date> commitsDates = new ArrayList<Date>();
		
		for (String repo : repos) {

			// retrieve json commits
			String jsonCommitsString = StatsServiceHelper.getHTML("https://api.github.com/repos/" + username + "/" + repo + "/commits");
			JSONArray jsonCommits = new JSONArray(jsonCommitsString);
			
			// iterate through the commits
			for (int j=0; j < jsonCommits.length(); j++) {
				
				JSONObject jsonCommit = jsonCommits.getJSONObject(j);
				
				try {
					
					// retrieve the commit date - we just need the 10 first chars yyyy-MM-dd
					String strCommitDate = jsonCommit.getJSONObject("commit").getJSONObject("committer")
							.getString("date");
					Date commitDate = sdf.parse( strCommitDate.substring(0, 10) ); 
					
					// add to the list
					commitsDates.add( commitDate );
					
				} catch (ParseException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return commitsDates;
		
	}

}
