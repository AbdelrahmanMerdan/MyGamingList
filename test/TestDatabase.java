package test;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

import com.mongodb.client.result.DeleteResult;
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
import com.mongodb.client.result.UpdateResult;

import database.Database;
import src.Game;

@Nested
@TestMethodOrder(OrderAnnotation.class)
class TestDatabase {
	private static final int TEST_GAME_ID = 1693980;

	@BeforeEach
	void deleteAppDetails() {
		//Grabbing specified game
		Bson filter = eq("_id", TEST_GAME_ID);
		FindIterable<Document> result = Database.games.find(filter);
		Document game = result.first();
		Bson update = Updates.unset("description");
		Bson update2 = Updates.unset("number_of_reviews");
		Bson update3 = Updates.unset("user_reviews");
		Bson total = Updates.combine(update, update2, update3);

		//Deleting description
		try {
			UpdateResult updateResult = Database.games.updateOne(game, total);
			System.out.println("Acknowledged: "+updateResult.wasAcknowledged());

		} catch(MongoException e) {
			System.err.println("ERROR: "+e);
		}
	}

	@Test
	@Order(1)
	void noAppDetails() {
		assertTrue(Database.noAppDetails(TEST_GAME_ID));
	}

	@Test
	@Order(2)
	void noAppReviews() {
		assertTrue(Database.noAppReviews(TEST_GAME_ID));
	}

	@Test
	@Order(3)
	void updateGame() {
		Database.updateAppDetails(TEST_GAME_ID);
		assertFalse(Database.noAppDetails(TEST_GAME_ID));
		assertFalse(Database.noAppReviews(TEST_GAME_ID));
	}

	@Test
	@Order(4)
	void addGame() {
		Bson filter = eq("_id", TEST_GAME_ID);
		FindIterable<Document> result = Database.games.find(filter);
		Document game = result.first();

		//Deleting game in database
		try {
			DeleteResult deleteResult= Database.games.deleteOne(filter);
			System.out.println("Acknowledged: " + deleteResult.wasAcknowledged());

		} catch(MongoException e) {
			System.err.println("ERROR: "+e);
		}
		// confirm game doesn't exist first
		assertTrue(Database.noAppExists(TEST_GAME_ID));
		// Call DB add game
		Database.addApp(TEST_GAME_ID);
		// Confirm game exists in DB after adding operation
		assertFalse(Database.noAppExists(TEST_GAME_ID));
		assertFalse(Database.noAppDetails(TEST_GAME_ID));
		assertFalse(Database.noAppReviews(TEST_GAME_ID));
	}

	@Test
	@Order(5)
	void getGame() {
		Game game = Database.getGame(TEST_GAME_ID);
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
		// desc was updated to be null in @BeforeEach
		assertNull(desc);

		assertEquals("Dead Space", name);
	}
	
}
