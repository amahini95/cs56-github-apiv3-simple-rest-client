package edu.ucsb.cs56.projects.github.apiv3_simple_rest_client;

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



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

 
public class CS56ProjectDump {

    public static String readAllBytes(String filename) throws Exception {
	return new String(Files.readAllBytes(Paths.get(filename))).trim();
    }


    public static void main(String[] args)    {

        try {

	    String oauthToken = GithubAPIHelpers.readOauthToken("tokens/MostPrivileges.txt");

	    URL url = new URL("https://api.github.com/orgs/UCSB-CS56-Projects/repos");
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Accept", "application/json");
	    GithubAPIHelpers.setOauthToken(conn,oauthToken);
	    GithubAPIHelpers.dumpHttpHeaders(conn,System.out);

	    if (conn.getResponseCode() != 200) {
		throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
	    }
	    
	    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	    String apiOutput = br.readLine();
	    System.out.println(apiOutput);
	    conn.disconnect();
	} catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();        
        } catch (Exception e) {
            e.printStackTrace();
        } // try

    } // main
}
 
