import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import org.json.simple.JSONArray;


// Use https://www.javatpoint.com/java-json-example for help with JSON
// How to parse the String given: https://www.javatpoint.com/how-to-convert-string-to-json-object-in-java

public class Review {
	
	static String User = "{\"Users\":[ {\"username\":\"Abdel\", \"games\":[{\"appid\": \"271590\", \"score\":\"9\"}]} , {\"games\":[{\"appid\": \"271590\", \"score\":\"9\"}]}]}";
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, InterruptedException, ParseException {
       		
		JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(User);
        
        // Access the "Users" object
        JSONArray users = (JSONArray) json.get("Users");
        
        for (Object userObj : users) {
        	JSONObject user = (JSONObject) userObj;

            // Access the "name" property within the user object
            String name = (String) user.get("username");
            
            JSONArray games = (JSONArray) user.get("games");
            
            JSONObject newGame = new JSONObject();
            newGame.put("987654", "7"); // Example game ID and rating
            games.add(newGame);

            // Print the updated user object
            System.out.println("Updated User:");
            System.out.println(user.toJSONString());
//        	System.out.println(games);
            
//            System.out.println(name);
        }
        
//        System.out.println(name);
        
        review_game("Abdel", 1,1);
        review_game("Abdel", 2,2);
        review_game("Abdel", 1,3);
        review_game("Abdel", 2,5);
        
        System.out.println("Updated User!!:");
        System.out.println(User);
    }
	


	@SuppressWarnings("unchecked")
	public static boolean review_game(String username, int gameid, int review) throws ParseException {
		
		JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(User);
        
        JSONArray users = (JSONArray) json.get("Users");
        
        
        
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
                	
                    JSONObject game = (JSONObject) gameObj;
                    String gamename = (String) game.get("appid");

                    System.out.println(gamename);
                    
                    
                    // Check if the game ID matches the given game ID
                    if (gamename.equals(String.valueOf(gameid))) {
                        // Update the existing review for the game
                    	game.put("score", String.valueOf(review));
                        
                 
                        // Update the User string with the modified JSON data
                        User = json.toJSONString();
                        return true;
                    }
                }

                // If the game review for the given game does not exist, add a new review
                JSONObject newReview = new JSONObject();
                newReview.put("appid", String.valueOf(gameid));
                newReview.put("score", String.valueOf(review));
                games.add(newReview);

                // Update the User string with the modified JSON data
                User = json.toJSONString();
                return true;
            }
        }

        return false; // User not found
    }
	
}
