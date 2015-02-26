package edu.ucsb.cs56.projects.github.apiv3_simple_rest_client;


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

    public static String readAllBytes(String filename) throws Exception {
	return new String(Files.readAllBytes(Paths.get(filename))).trim();
    }


    public static void main(String[] args)    {

        try {

	    String oauthToken = Demo1.readAllBytes("tokens/MostPrivileges.txt");
	    System.out.println("Read oauthToken--length is " + oauthToken.length());
	    
	    URL url = new URL("https://api.github.com/orgs/UCSB-CS56-Projects/repos");

	    InputStream is = url.openStream();
	    JsonParser parser = Json.createParser(is);
	    while (parser.hasNext()) {
		Event e = parser.next();
		if (e == Event.KEY_NAME) {
		    switch (parser.getString()) {
		    case "name":
			parser.next();
			System.out.print(parser.getString());
			System.out.print(": ");
			break;
		    case "message":
			parser.next();
			System.out.println(parser.getString());
			System.out.println("---------");
			break;
		    } // switch
		} // if
	    } // while
	}  catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();        
	} catch (Exception e) {
	    e.printStackTrace();
	} // try

    } // main
}
 
