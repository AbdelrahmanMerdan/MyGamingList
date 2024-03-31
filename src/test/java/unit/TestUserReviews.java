package unit;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import database.*;
import mygaminglist.*;

class TestUserReviews {

	private static User user;
	
	private static final UsersImpl users = new UsersImpl();
	
	private static final String TEST_USER = "test.user";
	
    private static final String TEST_PASSWORD = "test.password";
    
    Game game = StubGameData.getGame(17470);
    
    @BeforeAll
    public static void setup() {
        user = new User(TEST_USER, TEST_PASSWORD);
        users.createAccount(user);
    }
    
    @AfterAll
    public static void clean() {
        users.delete(TEST_USER);
    }
    
    
	@Test
	@Order(1)
	void reviewGame() throws IOException {
		Review.review_game(TEST_USER, game, 9, "Game is Good!", "Yes" );
		assertTrue(Review.AlreadyReviewed(game, TEST_USER));
	}
	
	@Test
	@Order(2)
	void DeleteGame() throws IOException {
		Review.DeleteReview(TEST_USER, game);
		assertFalse(Review.AlreadyReviewed(game, TEST_USER));
	}
	
	

}
