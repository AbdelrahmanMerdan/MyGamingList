package src;

import database.Database;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import com.fasterxml.jackson.databind.*;

public class MostPlayed {

	//Instance variable
	public static ArrayList<Game> games = new ArrayList<>();

	public MostPlayed() {
		try {
			//Calling API
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://api.steampowered.com/ISteamChartsService/GetGamesByConcurrentPlayers/v1/?"))
					.method("GET", HttpRequest.BodyPublishers.noBody())
					.build();

			//Grabbing JSON response
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

			String responseBody = response.body();

			//Getting array from JSON
			JsonNode jsonArray = Database.map.readTree(responseBody);
			jsonArray = jsonArray.get("response").get("ranks");
			
			for(JsonNode node : jsonArray) 
			{
				int id = node.get("appid").asInt();
				games.add(Database.getGame(id));
				
			}
			
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

}
