package test;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
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


class TestDatabase {
	
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	class TestGameDetail{
		@BeforeEach
		void deleteAppDetails() {
			//Grabbing specified game
			Bson filter = eq("_id", 1693980);
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
			assertEquals(true, Database.noAppDetails(1693980));
		}

		@Test
		@Order(2)
		void noAppReviews() {
			assertEquals(true, Database.noAppReviews(1693980));
		}

		@Test
		@Order(3)
		void updateGame() {
			Database.updateAppDetails(1693980);
			assertEquals(false, Database.noAppDetails(1693980));
			assertEquals(false, Database.noAppReviews(1693980));
		}
	}
	
	
	
}
