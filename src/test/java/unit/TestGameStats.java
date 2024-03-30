package unit;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;

import com.mongodb.client.FindIterable;

import database.*;

class TestGameStats {

	@Test
	void test() {
		assertFalse(GameData.noGameStats(271590));
	}
	
	@Test
	void test2() {
		Bson filter = eq("_id", 271590);
		FindIterable<Document> result = GameData.games.find(filter);
		Document data = result.first();
		int players = data.getInteger("player_count");
		int peak = data.getInteger("twenty_four_hr_peak");
		
		assertTrue(peak >= players);
	}
}
