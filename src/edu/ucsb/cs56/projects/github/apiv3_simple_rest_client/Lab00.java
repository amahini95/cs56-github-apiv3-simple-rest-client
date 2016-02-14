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

public class Lab00 extends Lab {
  
  Lab00() {
    super();
  }

  protected void initGitHub(String username, String password) {
    try {
      github = GitHub.connectToEnterprise("https://github.ucsb.edu/api/v3/",username, password);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  protected void initRepos() {
    repos = new ArrayList<GHRepository>();
    // Lab00 does not need to initialize repos
  }

  public void grade() {
    try {
      File f = new File("output/output00.html");
      f.createNewFile();
      Path file = Paths.get("output/output00.html");
      GHRepository lab00;
      String html;
      String readMe = new String();
      List<GHCommit> commits = new ArrayList<GHCommit>();
      for (String name : names) {
        html = "<h1>" + name + "</h1><h2>cs56-w16-lab00</h2>";
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
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

}