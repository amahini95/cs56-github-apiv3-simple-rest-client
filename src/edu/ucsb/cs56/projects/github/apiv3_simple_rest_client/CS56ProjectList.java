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

 
public class CS56ProjectList {


    public static void main(String[] args)    {
    boolean descriptionCreated = false;

        try {

	    String oauthToken = GithubAPIHelpers.readOauthToken("tokens/MostPrivileges.txt");

	    URL url = new URL("https://api.github.com/orgs/UCSB-CS56-Projects/repos?page=1&per_page=100");
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    GithubAPIHelpers.setOauthToken(conn,oauthToken);

	    GithubAPIHelpers.dumpHttpHeaders(conn,System.out);

	    InputStream is = url.openStream();
	    JsonParser parser = Json.createParser(is);
	    
	    File file = new File("data.tab");
	    // creates the file
	    file.createNewFile();
	    // creates a FileWriter Object
	    FileWriter writer = new FileWriter(file); 
	    int count = 0;
	    while (parser.hasNext()) {
		String out = "";
		Event e = parser.next();
		if (e == Event.KEY_NAME) {
		    switch (parser.getString()) {
		    case "name":
			
			parser.next();
			if(!descriptionCreated){
			// System.out.println();
			// out+="\n";
			// writer.write("\n");

			}
			// System.out.print(parser.getString() + ", ");
			out = parser.getString() + "\t";
			// writer.write(parser.getString());
			// writer.write(", ");
			descriptionCreated=false;
			break;
		    case "description":

			parser.next();
			if(parser.getString().contains("W15-YES")) {
			    out += "W15-YES\t ";
			} else {
			    out += "OTHER\t ";			    
			}

			count ++;
			out += parser.getString().replace("|","\t");
			System.out.println(out);
			writer.write(out+"\n");
			descriptionCreated=true;
			out = "";
			
			//		writer.write("---------");
			break;
		    } // switch
		} // if
	    } // while
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
 
