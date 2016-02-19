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

public class Lab03 extends Lab {
  
  Lab03() {
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
    // Lab03 does not need to initialize repos
  }

  public void grade() {
    try {
      String html;
      File f = new File("output/output03.html");
      f.createNewFile();
      Path file = Paths.get("output/output03.html");
      GHRepository lab03;
      List<GHCommit> commits = new ArrayList<GHCommit>();
      String readMe = new String();
      List<String> javadocs = new ArrayList<String>();

      ArrayList<String> sources = new ArrayList(Arrays.asList(
       "src/Polynomial.java",
       "build.xml",
        ".gitignore",
       "README.md"
      ));

      for(int i = 0 ; i < names.size(); i += 2) {
        html = "<h1>" + names.get(i) + " and " + names.get(i+1) + "</h1><h2>cs56-w16-lab03</h2>";
        lab03 = github.getUser(names.get(i)).getRepository("cs56-w16-lab03");        
        
        if (lab03 == null) {
          lab03 = github.getUser(names.get(i + 1)).getRepository("cs56-w16-lab03");        
        }
        if (lab03 == null) {
          html += "<p>There was an error, please check manually</p>";
          ArrayList al = new ArrayList();
          al.add(html);
          Files.write(file, al, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
          continue;
        }

        for(String source : sources) {
          try {
            html += "<p><a href='" + lab03.getFileContent(source).getHtmlUrl() + "'>" + source + "</a></p>";
          } catch(FileNotFoundException e) {
              lab03 = github.getUser(names.get(i + 1)).getRepository("cs56-w16-lab03");
              html += "<p><a href='" + lab03.getFileContent(source).getHtmlUrl() + "'>" + source + "</a></p>";
            }
        }

        commits = lab03.listCommits().asList();

        html += "<ol>";
        for (GHCommit commit : commits)
          html += "<li>" + commit.getCommitShortInfo().getMessage() + "</li>";      
        html += "</ol>";
        ArrayList al = new ArrayList();
        al.add(html);
        Files.write(file, al, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }


}
