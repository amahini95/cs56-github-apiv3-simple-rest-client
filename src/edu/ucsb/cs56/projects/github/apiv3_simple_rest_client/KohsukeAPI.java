package edu.ucsb.cs56.projects.github.apiv3_simple_rest_client;

import org.kohsuke.github.*;
import java.util.*;


import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class KohsukeAPI {
	public static GHMyself me;
	public static GHOrganization cs56 = null;
	
		// Static block to initialize me object and cs56 organization
	static {
		try {
			me = GitHub.connect().getMyself();
			initCS56Org();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	// helper function to initialize cs56 GHOrganization
	private static void initCS56Org() throws IOException{
		GHPersonSet<GHOrganization> orgs = me.getAllOrganizations();
		for (GHOrganization org : orgs) {
			if (org.getName().equals("UCSB CS56 Projects"))
				cs56 = org;
		}
	}

	// Gets all the repos in cs56 organization
	public static List<GHRepository> getRepos(){
		List<GHRepository> repos = cs56.listRepositories(50).asList();
		return repos;
	} 

	public static List<GHRepository> getRepos(List<String> desiredRepos) {
		List<GHRepository> allRepos = cs56.listRepositories(50).asList();
		List<GHRepository> returnRepos = new ArrayList();

		for(GHRepository repo: allRepos) {
			if(desiredRepos.contains(repo.getName()))
				returnRepos.add(repo);
		}
		return returnRepos;
	}

	public static void writeData(String data) throws IOException{
		File file = new File("issues_data.csv");
	    // creates the file
	    //file.createNewFile();
	    // creates a FileWriter Object
	    FileWriter writer = new FileWriter(file, true); 
	    writer.append(data);
	    writer.flush();
	    writer.close();
	    return;
	}

	public static List<GHIssue> getIssues(GHRepository repo) throws IOException {
		return repo.getIssues(GHIssueState.valueOf("ALL"));
	}

	public static List<String> getIssueInfo(GHIssue issue) throws IOException {
		List<String> returnStrList = new ArrayList();
		String state = new String();
		if (issue.getState() == GHIssueState.valueOf("OPEN"))
			state = "OPEN";
		else 
			state = "CLOSED";
		returnStrList.add(String.valueOf(issue.getNumber()));
		returnStrList.add(state);
		String name;
		try {
			name = issue.getAssignee().getName();
		} catch(NullPointerException e) {
			name = "Not Assigned";
		}

		returnStrList.add(name);
		returnStrList.add(issue.getTitle());
		String issueBody = issue.getBody();
		System.out.println(issueBody);
		issueBody = issueBody.replace("\n", "").replace("\r","");
		System.out.println(issueBody);
		returnStrList.add(issueBody);
		return returnStrList;
	}

	public static String createIssueOutput(GHRepository repo) throws IOException {
		String data = new String(repo.getName() + "\n");
		List<String> issueInfo = new ArrayList();
		for (GHIssue issue : getIssues(repo)) {
			issueInfo = getIssueInfo(issue);
			for (String field : issueInfo) {
				data += field + "\t";
			}
			data += "\n";
		}
		return data;
	}

/* issue number, issue status (open/closed), user assigned to, title, description.*/
	public static void main(String[] args) {
		List<String> myRepos = Arrays.asList(
			"cs56-utilities-GEAR-scraper",
			"cs56-utilities-credit-card-validator",
			"cs56-scrapers-baseball-stats",
			"cs56-scrapers-ucsb-curriculum",
			"cs56-utilities-restaurant-list",
			"cs56-apidemo-facebook",
			"cs56-utilities-GEscraper",
			"cs56-github-apiv3-simple-rest-client",
			"cs56-utilities-GEAR-scraper",
			"cs56-utilities-GoldScheduler" );

		try {
			List<GHRepository> repos = getRepos(myRepos);
			for(GHRepository repo: repos) {
				writeData(createIssueOutput(repo));

			}
			//System.out.println(repos.toString());
			for (GHRepository repo : repos) {
				System.out.println(repo.getOwnerName());
			}
			System.out.println(repos.size());
			//getIssues("open")
		} catch(IOException e) {
			e.printStackTrace();
		}

	}


}

/*Collection<GHRepository> lst = GitHub.connect().getUser(username).getRepositories().values();
    for (GHRepository r : lst) {
		System.out.println(r.getName());
	}
*/

/*for(Map.Entry<String,GHRepository> entry: org.getRepositories().entrySet()) {
	System.out.println(entry.getKey());
	count++;
	}*/