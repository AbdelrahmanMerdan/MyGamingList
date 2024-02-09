import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;


// Use https://www.javatpoint.com/java-json-example for help with JSON
// How to parse the String given: https://www.javatpoint.com/how-to-convert-string-to-json-object-in-java

public class ID {
	
	public static void main(String[] args) throws IOException, InterruptedException, ParseException {
       

		//	Example of using getId	
		String s = getId("Counter Strike");
		
		
    }
    
	
	
	public static String getId(String gameTitle) throws IOException, InterruptedException, ParseException {

		//	Adds the required %20 to the title	
        String replaceString = gameTitle.replace(" ", "%20");
        System.out.println(replaceString);
        
        // Sends a GET request to the Steam API at https://rapidapi.com/noah14112004/api/steam-api7/
        HttpRequest request = HttpRequest.newBuilder()
        		.uri(URI.create("https://steam-api7.p.rapidapi.com/search?query=" + replaceString + "&limit=1"))
        		.header("X-RapidAPI-Key", "148bc7ae7emsh9ce2312cdf4c7e7p188d53jsn20da7f80baeb")
        		.header("X-RapidAPI-Host", "steam-api7.p.rapidapi.com")
        		.method("GET", HttpRequest.BodyPublishers.noBody())
        		.build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        
        // Parse the data received from the API using the library above
        String responseBody = response.body();

        System.out.println(responseBody);

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(responseBody);
        
        // Access the "results" array
        JSONArray resultsArray = (JSONArray) json.get("results");

        // Returns ONE App Id as a string if it exists or returns an empty String
        if (!resultsArray.isEmpty()) {
            JSONObject firstResult = (JSONObject) resultsArray.get(0);
            Long appid = (Long) firstResult.get("appid");
            
            System.out.println(appid);
            return String.valueOf(appid);
        
        } else {
            System.out.println("No results found.");
            return "";
        }

    }
}