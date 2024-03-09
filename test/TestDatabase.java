package test;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

import database.GameData;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import src.Game;

@TestMethodOrder(OrderAnnotation.class)
class TestDatabase {
	
	private static final int TEST_GAME_ID = 17470;
	
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	class TestGameDetail{
		@BeforeEach
		void deleteAppDetails() {
			//Grabbing specified game
			Bson filter = eq("_id", TEST_GAME_ID);
			FindIterable<Document> result = GameData.games.find(filter);
			Document game = result.first();

			//Delete settings
			Bson update = Updates.unset("description");
			Bson update2 = Updates.unset("number_of_reviews");
			Bson update3 = Updates.unset("user_reviews");
			Bson total = Updates.combine(update, update2, update3);

			//Deleting description
			try {
				UpdateResult updateResult = GameData.games.updateOne(game, total);
				System.out.println("Acknowledged: "+updateResult.wasAcknowledged());

			} catch(MongoException e) {
				System.err.println("ERROR: "+e);
			}
		}

		@Test
		@Order(1)
		void noAppDetails() {
			assertEquals(true, GameData.noAppDetails(TEST_GAME_ID));
		}

		@Test
		@Order(2)
		void noAppReviews() {
			assertEquals(true, GameData.noAppReviews(TEST_GAME_ID));
		}

		@Test
		@Order(3)
		void updateGame() {
			GameData.updateAppDetails(TEST_GAME_ID);
			assertEquals(false, GameData.noAppDetails(TEST_GAME_ID));
			assertEquals(false, GameData.noAppReviews(TEST_GAME_ID));
		}
	}
	
	@Test
	@Order(1)
	void addGame() {
		Bson filter = eq("_id", TEST_GAME_ID);
		
		//Deleting game in database
		try {
			DeleteResult deleteResult= GameData.games.deleteOne(filter);
			System.out.println("Acknowledged: " + deleteResult.wasAcknowledged());

		} catch(MongoException e) {
			System.err.println("ERROR: "+e);
		}
		// confirm game doesn't exist first
		assertTrue(GameData.noAppExists(TEST_GAME_ID));
		// Call DB add game
		GameData.addApp(TEST_GAME_ID);
		// Confirm game exists in DB after adding operation
		assertFalse(GameData.noAppExists(TEST_GAME_ID));
		assertFalse(GameData.noAppDetails(TEST_GAME_ID));
		assertFalse(GameData.noAppReviews(TEST_GAME_ID));
	}
	
	@Test
	@Order(2)
	void getGame() {
			Game game = GameData.getGame(TEST_GAME_ID);
			assertNotNull(game);

			String name = game.getName();
			String desc = game.getDesc();
			String sysReq = game.getSysRequire();
			String metaUrl = game.getMetaURL();
			String metaScore = game.getMetaScore();
			String cover = game.getCover();
			String shortDesc = game.getShortDesc();
			Integer numOfReviews = game.getNumOfReviews();
			Integer userReviews = game.getUserReviews();
			assertNotNull(name);
			assertNotNull(sysReq);
			assertNotNull(metaScore);
			assertNotNull(metaUrl);
			assertNotNull(cover);
			assertNotNull(shortDesc);
			assertNotNull(numOfReviews);
			assertNotNull(userReviews);
			assertNotNull(desc);

			assertEquals("Dead Space (2008)", name);
		}
}
