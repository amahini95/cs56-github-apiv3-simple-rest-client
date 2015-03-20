package edu.ucsb.cs56.projects.github.apiv3_simple_rest_client;

import java.util.Map;
import java.util.List;
import java.net.HttpURLConnection;

import java.nio.file.Files;
import java.nio.file.Paths;


public class GithubAPIHelpers {

    public static String readOauthToken(String filename) throws Exception {
	String oauthToken= new String(Files.readAllBytes(Paths.get(filename))).trim();
	if (oauthToken.length()!=40) {
	    throw new Exception("Check contents of " + filename + " because oauth token length should be 40 but it isn't.");
	}
	return oauthToken;
    }

    public static void setOauthToken(HttpURLConnection conn, String oauthToken) {
	conn.setRequestProperty("Authorization", "token " + oauthToken);
    }

    public static void dumpHttpHeaders(HttpURLConnection conn, java.io.PrintStream out) {
	Map<String, List<String>> map = conn.getHeaderFields();
	
	out.println("Printing All Response Header for URL: "
		    + conn.getURL().toString() + "\n");
	
	for (Map.Entry<String, List<String>> entry : map.entrySet()) {
	    out.println(entry.getKey() + " : " + entry.getValue());
	}
    }
    
}
