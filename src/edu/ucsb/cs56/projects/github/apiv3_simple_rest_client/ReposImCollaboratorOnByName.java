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

 
public class ReposImCollaboratorOnByName {

    public static String readAllBytes(String filename) throws Exception {
	return new String(Files.readAllBytes(Paths.get(filename))).trim();
    }
    
    public static void main(String[] args)    {
	String searchKey = "cs32-s15-lab04";

	if (args.length > 0) {
	    searchKey = args[0].trim();
	}

	System.out.println("searchKey=" + searchKey);

        try {
	    
	    String oauthToken = GithubAPIHelpers.readOauthToken("tokens/ucsb.ReadOnly.txt");
	    
	    System.out.println("oauthToken=" + oauthToken);

	    String urlString = "https://github.ucsb.edu/api/v3/user/repos";
	    urlString += "?access_token=" + oauthToken;
	    urlString += "&page=1&per_page=100";

	    URL url = new URL(urlString);

	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Accept", "application/json");
	    // GithubAPIHelpers.setOauthToken(conn,oauthToken);
	    GithubAPIHelpers.dumpHttpHeaders(conn,System.out);
	    
	    if (conn.getResponseCode() != 200) {
		throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
	    }

	    InputStream is = url.openStream();
	    JsonParser parser = Json.createParser(is);
	    
	    File file = new File("data.csv");
	    // creates the file
	    file.createNewFile();
	    // creates a FileWriter Object
	    FileWriter writer = new FileWriter(file); 
	    int count = 0;
	    String out = "";
	    int nestingLevel = 0;
	    Event e = null;
	    Event prevEvent = null;
	    java.util.Stack<String> eventStack = new java.util.Stack<String>();
	    String eString = null;
	    while (parser.hasNext()) {
		e = parser.next();

		if (e == Event.START_OBJECT) {
		    eventStack.push(eString);
		} else if (e == Event.END_OBJECT) {
		    eventStack.pop();
		} else if (e == Event.KEY_NAME) {
		    eString = parser.getString();
		    switch (eString) {
		    case "full_name":

			if (out != "")  {
			    
			    count ++;
			    writer.write(out + "\n");
			    out = "";
			}


			e = parser.next();
			assert e == Event.VALUE_STRING : "not VALUE_STRING";
			eString = parser.getString();
			if (eString.contains(searchKey)) {
			    out = eString;
			}
			break;
		    case "html_url":
			e = parser.next();
			assert e == Event.VALUE_STRING : "not VALUE_STRING";
			eString = parser.getString();
			if (eventStack.size() == 1) {
			    if (out != "")  {
				out += ("," + eString);
			    }
			}
			break;
		    case "login":
			e = parser.next();
			assert e == Event.VALUE_STRING : "not VALUE_STRING";
			eString = parser.getString();
			if (out != "" && eventStack.peek().equals("owner")) {
			    out += ("," + eString);
			    
			    UserInfo g = new UserInfo(eString);
			    out += ("," + g.getName());
			}
			
		    } // switch
		} 
	    } // while


	    if (out != "")  {
		
		count ++;
		writer.write(out + "\n");
		out = "";
	    }
	    
	    writer.flush();
	    writer.close();
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
 
