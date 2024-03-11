//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//import org.json.simple.JSONArray;

package src;

import com.mongodb.*;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.*;

import database.Database;
import database.GameData;
import database.UsersImpl;

import org.bson.*;
import org.bson.conversions.Bson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import java.net.*;
import java.net.http.*;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;



// Use https://www.javatpoint.com/java-json-example for help with JSON
// How to parse the String given: https://www.javatpoint.com/how-to-convert-string-to-json-object-in-java

public class Review {
	
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
	    	Bson filter = eq("username", name);
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
	    	Bson filter = and(eq("_id", id), exists("num_of_reviews"));
	    	FindIterable<Document> result = games.find(filter);
	    	
	    	//Checking if details is unavailable for the game
	    	if(result.first() == null)
	    	{
	    		return false;
	    	}
	    	
	    	return true;
	    }
		
		public static boolean CheckUpdateGame(int id) {
			
			
			Bson filter = and(eq("_id", id), exists("name"));
	    	FindIterable<Document> result = games.find(filter);
	    	
	    	//Checking if details is unavailable for the game
	    	if(result.first() != null)
	    	{
	    		if(noAppReviews(id) == false) {
	    			GameData.updateAppDetails(id);
	    			return true;
	    		}
	    		return false;
	    	}
	    	return false;
	    	
		}
		
		
		public static void review_game(String username, Game game, int review, String comment, String reccomendation) 
				throws IOException {
			
			if( CheckUpdateGame(game.getID()) ) {
				
				game = GameData.getGame(game.getID());
				
				
			};
			
			if(UserExists(username) & noAppReviews(game.getID())) {
				
//				Find User
		    	Document user_found = find_user(username);
		    	
		    	
//				Find Game
		    	Document game_found = find_game(game);
		    
		    	
		    	Bson update = Updates.combine(UpdateNumReview(game),
		    			UpdateSumOfReviews(game,review),
						UpdateCommentReview(game, username,review,comment,reccomendation)
		    			);
		    	
		    	Bson userupdate = addUserReview(game,username,review,comment,reccomendation);
		    	
		    	try {
					UpdateResult updateResult = games.updateOne(game_found, update);
					System.out.println("Acknowledged: "+updateResult.wasAcknowledged());
					try {
						
						UpdateResult updateResultUser = users.updateOne(user_found, userupdate);
					
					}catch(MongoException e) {
						System.err.println("ERROR: "+e);
					}
					
					
				} catch(MongoException e) {
					System.err.println("ERROR: "+e);
				}
		    	
			}
			
		}
		
//		Update the Game Review
		private static Bson UpdateNumReview(Game game) {
			
			int prevreviews = game.getNumOfReviews();
			prevreviews++;
			
			Bson Update = Updates.set("num_of_reviews", prevreviews);
			game.setNumOfReviews(prevreviews);
			return Update;
			
			
		}
		
		
		private static Bson UpdateSumOfReviews(Game game, int review) {
			
			int prevreviews = game.getSumOfAllReviews();
			prevreviews += review;
			
			Bson Update = Updates.set("sum_of_all_reviews", prevreviews);
			game.setSumOfAllReviews(prevreviews);
			return Update;
			
			
		}

//		Add Comment
		private static Bson UpdateCommentReview (Game game, String username, int review, String comment, String reccomendation) {
			
			List<Object> comment_list = new ArrayList<>();
			List<Object> sub_comment = new ArrayList<>();
			
			comment_list.add(username);
			comment_list.add(review);
			comment_list.add(reccomendation);
			comment_list.add(comment);
			comment_list.add(sub_comment);
			
			game.addComment(comment_list);
			
			Bson Update = Updates.set("comments", game.getComment());
			
			return Update;

		}
		
		
		//Find User
		private static Document find_user(String username) {
			
			Bson filter_user = eq("username", username);
	    	FindIterable<Document> result_user = users.find(filter_user);
	    	Document user_found = result_user.first();
	    	
	    	return user_found;
			
		}
		
//		Find Game
		private static Document find_game(Game game) {
			
			Bson filter_game = eq("_id", game.getID());
	    	FindIterable<Document> result_game = games.find(filter_game);
	    	Document game_found = result_game.first();
	    	
	    	return game_found;
		}
		
		
//		Add Review to User Class
		private static Bson addUserReview(Game game, String username, int review, String comment, String reccomendation) {
			
			
			User user = UsersImpl.getUser(username);
			List<Object> newreview= new ArrayList<>();
			newreview.add(game.getID());
			newreview.add(game.getName());
			newreview.add(review);
			newreview.add(reccomendation);
			newreview.add(comment);
			
			List<Object> allreviews = user.getGames();
			
			allreviews.add(newreview);
			
			Bson Update = Updates.set("Games", allreviews);
			
			
			return Update;
			
			
		}

				@SuppressWarnings("unchecked")
		public static List<Object> getAllComments(Game game){
			
			List<Object> copy = new ArrayList<>();
			
			for(int i = 0; i < game.getComment().size(); i++) {
				
				int j = 0;
	
				for(j = 0; j < ((List<Object>) ((List<Object>) game.getComment().get(i)).get(4)).size(); j++) {
					
					if(j % 2 == 1) {
						
						copy.add(((List<Object>) ((List<Object>) game.getComment().get(i)).get(4)).get(j));
						
					}
					
				}
				
				
			}
			
			return copy;
		}

		@SuppressWarnings("unchecked")
		public List<Object> getAllUsersForComments(Game game){
			
			List<Object> copy = new ArrayList<>();
	
			for(int i = 0; i < game.getComment().size(); i++) {
				
				int j = 0;
	
				for(j = 0; j < ((List<Object>) ((List<Object>) game.getComment().get(i)).get(4)).size(); j++) {
					
					if(j % 2 == 0) {
						
						copy.add(((List<Object>) ((List<Object>) game.getComment().get(i)).get(4)).get(j));
						
					}
				}
			}
			
			return copy;
		}
		
		@SuppressWarnings("unchecked")
		public int retrieveIndex(User user, Game game) {
			
			int index = -1;
			
			for(int i = 0; i < game.getComment().size(); i++) {
				
				if(((List<Object>) game.getComment().get(i)).get(0).equals(user.getUsername())) {
					
					index = i;
					break;
					
				}
			}
			
			return index;
		}
		
	
		@SuppressWarnings("unchecked")
		public Object getComment(User userWithReccomandation, Game game){
			
			int index = retrieveIndex(userWithReccomandation, game);
			List<Object> copy = new ArrayList<>();
			
			for(int j = 0; j < ((List<Object>) ((List<Object>) game.getComment().get(index)).get(4)).size(); j++) {
				
				if(j % 2 == 1) {
					
					copy.add(((List<Object>) ((List<Object>) game.getComment().get(index)).get(4)).get(j));
					
				}
			}
			
			return copy;
		}
		
		@SuppressWarnings("unchecked")
		public Object getUserForComment(User userWithReccomandation, Game game){
			
			int index = retrieveIndex(userWithReccomandation,game);
			List<Object> copy = new ArrayList<>();
			
			for(int j = 0; j < ((List<Object>) ((List<Object>) game.getComment().get(index)).get(4)).size(); j++) {
				
				if(j % 2 == 0) {
					
					copy.add(((List<Object>) ((List<Object>) game.getComment().get(index)).get(4)).get(j));
					
				}
			}
			
			return copy;
		}
		
		@SuppressWarnings("unchecked")
		public void addCommentToUserReview(User user, String message, User userWithTheReview, Game game) {
			
			Document game_found = find_game(game);
			
			int reccomandationIndex = retrieveIndex(userWithTheReview, game);
			List<Object> toAdd = (List<Object>) ((List<Object>) game.getComment().get(reccomandationIndex)).get(4);
			
			//the first index will have the username and the seccond will have the message of the comment
			toAdd.add(user);
			toAdd.add(message);
			
			Bson Update = Updates.set("comments", toAdd);
			
			try {
			UpdateResult updateResult = games.updateOne(game_found, Update);
			System.out.println("Acknowledged: "+updateResult.wasAcknowledged());
			}catch(MongoException e) {
			System.err.println("ERROR: "+e);
		}
			
			
		}
		
		public static void main(String[] args) throws IOException {
			
//			CheckUpdateGame(1747800);
			
//			Get the Game from the database using it's ID
			Game game = GameData.getGame(105600);
//			System.out.print(game);
//			User 1 and User 2 review the game
			review_game("User2", game, 9, "Game is bad", "No" );


		  }
		
		

}
