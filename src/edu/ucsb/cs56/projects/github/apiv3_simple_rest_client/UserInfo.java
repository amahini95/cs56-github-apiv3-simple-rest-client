package edu.ucsb.cs56.projects.github.apiv3_simple_rest_client;

import java.util.Map;
import java.util.List;
import javax.json.*;
import javax.json.spi.*;
import javax.json.stream.*;
import javax.json.stream.JsonParser.Event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

 
public class UserInfo {
    
    String login;

    /* int id;
    String avatar_url;
    String gravatar_id;
    String url;
    String html_url;
    String followers_url;
    String following_url;
    String gists_url;
    String starred_url;
    String subscriptions_url;
    String organizations_url;
    String repos_url;
    String events_url;
    String received_events_url;
    String type;
    boolean site_admin;
    String ldap_dn; */

    String name;

    /*    String company;
    String blog;
    String location;
    String email;
    String hireable;
    String bio;
    int public_repos;
    int public_gists;
    int followers;
    int following;
    String created_at;
    String updated_at;
    String suspended_at; */
    
    public String getLogin() { return this.login; }
    public String getName() { return this.name; }

    public UserInfo(String username) {
	String urlString = "https://github.ucsb.edu/api/v3/users/" + username;
	
	try {

	    String oauthToken = GithubAPIHelpers.readOauthToken("tokens/ucsb.ReadOnly.txt");
	    // System.out.println("oauthToken=" + oauthToken);
	    urlString += "?access_token=" + oauthToken;

	    URL url = new URL(urlString);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Accept", "application/json");
	    // GithubAPIHelpers.setOauthToken(conn,oauthToken);
	    // GithubAPIHelpers.dumpHttpHeaders(conn,System.out);
	    
	    if (conn.getResponseCode() != 200) {
		throw new RuntimeException("Failed : HTTP error code : " + 
					   conn.getResponseCode());
	    }

	    InputStream is = url.openStream();
	    JsonParser parser = Json.createParser(is);
	    
	    int count = 0;
	    String out = "";
	    int nestingLevel = 0;
	    while (parser.hasNext()) {
		Event e = parser.next();
		if (e == Event.START_OBJECT) {
		    nestingLevel++;
		} else if (e == Event.END_OBJECT) {
		    nestingLevel--;
		} else if (e == Event.KEY_NAME) {
		    switch (parser.getString()) {
		    case "login":
			parser.next();
			this.login = parser.getString();
			break;
		    case "name":
			parser.next();
			this.name = parser.getString();
			break;
		    } // switch
		} 
	    } // while
	}  catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();        
	} catch (Exception e) {
	    e.printStackTrace();
	} // try


	
    }

    public String toString() {
	return "{" + "\"login\":\"" + this.login + "\",\"name\":\"" + this.name + "\"}";
	
    }
    
    public static void main(String[] args)    {
	String searchKey = "pconrad";

	if (args.length > 0) {
	    searchKey = args[0].trim();
	}
	
	UserInfo g = new UserInfo(searchKey);

	System.out.println("g=" + g);

    } // main
}
 
