package database;

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
import org.bson.Document;
import org.bson.conversions.Bson;
import src.Game;
import java.util.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;


public class UserData implements Database, StubDatabase{
	

	//Collections
	final static MongoCollection<Document> users = database.getCollection("Users");
	
	//ObjectMapper
	final static ObjectMapper map = new ObjectMapper();

	
	public static boolean noUserExists(String name) {
    	//Filtering
    	Bson filter = eq("username", name);
    	FindIterable<Document> result = users.find(filter);
    	
    	//Checking if app does not exist
    	if(result.first() == null)
    	{
    		return true;
    	}
    	
    	return false;
    }

    public static UserDatabase getUser(String string) {
    	//Grabbing specified game
    	Bson filter = eq("username", string);
    	FindIterable<Document> result = users.find(filter);
    	Document user = result.first();
    	String jsonResponse = user.toJson();
    	
    	try {
    		//Returning game object from jsonResponse
			return map.readValue(jsonResponse, UserDatabase.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
}
