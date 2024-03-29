package database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import mygaminglist.Game;
import java.util.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Filters.eq;

public class GameData implements Database, StubDatabase {

    //Collection
    public static MongoCollection<Document> games = database.getCollection("Games");

    //ObjectMapper
    public final static ObjectMapper map = new ObjectMapper();
    

    public static boolean noAppExists(int id) {
    	//Filtering
    	Bson filter = eq("_id", id);
    	FindIterable<Document> result = games.find(filter);

    	//Checking if app does not exist
    	if(result.first() == null)
    	{
    		return true;
    	}

    	return false;
    }

	public static boolean noAppDetails(int id) {
    	//Filtering
    	Bson filter = and(eq("_id", id), exists("description"));
    	FindIterable<Document> result = games.find(filter);
    	
    	//Checking if details is unavailable for the game
    	if(result.first() == null)
    	{
    		return true;
    	}
    	
    	return false;
    }
	
	public static boolean noAppReviews(int id) {
    	//Filtering
    	Bson filter = and(eq("_id", id), exists("num_of_reviews"));
    	FindIterable<Document> result = games.find(filter);
    	
    	//Checking if reviews is unavailable for the game
    	if(result.first() == null)
    	{
    		return true;
    	}
    	
    	return false;
    }
	
	public static boolean noGameStats(int id) {
    	//Filtering
    	Bson filter = and(eq("_id", id), exists("player_count"));
    	FindIterable<Document> result = games.find(filter);
    	
    	//Checking if game stats is unavailable for the game
    	if(result.first() == null)
    	{
    		return true;
    	}
    	
    	return false;
    }
    
    
    public static void updateAppDetails(int id) {
    	//Declaring and initializing 
    	String appID = String.valueOf(id);
    	JsonNode jsonResponse;
    	
    	//Grabbing specified game
    	Bson filter = eq("_id", id);
    	FindIterable<Document> result = games.find(filter);
    	Document game = result.first();
    	
    	try {
    		//Calling API
    		HttpRequest request = HttpRequest.newBuilder()
    				.uri(URI.create("https://store.steampowered.com/api/appdetails?appids="+appID+"&I=english"))
    				.method("GET", HttpRequest.BodyPublishers.noBody())
    				.build();

    		//Grabbing JSON response
    		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    		String responseBody = response.body();
    		
    		jsonResponse = map.readTree(responseBody);
    		jsonResponse = jsonResponse.get(appID).get("data");
    		
    		//Updating database
    		Bson update = Updates.combine(updateShortDesc(jsonResponse),
    		updateDesc(jsonResponse),
    		updateCover(jsonResponse),
    		updateSysRequire(jsonResponse),
    		updateMetacritic(jsonResponse),
    		updateReview(),
    		updateComment(),
    		updateGameStats()
    				);
    		
    		//Updating
			try {
				UpdateResult updateResult = games.updateOne(game, update);
				System.out.println("Updated Game: "+updateResult.wasAcknowledged());
				
			} catch(MongoException e) {
				System.err.println("ERROR: "+e);
			}
    		
    	} catch(InterruptedException e) {
			e.printStackTrace();

		} catch(IOException e) {
			e.printStackTrace();
			
        } catch(NullPointerException e) {
			//When game is not available in Canada :(
			setDefaultParameters(game);
    	}
    }

    public static void removeApp(int id) {
    	Bson filter = eq("_id", id);

    	//Deleting game in database
    	try {
    		DeleteResult deleteResult= GameData.games.deleteOne(filter);
    		System.out.println("Acknowledged: " + deleteResult.wasAcknowledged());

    	} catch(MongoException e) {
    		System.err.println("ERROR: "+e);
    	}
    	
    }
    
    
    public static void addApp(int id) {
    	//Declaring and initializing 
    	String appID = String.valueOf(id);
    	JsonNode jsonResponse;
    	
    	try {
    		//Calling API
    		HttpRequest request = HttpRequest.newBuilder()
    				.uri(URI.create("https://store.steampowered.com/api/appdetails?appids="+appID+"&I=english"))
    				.method("GET", HttpRequest.BodyPublishers.noBody())
    				.build();

    		//Grabbing JSON response
    		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    		String responseBody = response.body();
    		
    		jsonResponse = map.readTree(responseBody);
    		jsonResponse = jsonResponse.get(appID).get("data");
    		
    		//Creating new entry
        	try {
        		InsertOneResult result = games.insertOne(new Document()
                        .append("_id", id)
                        .append("name", jsonResponse.get("name").asText()));
                System.out.println("Added New Game: "+result.wasAcknowledged());
                
        	} catch(MongoException e) {
        		System.err.println("ERROR: "+e);
        	}
        	
        	//Grabbing specified game
        	Bson filter = eq("_id", id);
        	FindIterable<Document> result = games.find(filter);
        	Document game = result.first();
    		
    		//Updating database
    		Bson update = Updates.combine(updateShortDesc(jsonResponse),
    		updateDesc(jsonResponse),
    		updateCover(jsonResponse),
    		updateSysRequire(jsonResponse),
    		updateMetacritic(jsonResponse),
    		updateReview(),
    		updateComment(),
    		updateGameStats()
    		);
    		
    		//Updating
			try {
				UpdateResult updateResult = games.updateOne(game, update);
				System.out.println("Acknowledged: "+updateResult.wasAcknowledged());
				
			} catch(MongoException e) {
				System.err.println("ERROR: "+e);
			}
    		
    	} catch(InterruptedException e) {
			e.printStackTrace();

		} catch(IOException e) {
			e.printStackTrace();
			
		} catch(NullPointerException e) {
			//When game is not available in Canada
			Document na = new Document().append("_id", id).append("name", "REGION-LOCKED GAME");
			
			try {
				InsertOneResult result = games.insertOne(na);
				System.out.println("Added region-locked Game: "+result.wasAcknowledged());
			} catch (MongoException f) {
				System.err.println("ERROR: "+f);
			}
		} 	 
    }
    
   public static void setReviewsComments(int id) {
	   Bson update = Updates.combine(updateReview(), updateComment());
	   
	   //Grabbing specified game
	   Bson filter = eq("_id", id);
	   FindIterable<Document> result = games.find(filter);
	   Document game = result.first();
	   
	   //Updating
	   try {
		   UpdateResult updateResult = games.updateOne(game, update);
		   System.out.println("Added Only Reviews: "+updateResult.wasAcknowledged());

	   } catch(MongoException e) {
		   System.err.println("ERROR: "+e);
	   }  
   }
   
   public static void setGameStats(int id) {
	   Bson update = updateGameStats();
	   
	   //Grabbing specified game
	   Bson filter = eq("_id", id);
	   FindIterable<Document> result = games.find(filter);
	   Document game = result.first();
	   
	   //Updating
	   try {
		   UpdateResult updateResult = games.updateOne(game, update);
		   System.out.println("Added Only Game Stats: "+updateResult.wasAcknowledged());

	   } catch(MongoException e) {
		   System.err.println("ERROR: "+e);
	   }  
   }
    
    public static String getName(int id) {
    	//Grabbing specified game
    	Bson filter = eq("_id", id);
    	FindIterable<Document> result = games.find(filter);
    	Document game = result.first();
    	
    	return game.getString("name");
    }

	
    public static Game getGame(int id) {
    	//Grabbing specified game
    	Bson filter = eq("_id", id);
    	FindIterable<Document> result = games.find(filter);
    	Document game = result.first();
    	String jsonResponse = game.toJson();
    	
    	try {
    		//Returning game object from jsonResponse
			return map.readValue(jsonResponse, Game.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    private static void setDefaultParameters(Document game) {
    	Bson update1 = Updates.set("short_description", "Game is not available in Canada.");
		Bson update2 = Updates.set("description", "Game is not available in Canada");
		Bson update3 = Updates.set("cover_art", "");
		Bson update4 = Updates.set("pc_requirements", "System requirements are not available.");
		Bson update5 = Updates.set("meta_score", "N/A");
		Bson update6 = Updates.set("meta_link", "N/A");
		
		Bson update = Updates.combine(update1, update2, update3, update4, update5, update6, updateReview(), updateComment(), updateGameStats());
		
		//Updating
		try {
			UpdateResult updateResult = games.updateOne(game, update);
			System.out.println("Acknowledged: "+updateResult.wasAcknowledged());
			
		} catch(MongoException f) {
			System.err.println("ERROR: "+f);
		}
    }
    
	private static Bson updateShortDesc(JsonNode jsonResponse) {
		//Grabbing short description
		String txt = jsonResponse.get("short_description").asText();
		
		//Setting update option
		Bson update = Updates.set("short_description", txt);
		
		return update;
	}


	private static Bson updateDesc(JsonNode jsonResponse) {
		//Grabbing description
		String txt = jsonResponse.get("about_the_game").asText();

		//Setting update option
		Bson update = Updates.set("description", txt);

		return update;
	}


	private static Bson updateCover(JsonNode jsonResponse) {
		//Grabbing img link
		String img = jsonResponse.get("header_image").asText();

		//Setting update option
		Bson update = Updates.set("cover_art", img);

		return update;
	}


	private static Bson updateSysRequire(JsonNode jsonResponse) {
		String txt = "N/A";
		
		try {
			txt = jsonResponse.get("pc_requirements").get("minimum").asText();
		} catch(NullPointerException e) {
			//Else, do nothing
		}
		
		try {
			txt = txt+jsonResponse.get("pc_requirements").get("recommended").asText();
		} catch(NullPointerException e) {
			//Else, do nothing
		}
		
		//Setting update option
		Bson update = Updates.set("pc_requirements", txt);

		return update;
		
	}


	private static Bson updateMetacritic(JsonNode jsonResponse) {
		//Default values for score and link
		String score = "N/A";
		String link = "N/A";
		
		try {
			//Adding metacritic reviews if there is
			score = jsonResponse.get("metacritic").get("score").asText();
			link = jsonResponse.get("metacritic").get("url").asText();
		} catch(NullPointerException e) {
			//Else, do nothing
		} 
		
		//Setting update option
		Bson update = Updates.set("meta_score", score);
		Bson update2 = Updates.set("meta_link", link);
		Bson total = Updates.combine(update, update2);

		return total;
		
	}
	
	private static Bson updateReview() {
		
		int UserReview = 0;
		int NumberofReview = 0;
		
		Bson update = Updates.set("num_of_reviews", UserReview);
		Bson update2 = Updates.set("sum_of_all_reviews", NumberofReview);
		Bson total = Updates.combine(update, update2);

		return total;
		
	}
	
	private static Bson updateComment() {
		
		List<Object> comments = new ArrayList<>();
		
		Bson update = Updates.set("comments", comments);
		

		return update;
		
	}
	
	private static Bson updateGameStats() {
		int players = 0;
		int peak = 0;
		
		Bson update = Updates.set("player_count", players);
		Bson update2 = Updates.set("twenty_four_hr_peak", peak);
		Bson total = Updates.combine(update, update2);
		
		return total;
		
	}
}
