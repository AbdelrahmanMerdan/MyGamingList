package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;

import database.GameData;
import database.StubGameData;
import database.UsersImpl;

import org.junit.jupiter.api.Test;

import src.Game;
import src.Review;
import src.User;

class TestReview {
	
	  private User user;
	    private static final UsersImpl users = new UsersImpl();
	    

	    private static final String TEST_USER = "test.user";
	    private static final String TEST_PASSWORD = "test.password";
	    Game game = GameData.getGame(17470);

	    @BeforeEach
	    public void setup() {
	        user = new User(TEST_USER, TEST_PASSWORD);
	        // insert a record
	        users.createAccount(user);
	    }

	    @AfterEach
	    public void clean() {
	        users.delete(TEST_USER);
	        GameData.updateAppDetails(17470);
	    }
	    
	    @Test
		@Order(1)
		void AlreadyReviewedTest() {
			assertEquals(false, Review.AlreadyReviewed(game, TEST_USER));
		}

		@Test
		@Order(2)
		void ReviewGame() throws IOException {
			Review.review_game(TEST_USER, game, 9, "Game is Good!", "Yes" );
			assertEquals(true, Review.AlreadyReviewed(game, TEST_USER));
		}

}