package org.gescobar.wayra.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.gescobar.wayra.service.StatisticsService;
import org.gescobar.wayra.service.StatsDTO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An {@link StatisticsService} implementation using the Github API.
 * 
 * @author German Escobar
 */
public class GithubStatsService implements StatisticsService {

	@Override
	public StatsDTO buildStats(String data) {
		
		try {
			
			// retrieve the dates of last commits
			Collection<Date> commitsDates = getCommitsDates( data );
			
			// build and return the StatsDTO object
			return StatsServiceHelper.buildStats( commitsDates );
			
		} catch (Exception e) {
			System.err.println( e.getMessage() );
			e.printStackTrace();
		}
		
		// something went wrong, return zeros
		return new StatsDTO(0,0,0,0,0,0,0);
	}
	
	/**
	 * Helper method. Retrieves the dates of the last commits.
	 * 
	 * @param username the Github username.
	 * 
	 * @return a collection of Dates objects.
	 * @throws Exception
	 */
	private Collection<Date> getCommitsDates(String username) throws Exception {
		
		Collection<Date> commitsDates = new ArrayList<Date>(); // this is what we are returning
		
		List<String> repos = getRepos(username);
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
					Date commitDate = parse( strCommitDate ); 
					
					// add to the list
					commitsDates.add( commitDate );
					
				} catch (ParseException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return commitsDates;
		
	}
	
	/**
	 * Helper method. Retrieves the names of the public repositories of the user.
	 * 
	 * @param username the Github username.
	 * 
	 * @return a list of strings with the names of the repos.
	 * @throws Exception
	 */
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
	
	/**
	 * Helper method. Parses a date using the specific Github API format. 
	 * 
	 * @param strDate the String that we are going to parse.
	 * 
	 * @return a Date object parsed from the strDate argument.
	 * @throws ParseException if there is a problem parsing the strDate argument.
	 */
	private Date parse(String strDate) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse( strDate.substring(0, 10) );
		
	}

}
