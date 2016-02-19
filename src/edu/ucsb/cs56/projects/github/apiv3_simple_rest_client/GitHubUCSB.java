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
  
  public static void main(String[] args) {
    Lab03 lab03 = new Lab03();
    lab03.grade();
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
}
