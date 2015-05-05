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

 
public class CS56PullRequestDump {


    public static void main(String[] args)    {
    boolean descriptionCreated = false;

        try {

	    String oauthToken = GithubAPIHelpers.readOauthToken("tokens/MostPrivileges.txt");

	    URL url = new URL("https://api.github.com/orgs/UCSB-CS56-Projects/repos?page=1&per_page=100");
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    GithubAPIHelpers.setOauthToken(conn,oauthToken);

	    // GithubAPIHelpers.dumpHttpHeaders(conn,System.out);

	    InputStream is = url.openStream();
	    JsonParser parser = Json.createParser(is);
	    String out="";
	    int count=0;
	    while (parser.hasNext()) {

		Event e = parser.next();
		if (e == Event.KEY_NAME) {
		    switch (parser.getString()) {
		    case "name":
			
			parser.next();
			String name = parser.getString() ;
			String pullsUrl = "https://api.github.com/repos/UCSB-CS56-Projects/" + name + "/pulls";
			String allPulls = PullRequestGetterThingy.getAllPullRequests(pullsUrl);
			System.out.println(name + "\n");
			System.out.println(allPulls);
			System.out.println("-------------------\n");
			break;
		    } // switch
		} // if
	    } // while
	    System.out.println("outputted: " + count + " projects" );
	    
	}  catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();        
	} catch (Exception e) {
	    e.printStackTrace();
	} // try





    } // main
}
 
