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
    Scanner sc = new Scanner(System.in);
    System.out.println("Which lab would you like to grade? (Enter a number 0 - 10)");
    String option = sc.nextLine();
    switch (option) {
      case "0":
        Lab00 lab0 = new Lab00();
        lab0.grade();
        break;
      case "1":
        Lab00 lab1 = new Lab00();
        lab1.grade();
        break;
      case "2":
        Lab02 lab2 = new Lab02();
        lab2.grade();
        break;
      case "3":
        Lab03 lab3 = new Lab03();
        lab3.grade();
        break;
      case "4":
        break;
      case "5":
        break;
      case "6":
        break;
      case "7":
        break;
      case "8":
        break;
      case "9":
        break;
      case "10":
        break;
      default:
        throw new IllegalArgumentException("Invalid option: " + option);
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
}
