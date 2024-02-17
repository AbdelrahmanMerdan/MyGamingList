import org.json.*;

import java.io.IOException;


public class Review {

	// STUB DATABASE OF USER
	static String User = "{\"Users\":[ {\"username\":\"Abdel\", \"games\":[{\"appid\": \"271590\", \"score\":\"9\"}]} , {\"games\":[{\"appid\": \"271590\", \"score\":\"9\"}]}]}";
	
	public static boolean review_game(String username, Game game, int review) {
		
		JSONObject jSONResponse = new JSONObject(User);
		JSONArray users = jSONResponse.getJSONArray("Users");
		
		
	      if(game.getName() == "Not Available") {
	    	  return false;
	      }
	        
	     // Iterate over each user
	        for (Object userObj : users) {
	            JSONObject user = (JSONObject) userObj;
	
	            // Access the "username" property within the user object
	            String name = (String) user.get("username");
	
	            // If the name matches the desired value
	            if (username.equals(name)) {
	
	                // Access the "games" array for the current user
	                JSONArray games = (JSONArray) user.get("games");
	
	                // Iterate over each game
	                for (Object gameObj : games) {
	                	
	                    JSONObject g = (JSONObject) gameObj;
	                    String gamename = (String) g.get("appid");
	
	 
	                    
	                    
	                    // Check if the game ID matches the given game ID
	                    if (gamename.equals(String.valueOf(game.getId()))) {
	                        
	                    	int score = Integer.parseInt((String) g.get("score"));
	                    	// Update Review on the games end    
	
	                    	game.updateReview(review, score);
	
	                    	// Update the existing review for the game
	                    	g.put("score", String.valueOf(review));
	                    	
	                        // Update the User string with the modified JSON data
	                    	User = jSONResponse.toString();
	                        return true;
	                    }
	                }
	
	                // If the game review for the given game does not exist, add a new review
	                JSONObject newReview = new JSONObject();
	                newReview.put("appid", String.valueOf(game.getId()));
	                newReview.put("score", String.valueOf(review));
	                games.put(newReview);
	                
	                // Update Review on the games end                        
	            	game.addReview(review);
	                
	                // Update the User string with the modified JSON data
	                User = jSONResponse.toString();
	                return true;
	            }
	        }
	
	        return false; // User not found
	    }
	
}
	
}
