
package database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import mygaminglist.Moderator;
import mygaminglist.User;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ModeratorImpl extends UsersImpl implements Database {

    
    public final static MongoCollection<Document> moderators= database.getCollection("Moderators");

    private static final String TABLE_NAME = "Users";

    private static final String USER_KEY = "username";
    private static final String PWD_KEY = "password";
    private static final String GAMES_KEY = "Games";
    private static final String FRIENDS_KEY = "Friends";
    private static final String BANNEDUSERS_KEY = "BannedUsers";
    
    
 // Login and Sign up ------------------------------------------------------------------  
    public String login(Moderator moderator) {
    	Moderator returnedUser = this.get(moderator.getUsername());
        if (returnedUser == null) {
            throw new IllegalArgumentException("Username doesn't exist");
        }

        if (!returnedUser.getPassword().equals(moderator.getPassword())) {
            throw new IllegalArgumentException("Password incorrect");
        }

        return moderator.getUsername();
    }
    
    
    public boolean createAccount(Moderator moderator) {
        // validate username doesn't exist
        User returnedUser = this.get(moderator.getUsername());
        if (returnedUser != null) {
            throw new IllegalArgumentException("Username already exist, please choose another one");
        }

        // user -> doc
        Document document = userToDocument(moderator);

        // insert
        try {
        	moderators.insertOne(document);
            System.out.println("Added new user " + moderator.getUsername());
            return true;
        } catch (Exception exp) {
            System.err.println("Error happened when adding new user " + exp.getMessage());
            return false;
        }
    }
    
// Moderator/Document to Document/Moderator -------------------------------------------- 
    private Document userToDocument(Moderator moderator) {
        return new Document()
                .append(USER_KEY, moderator.getUsername())
                .append(PWD_KEY, moderator.getPassword())
                .append(GAMES_KEY, moderator.getGames())
                .append(FRIENDS_KEY, moderator.getFriends())
                ;
        
    }
    
    private Moderator documentToUser(Document doc) {
        String jsonResponse = doc.toJson();
    	
    	try {
    		//Returning game object from jsonResponse
			return GameData.map.readValue(jsonResponse, Moderator.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
// Get Moderator -----------------------------------------------------------------------
    
    public Moderator get(String username) {
        Bson filter = eq(USER_KEY, username);
        Document result = moderators.find(filter).first();

        if (result == null) {
            return null;
        }

        return documentToUser(result);
    }
    
    public static Moderator getUser(String string) {
    	//Grabbing specified game
    	Bson filter = eq("username", string);
    	FindIterable<Document> result = moderators.find(filter);
    	Document user = result.first();
    	String jsonResponse = user.toJson();
    	
    	try {
    		//Returning game object from jsonResponse
			return GameData.map.readValue(jsonResponse, Moderator.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }

 // Friends -------------------------------------------------------------------    
    
    public void updateFriend(String username, String friendName, String operation) {
        Bson filter = eq(USER_KEY, username);
        Document document = moderators.find(filter).first();

        Bson update;
        if (operation.equals("add")) {
            update = Updates.addToSet(FRIENDS_KEY, friendName);
        } else {
            update = Updates.pull(FRIENDS_KEY, friendName);
        }

        try {
            UpdateResult result = moderators.updateOne(document, update);
            System.out.println("Friend " + " was " + operation + " for " + username + ": " + result.wasAcknowledged());
        } catch (Exception exp) {
            System.err.println("Error happened when adding new friend " + exp.getMessage());
        }
    }

 // Delete Moderator -------------------------------------------------------------------
    public boolean delete(String username) {
        Bson filter = eq(USER_KEY, username);

        try {
        	moderators.deleteOne(filter);
            System.out.println("Moderator " + username + " was deleted");
            return true;
        } catch (Exception exp) {
            throw new RuntimeException("Error happened when deleting user account");
        }
    }


}
