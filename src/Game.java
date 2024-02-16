package src;

import java.io.IOException;
import java.util.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.*;


public class Game {
	
	//Instance variables
	private String name, id, description, sysRequire, criticScore, criticURL;
	
	
	public Game(String id) {
		try {
			//Calling api
			HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://store.steampowered.com/api/appdetails?appids="+id+"&I=english"))
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		
			//Grabbing json response
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		
			String responseBody = response.body();
		
			JSONObject jSONResponse = new JSONObject(responseBody);
			JSONObject game = jSONResponse.getJSONObject(id);
        
   			//Setting Attributes
        		setName(game);
        		setID(game);
        		setDescription(game);
        		setCriticRev(game);
        		setSysRequire(game);
        
		} catch(InterruptedException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(JSONException e) {
			//Here just in case steamappID turns out to be empty
			name = "Not Available";
		} 
	}


	public void setName(JSONObject game) {
		name = game.getJSONObject("data").optString("name");
	}


	public void setID(JSONObject game) {
		id = game.getJSONObject("data").optString("steam_appid");
	}


	public void setDescription(JSONObject game) {
		description = game.getJSONObject("data").optString("about_the_game");
	}

	
	public void setCriticRev(JSONObject game) {
		try {
	            criticScore = game.getJSONObject("data").getJSONObject("metacritic").optString("score");
	            criticURL = game.getJSONObject("data").getJSONObject("metacritic").optString("url");
			
	        } catch(JSONException e) {
	            criticScore = "N/A";
	            criticURL = "N/A";
	        }
	}

	public void setSysRequire(JSONObject game) {
		sysRequire = game.getJSONObject("data").optString("pc_requirements");

	}

	public String getCurrentPlayers() {
		try {
			//Calling API
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://api.steampowered.com/ISteamUserStats/GetNumberOfCurrentPlayers/v1/?appid="+id))
					.method("GET", HttpRequest.BodyPublishers.noBody())
					.build();

			//Grabbing JSON response
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

			String responseBody = response.body();

			JSONObject jSONResponse = new JSONObject(responseBody);

			//Returning player count
			return jSONResponse.getJSONObject("response").optString("player_count");

		} catch(InterruptedException e) {
			e.printStackTrace();

		} catch(IOException e) {
			e.printStackTrace();
		}

		//If above operation was unsuccessful.
		return "ERROR";
	}

	public String getName() {
		return name;
	}


	public String getId() {
		return id;
	}


	public String getDescription() {
		return description;
	}


	public String getSysRequire() {
		return sysRequire;
	}


	public String getCriticScore() {
		return criticScore;
	}


	public String getCriticURL() {
		return criticURL;
	}


	@Override
	public String toString() {
		return name;
	}
	
}
