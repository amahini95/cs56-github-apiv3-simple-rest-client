package edu.ucsb.cs56.projects.github.apiv3_simple_rest_client;

import org.kohsuke.github.*;
import java.util.*;


import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class GitHubUCSB {
  public static GHMyself me;
  public static List<GHRepository> repos;
  public static List<String> names;
  public static String rosterName = "roster.csv";
  public static String username, password;
  
  static {
    try {
      Scanner sc = new Scanner(System.in);
      // System.out.println("Please enter your GitHub UCSB username: ");
      // username = sc.nextLine();
      // System.out.println("Please enter your GitHub UCSB password: ");
      // password = sc.nextLine();
      me = GitHub.connectToEnterprise("https://github.ucsb.edu/api/v3/","jdogg5566", "Whinfrey1").getMyself();
      repos = me.listRepositories(50).asList();
      initNames();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  public static void initNames() {
    names = new ArrayList<String>();
    BufferedReader br = null;
    String line = "";
    try {

      br = new BufferedReader(new FileReader(rosterName));
      while ((line = br.readLine()) != null) {
        String[] columns = line.split(",");
        names.add(columns[5]);
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

  }

  public static void main(String[] args) {
    try {
      for(String name : names){
        for(GHRepository repo : repos) {
          Set<String> collaborators = repo.getCollaboratorNames();
          if (collaborators.contains(name)) {
            // Here
            System.out.println("<p>" + name + "<br>" + repo.getName() + "</p>");
            //GHContent repo.getReadme();
            String url = "http://www.cs.ucsb.edu/~" + name + "/cs56/lab00/javadoc";
            System.out.println("<p><a href='" + url + "'>Javadoc Link</a></p>");
            List<GHCommit> commits = repo.listCommits().asList();
            for (GHCommit commit : commits) {
              System.out.println(commit.getCommitShortInfo().getMessage());
            }
            // do stuff
          }
        }
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
    // for(GHRepository repo : repos) {
    //   Set<String> collaborators = repo.getCollaborators()
    //   for (String collaborator : collaborators)
    //     System.out.println(collaborator);
    //   List<GHCommit> commits = repo.listCommits().asList();
    //   for (GHCommit commit : commits) {
    //     System.out.println(commit.getCommitShortInfo().getMessage());
    //   }
    // }
  //}

}