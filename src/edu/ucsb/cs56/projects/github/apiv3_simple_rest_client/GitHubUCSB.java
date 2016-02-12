package edu.ucsb.cs56.projects.github.apiv3_simple_rest_client;

import org.kohsuke.github.*;
import java.util.*;
import org.apache.commons.io.IOUtils;
import java.nio.charset.Charset;

import java.nio.file.StandardOpenOption;
import java.nio.*;
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
  public static GitHub github;
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
      github = GitHub.connectToEnterprise("https://github.ucsb.edu/api/v3/","jdogg5566", "Whinfrey1");
      me = github.getMyself();
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
      int i = 0;
      while ((line = br.readLine()) != null) {
        if (i != 0){
          String[] columns = line.split(",");
          names.add(columns[5]);
        }
        i++;
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
      GHRepository lab00;
      String readMe = new String();
      Path file = Paths.get("output.html");
      List<GHCommit> commits = new ArrayList<GHCommit>();
      Set<String> exempt = new HashSet(Arrays.asList("jrcryan", "grinta", "jdogg5566", "pconrad", "arda", "chall01", "vincenicoara",
      "allisonshedden", "omeedrabani", "hanna", "mbahia"));
       ArrayList<String> urls = new ArrayList();
      for (String name : names) {
        String html = "<h1>" + name + "</h1><h2>cs56-w16-lab00</h2>";
        GHUser user = github.getUser(name);
        if (user == null) {
          user = github.getUser("samuelechu");
          html += "Error with Username";
        }
        Map<String, GHRepository> repos = user.getRepositories();
        lab00 = repos.get("cs56-w16-lab00");
        if (lab00 == null) {
          lab00 = repos.get("cs56-w15-lab00-");//samuelechu
          html += "ERROR WITH REPO NAME";
        }
        if(lab00 == null) {
          lab00 = repos.get("cs56-w15-lab00");//Kevin Lee
          html += "ERROR WITH REPO NAME";
        }
        
        try{
          readMe = IOUtils.toString(lab00.getReadme().read());
          String javadoc = readMe.substring(readMe.indexOf(":") + 2, readMe.indexOf("/javadoc")+8);
          html += "<p><a href='" + javadoc + "'>Javadoc Link</a></p>";
          commits = lab00.listCommits().asList();

        } catch(StringIndexOutOfBoundsException e) {
          html += "INVALID JAVADOC LINK";
        }
        catch(NullPointerException e) {
          html += "Lab00 does not exist or is private";
          readMe = "null";
          commits = new ArrayList<GHCommit>();
        }
        //Set<String> collaborators = lab00.getCollaboratorNames();
        html += "<ol>";

        for (GHCommit commit : commits)
          html += "<li>" + commit.getCommitShortInfo().getMessage() + "</li>";
        
        html += "</ol><h2>ReadMe Contents</h2><p>";
        html += readMe;
        html += "</p><br><br>";

        ArrayList al = new ArrayList();
        al.add(html);
        Files.write(file, al, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        System.out.println(html);
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