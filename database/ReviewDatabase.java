//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//import org.json.simple.JSONArray;

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



// Use https://www.javatpoint.com/java-json-example for help with JSON
// How to parse the String given: https://www.javatpoint.com/how-to-convert-string-to-json-object-in-java

public class ReviewDatabase {
	
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
		public final static MongoCollection<Document> games = database.getCollection("Games");
		
		public final static MongoCollection<Document> users = database.getCollection("Users");
		
		
		
		public static boolean UserExists(String name) {
	    	//Filtering
	    	Bson filter = eq("name", name);
	    	FindIterable<Document> result = users.find(filter);
	    	
	    	//Checking if app does not exist
	    	if(result.first() == null)
	    	{
	    		return false;
	    	}
	    	
	    	return true;
	    }
		
		public static boolean noAppReviews(int id) {
	    	//Filtering
	    	Bson filter = and(eq("_id", id), exists("user_reviews"));
	    	FindIterable<Document> result = games.find(filter);
	    	
	    	//Checking if details is unavailable for the game
	    	if(result.first() == null)
	    	{
	    		return false;
	    	}
	    	
	    	return true;
	    }
		
		
		public static void review_game(String username, Game game, int review) throws IOException {
			
			
			
			if(UserExists(username) & noAppReviews(game.getID())) {
				
//				Find User
				Bson filter_user = eq("name", username);
		    	FindIterable<Document> result_user = users.find(filter_user);
		    	Document user_found = result_user.first();
		    	
//				Find Game
		    	Bson filter_game = eq("_id", game.getID());
		    	FindIterable<Document> result_game = games.find(filter_game);
		    	Document game_found = result_game.first();
		    
		    	Bson update = Updates.combine(UpdateNumReview(game),
						UpdateUserReview(game,review));
		    	
		    	try {
					UpdateResult updateResult = games.updateOne(game_found, update);
					System.out.println("Acknowledged: "+updateResult.wasAcknowledged());
					
				} catch(MongoException e) {
					System.err.println("ERROR: "+e);
				}
		    	
			}
			
		}
		
		private static Bson UpdateNumReview(Game game) {
			
			int prevreviews = game.getNumOfReviews();
			prevreviews++;
			
			Bson Update = Updates.set("number_of_reviews", prevreviews);
			game.setNumOfReviews(prevreviews);
			return Update;
			
			
		}
		
		private static Bson UpdateUserReview(Game game, int review) {
			
			int prevreviews = game.getUserReviews();
			prevreviews += review;
			
			Bson Update = Updates.set("user_reviews", prevreviews);
			game.setUserReviews(prevreviews);
			return Update;
			
			
		}
		
		public static void main(String[] args) throws IOException {
			
			Game g = Database.getGame(1747830);
			
			review_game("Abdelrahaman", g, 9 );
			review_game("Abdelrahaman", g, 9 );

		  }
		
		

}
