package mygaminglist;

import static com.mongodb.client.model.Filters.eq;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.fasterxml.jackson.databind.*;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

import database.GameData;

public class News extends NewsBlog{
	
	private static ArrayList<NewsBlog> newsBlogs = new ArrayList<>();
	
	public static void updateNewsBlog(int appId) {
		
		Bson filter_game = eq("_id", appId);
		FindIterable<Document> result_game = GameData.games.find(filter_game);
		Document found_game = result_game.first();
	    ArrayList<Document> newsBlogs = new ArrayList<>();
		
		try {
			
			//Calling API
			
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://api.steampowered.com/ISteamNews/GetNewsForApp/v2/?appid=" + appId))
					.method("GET", HttpRequest.BodyPublishers.noBody())	
					.build();
			
			//Grabbing JSON response
			
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = response.body();
			
			// Parsing JSON
			
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode jsonNode = objectMapper.readTree(responseBody);
	        
	        // Extracting data
	        
	        JsonNode appnews = jsonNode.get("appnews");
	        JsonNode newsItems = appnews.get("newsitems");
	       
	        for (JsonNode newsItem : newsItems) {
	        	
	            String title = newsItem.get("title").asText();
	            String url = newsItem.get("url").asText();
	            String contents = newsItem.get("contents").asText();
	            String author = newsItem.get("author").asText();
	            String website = newsItem.get("feedname").asText();
	            String date =  getDate(newsItem.get("date").asLong());
	            
	            // Check for empty strings and replace them with "N/A"
	      
	            if (title.isEmpty()) {
	                title = "N/A";
	            }
	            if (url.isEmpty()) {
	                url = "N/A";
	            }
	            if (contents.isEmpty()) {
	                contents = "N/A";
	            }
	            if (author.isEmpty()) {
	                author = "N/A";
	            }
	            if (website.isEmpty()) {
	                website = "N/A";
	            }
	            if (date.isEmpty()) {
	                date = "N/A";
	            }
	           
	            //making a doc
	            Document theNewsBlog = new Document()
	            		.append("title", title)
	                    .append("author", author)
	                    .append("url", url)
	                    .append("content", contents)
	                    .append("website" ,website )
	                    .append("date", date);
	           
	            //adding it to the database
	            newsBlogs.add(theNewsBlog);
	        }	
			
		} catch(InterruptedException e) {
			
			e.printStackTrace();
		} catch(IOException e) {
			
			e.printStackTrace();
		}
		
		Bson update = Updates.set("newsBlogs", newsBlogs);
		
		try {
			
			UpdateResult updateResult = GameData.games.updateOne(found_game, update);
			
			System.out.println("Updated newsBlog: "+updateResult.wasAcknowledged());
	
			
		}  catch(MongoException e) {
			
			System.err.println("ERROR: "+e);
		}
		
		
	}
	
	public ArrayList<NewsBlog> getNewPosts(){
		
		return newsBlogs;
	}
	
	public static String getDate(long time){
		 
		// Unix timestamp in seconds
		
        long timestamp = time; 

        // Convert Unix timestamp to Instant
        
        Instant instant = Instant.ofEpochSecond(timestamp);

        // Convert Instant to LocalDateTime in default system timezone
        
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        // Define a DateTimeFormatter for formatting the date and time
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Format the LocalDateTime using the formatter
        
        String formattedDateTime = dateTime.format(formatter);

        return formattedDateTime;
    }
	
}


