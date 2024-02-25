package src;

import com.mongodb.*;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.*;
import org.bson.*;
import org.bson.conversions.Bson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import java.net.*;
import java.net.http.*;
import java.io.IOException;


public class Database {
	
	//Client settings
	final static String connection = "mongodb+srv://2311team5:qn431J86d7xMEdpc@mygaminglist.igshqok.mongodb.net/?retryWrites=true&w=majority&appName=MyGamingList";

	final static ServerApi serverApi = ServerApi.builder()
			.version(ServerApiVersion.V1)
			.build();

	final static MongoClientSettings settings = MongoClientSettings.builder()
			.applyConnectionString(new ConnectionString(connection))
			.serverApi(serverApi)
			.build();

	final static MongoClient client = MongoClients.create(settings);

	//Database
	final static MongoDatabase database = client.getDatabase("MyGamingList");

	//Collections
	final static MongoCollection<Document> games = database.getCollection("Games");
	
	//ObjectMapper
	final static ObjectMapper map = new ObjectMapper();

	
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
    	Bson filter = and(eq("_id", id), exists("user_reviews"));
    	FindIterable<Document> result = games.find(filter);
    	
    	//Checking if details is unavailable for the game
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
    		updateReview());
    		
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
    		updateReview());
    		
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
		}
    	 	
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
		//Grabbing sys requirements
		String txt = jsonResponse.get("pc_requirements").get("minimum").asText();
		
		try {
			//Adding recommended sys requirements if there is
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
		
		Bson update = Updates.set("user_reviews", UserReview);
		Bson update2 = Updates.set("number_of_reviews", NumberofReview);
		Bson total = Updates.combine(update, update2);

		return total;
		
		
	}
}
