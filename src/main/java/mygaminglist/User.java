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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import database.Database;
import database.GameData;
import database.UsersImpl;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	public final static MongoCollection<Document> bannedusers= Database.database.getCollection("BannedUsers");
	
	
	//Instance variables
	private Object id;
	private String username, password;
	private List<Object> games;
	private List<String> friends;
	private boolean moderator;

	@JsonCreator
	public User(@JsonProperty("_id") Object id, @JsonProperty("username") String username, 
			@JsonProperty("password") String password, @JsonProperty("Games") List<Object> games, 
			@JsonProperty("Friends") List<String> friends, @JsonProperty("Moderartor") boolean moderator )
			 {
		this.id = id;
		this.username = username;
		this.password = password;
		this.games = games;
		this.friends = friends;
		this.setModerator(moderator);
	}

	public User(String username, String password)
	 {
		this.id = 0;
		this.username = username;
		this.password = password;
		this.games = new ArrayList<>();
	    this.friends = new ArrayList<>();
	    this.setModerator(false);
		}
	


	public Object getId() {
		return id;
	}



	public void setId(Object id) {
		this.id = id;
	}



	public String getUsername() {
		return username;
	}



	public void setName(String name) {
		this.username = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	
	public void setPassword(String password2) {
		this.password = password2;
		
	}
	
	public List<String> getFriends() {
		return friends;
	}
	
	public List<Object> getGames() {
		return games;
	}
	
		public boolean isModerator() {
		return moderator;
	}

	public void setModerator(boolean moderator) {
		this.moderator = moderator;
	}
	
//	Moderator Functions -----------------------------------------------------------------------------
public boolean banUser(String username) {
		
        User returnedUser = UsersImpl.getUser(username);
        if (returnedUser != null) {
        	
        	if(username.equals(this.getUsername())) {
        		System.out.println("Can't ban yourself");
        		return false;
        	}
        	
        	if(getBannedUser(username) != null) {
        		System.out.println("User is already banned");
        		return false;
        	}
        		
        	if(this.isModerator()) {
        		
        		Review.DeleteAllUserReviews(username);
        		addToBannedDatabase(returnedUser);
        		return true;
        	}
        	
        	return false;
        }
		
		return false;
		
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

	private void addToBannedDatabase(User user) {
		
		Document document = banneduserToDocument(user);
		
		// insert
		try {
            bannedusers.insertOne(document);
            System.out.println("Banned user " + user.getUsername());
        } catch (Exception exp) {
            System.err.println("Error happened when adding new user " + exp.getMessage());
        }
		
		
	}
	
	private Document banneduserToDocument(User user) {
        return new Document()
                .append("username", user.getUsername());
    }
	

	
    private static BannedUsers getBannedUser(String string) {
    	//Grabbing specified game
    	Bson filter = eq("username", string);
    	FindIterable<Document> result = bannedusers.find(filter);
    	Document user = result.first();
    	
    	if(user != null) {
    		
	    	String jsonResponse = user.toJson();
	    	
	    	try {
	    		//Returning game object from jsonResponse
				return GameData.map.readValue(jsonResponse, BannedUsers.class);
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
    	
    	}
    	return null;
    }
    
    public  boolean isBanned(String username) {
    	
    	if(getBannedUser(username) != null) {
    		System.out.println("This user is banned");
    		return true;
    	}
    	return false;
    }

	@Override
	public String toString() {
		return " username=" + username + "\n id=" + id + "\n password=" + password + "\n Games=" + games + "\n friends=" + friends;
				
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(username, other.username);
	}
	
	


}
