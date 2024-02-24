package src;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import com.fasterxml.jackson.databind.*;

public class PopReleases {

	//ObjectMapper
	final static ObjectMapper map = new ObjectMapper();
	
	//Instance variable
	ArrayList<Game> games;

	public PopReleases() {
		try {
			//Calling API
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://store.steampowered.com/api/getappsincategory/?category=cat_newreleases&cc=us&l=english"))
					.method("GET", HttpRequest.BodyPublishers.noBody())
					.build();

			//Grabbing JSON response
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

			String responseBody = response.body();

			//Getting array from JSON
			JsonNode jsonArray = map.readTree(responseBody);
			jsonArray = jsonArray.get("tabs").get("topsellers").get("items");
			
			LinkedHashSet<Game> tmp = new LinkedHashSet<>();
			
			for(JsonNode node : jsonArray) 
			{
				if(node.get("type").asInt() == 0)
				{
					int id = node.get("id").asInt();
					
					//When database doesn't have the app
					if(Database.noAppExists(id))
					{
						Database.addApp(id);
					}
					
					tmp.add(Database.getGame(id));
				}
			}
			
			games = new ArrayList<>(tmp);

		} catch(InterruptedException e) {
			e.printStackTrace();

		} catch(IOException e) {
			e.printStackTrace();
		} 

	}
	
	public Game getGame(int i) {
		return games.get(i);
	}

	
	public int getSize() {
		return games.size();
	}
	
	
	@Override
	public String toString() {
		return games.toString();
	}

}
