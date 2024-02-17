package src;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import org.json.*;

public class PopReleases {

	//Instance variable
	ArrayList<Game> games = new ArrayList<>();

	
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

			JSONObject jSONResponse = new JSONObject(responseBody);
			
			String status = jSONResponse.optString("status");
			
			//If api fails to respond
			while(!status.equals("1"))
			{
				//Calling API
				request = HttpRequest.newBuilder()
						.uri(URI.create("https://store.steampowered.com/api/getappsincategory/?category=cat_newreleases&cc=us&l=english"))
						.method("GET", HttpRequest.BodyPublishers.noBody())
						.build();

				//Grabbing JSON response
				response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

				responseBody = response.body();
				
				jSONResponse = new JSONObject(responseBody);
				status = jSONResponse.optString("status");
			}
			
			JSONArray newReleases = jSONResponse.getJSONObject("tabs").getJSONObject("topsellers").getJSONArray("items");

			//Storing top releases
			for(int i = 0; i < newReleases.length(); i++) 
			{
				games.add(new Game(newReleases.getJSONObject(i).optString("id")));
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
