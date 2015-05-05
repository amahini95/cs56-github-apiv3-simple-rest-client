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

 
public class PullRequestGetterThingy {


    public static String getAllPullRequests(String urlString)    {

	String result="";
        try {
	    
	    String oauthToken = GithubAPIHelpers.readOauthToken("tokens/MostPrivileges.txt");

	    URL url = new URL(urlString);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    GithubAPIHelpers.setOauthToken(conn,oauthToken);
	    GithubAPIHelpers.dumpHttpHeaders(conn,System.out);

	    if (conn.getResponseCode() != 200) {
		throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
	    }
	    

	    // NOW DO SOMETHING USEFUL WITH THE INPUT STREAM

	    InputStream is = url.openStream();
	    JsonParser parser = Json.createParser(is);

	    while (parser.hasNext()) {
		Event e = parser.next();
		if (e == Event.KEY_NAME) {
		    switch (parser.getString()) {
		    case "url":
			parser.next();
			String theUrl = parser.getString();
			result += "\nurl:\t" + theUrl + "\t";
			break;
		    case "title":
			parser.next();
			String theTitle = parser.getString();
			result += "title:\t" + theTitle + "\t";
			break;
		    case "body":
			parser.next();
			String theBody = parser.getString();
			result += "theBody:\t" + theBody + "\n";
			break;
		    } // switch
		} // if
	    } // while





	    // DISCONNECT!  WE'RE DONE !!!
	    conn.disconnect();
	    
	}  catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();        
	} catch (Exception e) {
	    e.printStackTrace();
	} // try


	return result;

    } // main
}
 
