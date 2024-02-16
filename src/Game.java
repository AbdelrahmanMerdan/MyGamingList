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
	private String name, id, description, sysRequire, criticRev, urlCriticRev;
	
	
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
        
   
        setName(game);
        setID(game);
        setDescription(game);
        setCriticRev(game);
        /*setSysRequire(game);
        */
		} catch(InterruptedException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} 
	}


	public void setName(JSONObject game) {
		name = game.getJSONObject("data").optString("name");
	}


	public void setID(JSONObject game) {
		id = game.getJSONObject("data").optString("steam_appid");
	}


	public void setDescription(JSONObject game) {
		description = game.getJSONObject("data").optString("detailed_description");
	}

	
	public void setCriticRev(JSONObject game) {
		
		criticRev = game.getJSONObject("data").optString("metacritic");
		
		//if there are no critic reviews
		if(criticRev.equals("")) {
			
			criticRev = "N/A";
		}
		
		else {
			
			for(int i = 0; i < criticRev.length(); i++) {
				
				//the response body for metacritic can only contain a h after the url so we can isolate for it			
				if(criticRev.charAt(i) == 'h') {
					
					//last 2 chars are '"}' so we need to get rid of them
					urlCriticRev = criticRev.substring(i, criticRev.length()- 2);
					
					//there could be multiple 'h's in the sequence so we must break
					break;
				}
			}
			
		}
	
	}

	public void setSysRequire(JSONObject game) {
		
	}


	@Override
	public String toString() {
		return "Name: " + name + "\tID: " + id + "\tsrc.Description: " + description + "Critic reviews: " + criticRev;
	}
	
	

}
