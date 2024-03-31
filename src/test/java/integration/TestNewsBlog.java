package integration;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import database.GameData;
import org.junit.jupiter.api.Test;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import mygaminglist.*;

public class TestNewsBlog {
	
	Game heroes =  GameData.getGame(297000);
	
	@BeforeEach
	public void setup() {
		GameData.updateNewsBlog(heroes);
	}
	
	@AfterEach
	public void clean() {
		  
	    Bson filter_game = eq("_id", 297000);
		FindIterable<Document> result_game = GameData.games.find(filter_game);
		Document found_game = result_game.first();
    	
    	Bson update = Updates.unset("newsBlogs");

		try {
			
			UpdateResult updateResult = GameData.games.updateOne(found_game, update);


		}  catch(MongoException e) {
			
			System.err.println("ERROR: "+e);
		}
    }
	
	@Test
	@Order(1)
    
    void addGameBlogs() {
		assertNotNull(heroes.getNewsBlogs());
	}
	
	@Test
	@Order(2)
	
	void checkTitle() {
		assertNotNull(heroes.getNewsBlogs().get(0).getTitle());
		
	}
	
	@Test
	@Order(3)
	void checkUrl() {
		assertNotNull(heroes.getNewsBlogs().get(0).getUrl());
		
	}
	
	@Test
	@Order(4)
	void checkContents() {
		
		assertNotNull(heroes.getNewsBlogs().get(0).getContent());
		
	}
	
	@Test
	@Order(5)
	void checkAuthor() {
		
		assertNotNull(heroes.getNewsBlogs().get(0).getAuthor());
	}
	
	@Test
	@Order(6)
	void checkWebsite() {
		
		assertNotNull(heroes.getNewsBlogs().get(0).getWebsite());
		
	}
	
	@Test
	@Order(7)
	void checkDate() {
		
		assertNotNull(heroes.getNewsBlogs().get(0).getDate());
		
	}
	
	@Test
	@Order(8)
	void checkInvalidGame() {
		
		Game invalid = GameData.getGame(1808781);
		ArrayList<NewsBlog> empty = new ArrayList<>();
		GameData.updateNewsBlog(invalid);
		assertEquals(empty, invalid.getNewsBlogs());
		
	}
    
    
}
