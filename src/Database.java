package src;

import com.mongodb.*;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.*;
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
	static final MongoDatabase database = client.getDatabase("MyGamingList");
	
	//ObjectMapper
	final static ObjectMapper map = new ObjectMapper();
    
	
	public static boolean noAppExists(int id) {
    	//Grabbing games
    	MongoCollection<Document> games = database.getCollection("Games");
    	
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
    	//Grabbing games
    	MongoCollection<Document> games = database.getCollection("Games");
    	
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
    
    
    public static void updateAppDetails(int id) {
    	//Declaring and initializing 
    	String appID = String.valueOf(id);
    	JsonNode jsonResponse;
    	
    	//Grabbing games
    	MongoCollection<Document> games = database.getCollection("Games");
    	
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
    		updateShortDesc(jsonResponse, games, game);
    		updateDesc(jsonResponse, games, game);
    		updateCover(jsonResponse, games, game);
    		updateSysRequire(jsonResponse, games, game);
    		updateMetacritic(jsonResponse, games, game);
    		
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
    	
    	//Grabbing games
    	MongoCollection<Document> games = database.getCollection("Games");
    	
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
    		updateShortDesc(jsonResponse, games, game);
    		updateDesc(jsonResponse, games, game);
    		updateCover(jsonResponse, games, game);
    		updateSysRequire(jsonResponse, games, game);
    		updateMetacritic(jsonResponse, games, game);
    		
    	} catch(InterruptedException e) {
			e.printStackTrace();

		} catch(IOException e) {
			e.printStackTrace();
		}
    	 	
    }
    
    public static Game getGame(int id) {
    	//Grabbing games
    	MongoCollection<Document> games = database.getCollection("Games");
    	
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
	private static void updateShortDesc(JsonNode jsonResponse, MongoCollection<Document> games, Document game) {
		//Grabbing short description
		String txt = jsonResponse.get("short_description").asText();
		
		//Setting update option
		Bson update = Updates.set("short_description", txt);
		
		//Updating
		try {
			UpdateResult result = games.updateOne(game, update);
			System.out.println("Acknowledged: "+result.wasAcknowledged());
			
		} catch(MongoException e) {
			System.err.println("ERROR: "+e);
		}
	}


	private static void updateDesc(JsonNode jsonResponse, MongoCollection<Document> games, Document game) {
		//Grabbing description
		String txt = jsonResponse.get("about_the_game").asText();

		//Setting update option
		Bson update = Updates.set("description", txt);

		//Updating
		try {
			UpdateResult result = games.updateOne(game, update);
			
			System.out.println("Acknowledged: "+result.wasAcknowledged());
		} catch(MongoException e) {
			System.err.println("ERROR: "+e);
		}	
	}


	private static void updateCover(JsonNode jsonResponse, MongoCollection<Document> games, Document game) {
		//Grabbing img link
		String img = jsonResponse.get("header_image").asText();

		//Setting update option
		Bson update = Updates.set("cover_art", img);

		//Updating
		try {
			UpdateResult result = games.updateOne(game, update);
			System.out.println("Acknowledged: "+result.wasAcknowledged());
			
		} catch(MongoException e) {
			System.err.println("ERROR: "+e);
		}
	}


	private static void updateSysRequire(JsonNode jsonResponse, MongoCollection<Document> games, Document game) {
		//Grabbing sys requirements
		String txt = jsonResponse.get("pc_requirements").get("minimum").asText();
		
		try {
			//Adding recommended sys requirements if there is
			txt = txt+jsonResponse.get("pc_requirements").get("recommended").asText();
			
		} catch(NullPointerException e) {
			//Else, do nothing
			
		} finally {
			//Setting update option
			Bson update = Updates.set("pc_requirements", txt);

			//Updating
			try {
				UpdateResult result = games.updateOne(game, update);
				System.out.println("Acknowledged: "+result.wasAcknowledged());
				
			} catch(MongoException e) {
				System.err.println("ERROR: "+e);
			}
		}
	}


	private static void updateMetacritic(JsonNode jsonResponse, MongoCollection<Document> games, Document game) {
		//Default values for score and link
		String score = "N/A";
		String link = "N/A";
		
		try {
			//Adding metacritic reviews if there is
			score = jsonResponse.get("metacritic").get("score").asText();
			link = jsonResponse.get("metacritic").get("url").asText();
			
		} catch(NullPointerException e) {
			//Else, do nothing
			
		} finally {
			//Setting update option
			Bson update = Updates.set("meta_score", score);
			Bson update2 = Updates.set("meta_link", link);

			//Updating
			try {
				UpdateResult result = games.updateOne(game, update);
				result = games.updateOne(game, update2);
				System.out.println("Acknowledged: "+result.wasAcknowledged());
				
			} catch(MongoException e) {
				System.err.println("ERROR: "+e);
			}
		}
	}
}
