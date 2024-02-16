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

public class GetGameInfo {
	
	public static void main(String[] args) throws IOException, InterruptedException, ParseException {
       

		//	Example of using getId	
		String s = getId("Counter-Strike");
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://steam-api7.p.rapidapi.com/appDetails/271590"))
				.header("X-RapidAPI-Key", "148bc7ae7emsh9ce2312cdf4c7e7p188d53jsn20da7f80baeb")
				.header("X-RapidAPI-Host", "steam-api7.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		
		String responseBody = response.body();

        System.out.println(responseBody);

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(responseBody);
        
        // Access the "results" array
        String resultsArray = (String) json.get("short_description");
        
        
        System.out.println(resultsArray);
		
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
	
	public static String[] getInfo(String appid) throws IOException, InterruptedException {
		
		String id = String.valueOf(appid);
        // Sends a GET request to the Steam API at https://rapidapi.com/noah14112004/api/steam-api7/
        HttpRequest request = HttpRequest.newBuilder()
        		.uri(URI.create("https://steam-api7.p.rapidapi.com/appDetails/"+id))
        		.header("X-RapidAPI-Key", "148bc7ae7emsh9ce2312cdf4c7e7p188d53jsn20da7f80baeb")
        		.header("X-RapidAPI-Host", "steam-api7.p.rapidapi.com")
        		.method("GET", HttpRequest.BodyPublishers.noBody())
        		.build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        
        String responseBody = response.body();
        
        String description = getDescription(responseBody);
        String requirement = getRequirement(responseBody);
        String critic = getCritic(responseBody);
        
        String[] result = new String[3];
        
        result[0] = description;
        result[1] = requirement;
        result[2] = critic;
        
       
        
        return result;
 
	}
	
	public static String getDescription(String json) {
		
		return "";
		
	}
	
	public static String getRequirement(String json) {
		
		return "";
		
	}
	
	public static String getCritic(String json) {
		
		return "";
		
	}
}
