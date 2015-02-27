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
	   // writer.write("Read oauthToken--length is " + oauthToken.length());
	    
	    URL url = new URL("https://api.github.com/orgs/UCSB-CS56-Projects/repos");

	    InputStream is = url.openStream();
	    JsonParser parser = Json.createParser(is);

      File file = new File("data.csv");
      // creates the file
      file.createNewFile();
      // creates a FileWriter Object
      FileWriter writer = new FileWriter(file); 

	    while (parser.hasNext()) {
		Event e = parser.next();
		if (e == Event.KEY_NAME) {
		    switch (parser.getString()) {
		    case "name":
			parser.next();
			writer.write(parser.getString());
			writer.write(", ");
			break;
		    case "description":
			parser.next();
			writer.write(parser.getString().replace(",","\",\"").replace("|",","));
			writer.write("\n");
	//		writer.write("---------");
			break;
		    } // switch
		} // if
	    } // while
	      writer.flush();
      writer.close();
	}  catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();        
	} catch (Exception e) {
	    e.printStackTrace();
	} // try



    } // main
}
 
