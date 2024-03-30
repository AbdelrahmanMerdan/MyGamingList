//<<<<<<< HEAD
//=======
//
//>>>>>>> branch 'Gradle' of https://github.com/AbdelrahmanMerdan/MyGamingList
package mygaminglist;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;

import database.Database;
import database.UsersImpl;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Moderator extends User {
	
	public final static MongoCollection<Document> bannedusers= Database.database.getCollection("BannedUsers");
	
	
	
	public Moderator(String username, String password)
	 {
		super(username, password);
	  	this.setModerator(true);
		}


	
	public boolean banUser(String username) {
		
        User returnedUser = UsersImpl.getUser(username);
        if (returnedUser != null) {
        	
        	if(this.isModerator()) {
        		
        		Review.DeleteAllUserReviews(username);
        		addToBannedDatabase(returnedUser);
        		return true;
        	}
        	
        	return false;
        }
		
		return false;
		
	}
	
	private void addToBannedDatabase(User user) {
		
		Document document = banneduserToDocument(user);
		
		// insert
		try {
            bannedusers.insertOne(document);
            System.out.println("Banned user " + user.getUsername());
        } catch (Exception exp) {
            System.err.println("Error happened when banning " + exp.getMessage());
        }
		
		
	}
	
	private Document banneduserToDocument(User user) {
        return new Document()
                .append("username", user.getUsername());
    }
	
	public boolean unbanUser(String username) {
		Bson filter = eq("username", username);
		
		try {
			bannedusers.deleteOne(filter);
            System.out.println("User " + username + " was unbanned");
            return true;
        } catch (Exception exp) {
            throw new RuntimeException("Error happened when unbanning user account");
        }
	}
	
	
	
}

	
