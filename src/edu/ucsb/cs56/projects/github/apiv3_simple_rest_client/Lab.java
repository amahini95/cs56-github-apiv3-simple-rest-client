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


public abstract class Lab {
  protected GHMyself me;
  protected GitHub github;
  protected List<GHRepository> repos;
  protected List<String> names;
  protected String rosterName = "roster.csv";
  protected String username, password;
  protected Set<String> admins = new HashSet(Arrays.asList(
    "jrcryan",
    "grinta",
    "jdogg5566",
    "pconrad",
    "arda",
    "chall01",
    "vincenicoara",
    "allisonshedden",
    "omeedrabani",
    "hanna",
    "mbahia"
  ));



  Lab() {
    try {
      Scanner sc = new Scanner(System.in);
      System.out.println("Please enter your GitHub UCSB username: ");
      username = sc.nextLine();
      System.out.println("Please enter your GitHub UCSB password: ");
      password = sc.nextLine();
      initGitHub(username, password);
      me = github.getMyself();
      initRepos();
      initNames();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  protected abstract void initGitHub(String username, String password);

  protected abstract void initRepos();

  protected void initNames() {
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

  public abstract void grade();


}
