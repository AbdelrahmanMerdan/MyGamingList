package src;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import org.json.*;

public class MostPlayed {

	//Instance variable
	ArrayList<Game> games = new ArrayList<>();

	
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

			JSONObject jSONResponse = new JSONObject(responseBody);

			JSONArray list = jSONResponse.getJSONObject("response").getJSONArray("ranks");

			//Storing most played games
			for(int i = 0; i < list.length(); i++) 
			{
				games.add(new Game(list.getJSONObject(i).optString("appid")));
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
	
	
	@Override
	public String toString() {
		return games.toString();
	}

}
