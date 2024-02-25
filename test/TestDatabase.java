package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import database.Database;
import src.Game;

class TestDatabase {

	//Example ids
	int [] gameIds = {1086940, 271590, 1049590, 739630};

	//Tests cases for initializing
	@Test
	void noAppExists() {
		assertEquals(false, Database.noAppExists(gameIds[0]));
	}
	
	@Test
	void noAppDetails() {
		assertEquals(false, Database.noAppDetails(gameIds[0]));
	}
	
	@Test
	void noAppReviews() {
		assertEquals(false, Database.noAppReviews(gameIds[0]));
	}

	
}
