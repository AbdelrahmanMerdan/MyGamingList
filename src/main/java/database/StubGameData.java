package database;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;

import mygaminglist.Game;
import mygaminglist.News;

public class StubGameData extends GameData {
	
	public static void changeConnection() {
		games = stubDatabase.getCollection("Games");
	}
	
	public static boolean noAppExists(int id) {
        changeConnection();
        
        return GameData.noAppExists(id);
        
    }

    public static boolean noAppDetails(int id) {
    	changeConnection();
        
        return GameData.noAppDetails(id);
    }

    public static boolean noAppReviews(int id) {
    	changeConnection();
    	
    	return GameData.noAppReviews(id);
    	
    }
        
    public static void updateAppDetails(int id) {
    	changeConnection();
    	
    	GameData.updateAppDetails(id);

    }
    
    public static boolean noGameStats(int id) {
    	changeConnection();
    	
    	return GameData.noGameStats(id);
    }

    public static void removeApp(int id) {
    	changeConnection();
    	
    	GameData.removeApp(id);
    }
    
    public static void addApp(int id) {
    	changeConnection();
    	
    	GameData.addApp(id);

    }
    
    public static String getName(int id) {
    	changeConnection();
    	
    	return GameData.getName(id);
    }

    public static Game getGame(int id) {
       changeConnection();
       
       return GameData.getGame(id);
    }
    
    public static void updateGameStats(int id) {
 	   changeConnection();
 	   
 	   GameData.updateGameStats(id);
    }

   public static void updateGameNews(int id) {
  	   changeConnection();
  	   
  	   News.updateNewsBlog(id);
     }
	
}
