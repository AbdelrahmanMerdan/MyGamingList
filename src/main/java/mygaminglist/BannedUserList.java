
package mygaminglist;

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

import database.GameData;

import org.bson.Document;
import org.bson.conversions.Bson;
import mygaminglist.Game;
import java.util.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;

import static com.mongodb.client.model.Filters.*;

import static com.mongodb.client.model.Sorts.descending;


public class BannedUserList{
	
	static ArrayList<String> usernames = new ArrayList<>();

	public BannedUserList() {
		

		
		 User.bannedusers.find().forEach(user -> {
		        // Assuming `map` is an ObjectMapper instance
		        try {
		            BannedUsers banned= GameData.map.readValue(user.toJson(), BannedUsers.class);
		            
		            usernames.add(banned.getUsername());
		           
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    });
		
//		FindIterable<Document> result = GameData.games.find(filter);
	}
	
	public ArrayList<String> getBannesdUsers() {
		return usernames;
	}
	
	public String getuser(int i) {
		return usernames.get(i);
	}
	
	public int getSize() {
		return usernames.size();
	}
	
	public static void main(String[] args) {
		BannedUserList g = new BannedUserList();
		System.out.println(g.getBannesdUsers());
	}
	
	
}
