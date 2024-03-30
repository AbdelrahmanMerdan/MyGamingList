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



public class HighestReviewed{
	
	static ArrayList<Integer> ids = new ArrayList<>();

	public static void HighestReview() {
		
		Bson filter = and(gt("average_of_reviews", 0), lte("average_of_reviews", 10));
		
		 GameData.games.find(filter).forEach(game -> {
		        // Assuming `map` is an ObjectMapper instance
		        try {
		            Game g = GameData.map.readValue(game.toJson(), Game.class);
		            
		            ids.add(g.getID());
		           
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    });
		
//		FindIterable<Document> result = GameData.games.find(filter);
	}
	
	public static void main(String[] args) {
		HighestReview();
		System.out.println(ids);
	}
	
}
