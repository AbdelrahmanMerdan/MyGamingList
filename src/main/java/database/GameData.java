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
import mygaminglist.Game;
import java.util.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Filters.eq;

public class GameData implements Database, StubDatabase {

    //Collection
    public static MongoCollection<Document> games = database.getCollection("Games");

    //ObjectMapper
    public final static ObjectMapper map = new ObjectMapper();
   
    
    public static boolean noAppExists(int id) {
    	//Filtering
    	Bson filter = eq("_id", id);
    	FindIterable<Document> result = games.find(filter);

    	//Checking if app does not exist
    	if(result.first() == null)
    	{
    		return true;
    	}

    	return false;
    }

	public static boolean noAppDetails(int id) {
    	//Filtering
    	Bson filter = and(eq("_id", id), exists("description"));
    	FindIterable<Document> result = games.find(filter);
    	
    	//Checking if details is unavailable for the game
    	if(result.first() == null)
    	{
    		return true;
    	}
    	
    	return false;
    }
	
	public static boolean noAppReviews(int id) {
    	//Filtering
    	Bson filter = and(eq("_id", id), exists("num_of_reviews"));
    	FindIterable<Document> result = games.find(filter);
    	
    	//Checking if reviews is unavailable for the game
    	if(result.first() == null)
    	{
    		return true;
    	}
    	
    	return false;
    }
	
	public static boolean noAppScoreReviews(int id) {
    	//Filtering
    	Bson filter = and(eq("_id", id), exists("average_of_reviews"));
    	FindIterable<Document> result = games.find(filter);
    	
    	//Checking if reviews is unavailable for the game
    	if(result.first() == null)
    	{
    		return true;
    	}
    	
    	return false;
    }
	
	public static boolean noGameStats(int id) {
    	//Filtering
    	Bson filter = and(eq("_id", id), exists("player_count"));
    	FindIterable<Document> result = games.find(filter);
    	
    	//Checking if game stats is unavailable for the game
    	if(result.first() == null)
    	{
    		return true;
    	}
    	
    	return false;
    }
    
    
    public static void updateAppDetails(int id) {
    	//Declaring and initializing 
    	String appID = String.valueOf(id);
    	JsonNode jsonResponse;
    	
    	//Grabbing specified game
    	Bson filter = eq("_id", id);
    	FindIterable<Document> result = games.find(filter);
    	Document game = result.first();
    	
    	try {
    		//Calling API
    		HttpRequest request = HttpRequest.newBuilder()
    				.uri(URI.create("https://store.steampowered.com/api/appdetails?appids="+appID+"&I=english"))
    				.method("GET", HttpRequest.BodyPublishers.noBody())
    				.build();

    		//Grabbing JSON response
    		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    		String responseBody = response.body();
    		
    		jsonResponse = map.readTree(responseBody);
    		jsonResponse = jsonResponse.get(appID).get("data");
    		
    		//Updating database
    		Bson update = Updates.combine(updateShortDesc(jsonResponse),
    		updateDesc(jsonResponse),
    		updateCover(jsonResponse),
    		updateSysRequire(jsonResponse),
    		updateMetacritic(jsonResponse),
    		updateAverage(),
    		updateReview(),
    		updateComment(),
    		updateGameStats()
    				);
    		
    		//Updating
			try {
				UpdateResult updateResult = games.updateOne(game, update);
				System.out.println("Updated Game: "+updateResult.wasAcknowledged());
				
			} catch(MongoException e) {
				System.err.println("ERROR: "+e);
			}
    		
    	} catch(InterruptedException e) {
			e.printStackTrace();

		} catch(IOException e) {
			e.printStackTrace();
			
        } catch(NullPointerException e) {
			//When game is not available in Canada :(
			setDefaultParameters(game);
    	}
    }

    public static void removeApp(int id) {
    	Bson filter = eq("_id", id);

    	//Deleting game in database
    	try {
    		DeleteResult deleteResult= GameData.games.deleteOne(filter);
    		System.out.println("Acknowledged: " + deleteResult.wasAcknowledged());

    	} catch(MongoException e) {
    		System.err.println("ERROR: "+e);
    	}
    	
    }
    
    
    public static void addApp(int id) {
    	//Declaring and initializing 
    	String appID = String.valueOf(id);
    	JsonNode jsonResponse;
    	
    	try {
    		//Calling API
    		HttpRequest request = HttpRequest.newBuilder()
    				.uri(URI.create("https://store.steampowered.com/api/appdetails?appids="+appID+"&I=english"))
    				.method("GET", HttpRequest.BodyPublishers.noBody())
    				.build();

    		//Grabbing JSON response
    		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    		String responseBody = response.body();
    		
    		jsonResponse = map.readTree(responseBody);
    		jsonResponse = jsonResponse.get(appID).get("data");
    		
    		//Creating new entry
        	try {
        		InsertOneResult result = games.insertOne(new Document()
                        .append("_id", id)
                        .append("name", jsonResponse.get("name").asText()));
                System.out.println("Added New Game: "+result.wasAcknowledged());
                
        	} catch(MongoException e) {
        		System.err.println("ERROR: "+e);
        	}
        	
        	//Grabbing specified game
        	Bson filter = eq("_id", id);
        	FindIterable<Document> result = games.find(filter);
        	Document game = result.first();
    		
    		//Updating database
    		Bson update = Updates.combine(updateShortDesc(jsonResponse),
    		updateDesc(jsonResponse),
    		updateCover(jsonResponse),
    		updateSysRequire(jsonResponse),
    		updateMetacritic(jsonResponse),
    		updateReview(),
    		updateAverage(),
    		updateComment(),
    		updateGameStats()
    		);
    		
    		//Updating
			try {
				UpdateResult updateResult = games.updateOne(game, update);
				System.out.println("Acknowledged: "+updateResult.wasAcknowledged());
				
			} catch(MongoException e) {
				System.err.println("ERROR: "+e);
			}
    		
    	} catch(InterruptedException e) {
			e.printStackTrace();

		} catch(IOException e) {
			e.printStackTrace();
			
		} catch(NullPointerException e) {
			//When game is not available in Canada
			Document na = new Document().append("_id", id).append("name", "REGION-LOCKED GAME");
			
			try {
				InsertOneResult result = games.insertOne(na);
				System.out.println("Added region-locked Game: "+result.wasAcknowledged());
			} catch (MongoException f) {
				System.err.println("ERROR: "+f);
			}
		} 	 
    }
    
   public static void setReviewsComments(int id) {
	   Bson update = Updates.combine(updateReview(), updateComment());
	   
	   //Grabbing specified game
	   Bson filter = eq("_id", id);
	   FindIterable<Document> result = games.find(filter);
	   Document game = result.first();
	   
	   //Updating
	   try {
		   UpdateResult updateResult = games.updateOne(game, update);
		   System.out.println("Added Only Reviews: "+updateResult.wasAcknowledged());

	   } catch(MongoException e) {
		   System.err.println("ERROR: "+e);
	   }  
   }
   
   public static void setAverage(int id) {
	   Bson update = Updates.combine(updateAverage());
	   
	   //Grabbing specified game
	   Bson filter = eq("_id", id);
	   FindIterable<Document> result = games.find(filter);
	   Document game = result.first();
	   
	   //Updating
	   try {
		   UpdateResult updateResult = games.updateOne(game, update);
		   System.out.println("Added Only Review Average: "+updateResult.wasAcknowledged());

	   } catch(MongoException e) {
		   System.err.println("ERROR: "+e);
	   }  
   }
   
   public static void setGameStats(int id) {
	   Bson update = updateGameStats();
	   
	   //Grabbing specified game
	   Bson filter = eq("_id", id);
	   FindIterable<Document> result = games.find(filter);
	   Document game = result.first();
	   
	   //Updating
	   try {
		   UpdateResult updateResult = games.updateOne(game, update);
		   System.out.println("Initialized Game Stats: "+updateResult.wasAcknowledged());

	   } catch(MongoException e) {
		   System.err.println("ERROR: "+e);
	   }  
   }
   
   public static void updateGameStats(int id) {
	   setPlayerCount(id);
	   setPlayerPeak(id);
   }
   
   private static void setPlayerCount(int id) {
	   String appID = String.valueOf(id);
	   JsonNode jsonResponse;
	   
	   try {
		   //Calling API
		   HttpRequest request = HttpRequest.newBuilder()
				   .uri(URI.create("https://api.steampowered.com/ISteamUserStats/GetNumberOfCurrentPlayers/v1/?appid="+appID))
				   .method("GET", HttpRequest.BodyPublishers.noBody())
				   .build();

		   //Grabbing JSON response
		   HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		   String responseBody = response.body();
		   
		   jsonResponse = map.readTree(responseBody);
		   jsonResponse = jsonResponse.get("response");
		   int players = jsonResponse.get("player_count").asInt();
		   
		   //Grabbing specified game
		   Bson filter = eq("_id", id);
		   FindIterable<Document> result = games.find(filter);
		   Document game = result.first();
		   
		   //Update database
		   Bson update = Updates.set("player_count", players);
		   try {
			   UpdateResult updateResult = games.updateOne(game, update);
			   System.out.println("Updated Only Game Stats: "+updateResult.wasAcknowledged());

		   } catch(MongoException m) {
			   System.err.println("ERROR: "+m);
		   } 
		   
	   } catch(InterruptedException e) {
		   e.printStackTrace();

	   } catch(IOException e) {
		   e.printStackTrace();

	   } catch(NullPointerException e) {
		   //Set default stats
		   Bson update = updateGameStats();
		   
		   //Grabbing specified game
		   Bson filter = eq("_id", id);
		   FindIterable<Document> result = games.find(filter);
		   Document game = result.first();
		   
		   //Updating
		   try {
			   UpdateResult updateResult = games.updateOne(game, update);
			   System.out.println("Updated Player Count: "+updateResult.wasAcknowledged());

		   } catch(MongoException m) {
			   System.err.println("ERROR: "+m);
		   }  
	   } 	 
   }
   
   private static void setPlayerPeak(int id) {
	   Bson filter = eq("_id", id);
	   FindIterable<Document> result = games.find(filter);
	   Document game = result.first();
	   int players = game.getInteger("player_count");
	   int peak = game.getInteger("twenty_four_hr_peak");
	   
	   if(players > peak)
	   {
		   peak = players;
		   
		   Bson update = Updates.set("twenty_four_hr_peak", peak);
		   try {
			   UpdateResult updateResult = games.updateOne(game, update);
			   System.out.println("Updated Player Count: "+updateResult.wasAcknowledged());

		   } catch(MongoException m) {
			   System.err.println("ERROR: "+m);
		   }
	   }
   }
    
    public static String getName(int id) {
    	//Grabbing specified game
    	Bson filter = eq("_id", id);
    	FindIterable<Document> result = games.find(filter);
    	Document game = result.first();
    	
    	return game.getString("name");
    }
    
    public static String getAverageofusers(int id) {
    	//Grabbing specified game
    	Game g = getGame(id);
    	return String.valueOf(g.getAverage_of_reviews()) ;
    }

	
    public static Game getGame(int id) {
    	//Grabbing specified game
    	Bson filter = eq("_id", id);
    	FindIterable<Document> result = games.find(filter);
    	Document game = result.first();
    	String jsonResponse = game.toJson();
    	
    	try {
    		//Returning game object from jsonResponse
			return map.readValue(jsonResponse, Game.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    private static void setDefaultParameters(Document game) {
    	Bson update1 = Updates.set("short_description", "Game is not available in Canada.");
		Bson update2 = Updates.set("description", "Game is not available in Canada");
		Bson update3 = Updates.set("cover_art", "");
		Bson update4 = Updates.set("pc_requirements", "System requirements are not available.");
		Bson update5 = Updates.set("meta_score", "N/A");
		Bson update6 = Updates.set("meta_link", "N/A");
		
		Bson update = Updates.combine(update1, update2, update3, update4, update5, update6, updateReview(), updateComment(), updateGameStats());
		
		//Updating
		try {
			UpdateResult updateResult = games.updateOne(game, update);
			System.out.println("Acknowledged: "+updateResult.wasAcknowledged());
			
		} catch(MongoException f) {
			System.err.println("ERROR: "+f);
		}
    }
    
	private static Bson updateShortDesc(JsonNode jsonResponse) {
		//Grabbing short description
		String txt = jsonResponse.get("short_description").asText();
		
		//Setting update option
		Bson update = Updates.set("short_description", txt);
		
		return update;
	}


	private static Bson updateDesc(JsonNode jsonResponse) {
		//Grabbing description
		String txt = jsonResponse.get("about_the_game").asText();

		//Setting update option
		Bson update = Updates.set("description", txt);

		return update;
	}


	private static Bson updateCover(JsonNode jsonResponse) {
		//Grabbing img link
		String img = jsonResponse.get("header_image").asText();

		//Setting update option
		Bson update = Updates.set("cover_art", img);

		return update;
	}


	private static Bson updateSysRequire(JsonNode jsonResponse) {
		String txt = "N/A";
		
		try {
			txt = jsonResponse.get("pc_requirements").get("minimum").asText();
		} catch(NullPointerException e) {
			//Else, do nothing
		}
		
		try {
			txt = txt+jsonResponse.get("pc_requirements").get("recommended").asText();
		} catch(NullPointerException e) {
			//Else, do nothing
		}
		
		//Setting update option
		Bson update = Updates.set("pc_requirements", txt);

		return update;
		
	}


	private static Bson updateMetacritic(JsonNode jsonResponse) {
		//Default values for score and link
		String score = "N/A";
		String link = "N/A";
		
		try {
			//Adding metacritic reviews if there is
			score = jsonResponse.get("metacritic").get("score").asText();
			link = jsonResponse.get("metacritic").get("url").asText();
		} catch(NullPointerException e) {
			//Else, do nothing
		} 
		
		//Setting update option
		Bson update = Updates.set("meta_score", score);
		Bson update2 = Updates.set("meta_link", link);
		Bson total = Updates.combine(update, update2);

		return total;
		
	}
	
	private static Bson updateReview() {
		
		int UserReview = 0;
		int NumberofReview = 0;
		
		Bson update = Updates.set("num_of_reviews", UserReview);
		Bson update2 = Updates.set("sum_of_all_reviews", NumberofReview);
		Bson total = Updates.combine(update, update2);

		return total;
		
	}
	
	private static Bson updateAverage() {
		
		int Average = 0;
		
		
		Bson update = Updates.set("average_of_reviews", Average);
		
		Bson total = Updates.combine(update);

		return total;
		
	}
	
	private static Bson updateComment() {
		
		List<Object> comments = new ArrayList<>();
		
		Bson update = Updates.set("comments", comments);
		

		return update;
		
	}
	
	private static Bson updateGameStats() {
		int players = 0;
		int peak = 0;
		
		Bson update = Updates.set("player_count", players);
		Bson update2 = Updates.set("twenty_four_hr_peak", peak);
		Bson total = Updates.combine(update, update2);
		
		return total;
		
	}
	public static void updateNewsBlog(Game game) {

			Bson filter_game = eq("_id", game.getID());
			FindIterable<Document> result_game = GameData.games.find(filter_game);
			Document found_game = result_game.first();
			
		    ArrayList<Document> newsBlogs = new ArrayList<>();
		    ArrayList<NewsBlog> newsBlogsClean = new ArrayList<>();
		    boolean validGame = false;
		    
		    //if the game object has no blogs
		    if(game.getNewsBlogs() == null || game.getNewsBlogs().size() == 0) {
		    	
		    	game.setNewsBlogs(newsBlogsClean);
		    }
		    
		    //if the game already has newsblogs 
		    if(!(found_game.containsKey("newsBlogs"))) {
		    
		    try {
		
			//Calling API
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://api.steampowered.com/ISteamNews/GetNewsForApp/v2/?appid=" + game.getID() + "&count=5"))
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
	        
	        //if a game does have news
	        if(jsonNode.get("appnews") != null ) {
	        	
	        validGame = true;
	        	
	        JsonNode newsItems = appnews.get("newsitems");
	       
	        for (JsonNode newsItem : newsItems) {
	        	
	            String title = newsItem.get("title").asText();
	            String url = newsItem.get("url").asText();
	            String contents = newsItem.get("contents").asText();
	            String author = newsItem.get("author").asText();
	            String website = newsItem.get("feedname").asText();
	            String date =  News.getDate(newsItem.get("date").asLong());
	            
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
	            
	            //making a new object 
	            NewsBlog theBlog = new NewsBlog(title, author, url, contents, website, date);
	            
	            //adding it to the game
	            game.addNewsBlogs(theBlog);
	            
	            //adding it to the database
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
	        
	       }
			
		} 
		    
		  catch(InterruptedException e) {
				
			e.printStackTrace();
		} catch(IOException e) {
				
			e.printStackTrace();
		}
		    
		Bson update =  Updates.unset("newsBlogs");
		    
		if(validGame) {
		    	
		   update = Updates.set("newsBlogs", newsBlogs);
		} 
		
		if(found_game.get("description").equals("Game is not available in Canada")) {
			
			 ArrayList<String> na = new ArrayList<>();
			 na.add("N/A");
			 update = Updates .set("newsBlogs", na);
		}
		
		try {
			
			UpdateResult updateResult = GameData.games.updateOne(found_game, update);
			
			System.out.println("Updated newsBlog: "+updateResult.wasAcknowledged());
	
			
		}  catch(MongoException e) {
			
			System.err.println("ERROR: "+e);
		}

		}
		
		
	}
}
