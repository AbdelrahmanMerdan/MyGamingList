package mygaminglist;

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
import com.fasterxml.jackson.databind.*;

public class News extends NewsBlog{
	
	private ArrayList<NewsBlog> newsBlogs = new ArrayList<>();
	
	public News(int appId) {
		
		try {
			
			//Calling API
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://api.steampowered.com/ISteamNews/GetNewsForApp/v2/?appid=" + appId))
					.method("GET", HttpRequest.BodyPublishers.noBody())	
					.build();
			//Grabbing JSON response
			HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			String responseBody = response.body();
			
			System.out.println(responseBody);
			
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
	           
	            NewsBlog theBlog = new NewsBlog(title, author, url, contents, website, date);
	           
	            newsBlogs.add(theBlog);
	        }	
			
		} catch(InterruptedException e) {
			
			e.printStackTrace();
		} catch(IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public ArrayList<NewsBlog> getNewPosts(){
		
		return newsBlogs;
	}
	
	public static String getDate(long time){
		 
        long timestamp = time; // Unix timestamp in seconds

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


