package integration;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import database.GameData;
import database.UsersImpl;
import mygaminglist.Game;
import mygaminglist.Review;
import mygaminglist.User;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestMyReviews {

	private static User user;
	
	private static final UsersImpl users = new UsersImpl();
	
	private static final String TEST_USER = "test.user";
	
    private static final String TEST_PASSWORD = "test.password";
    
    public static final Game game = GameData.getGame(17470);
    
    @BeforeAll
    public static void setup() throws IOException {
        user = new User(TEST_USER, TEST_PASSWORD);
        users.createAccount(user);
        
        Review.review_game(TEST_USER, game, 9, "Game is Good!", "Yes" );
        
        User newUser = new User("Bob", "123");
		Review.addCommentToUserReview(newUser, "I don't agree!", user, game);
    }
    
    @AfterAll
    public static void clean() {
    	Review.DeleteReview(TEST_USER, game);
        users.delete(TEST_USER);
    }
    
	@Test
	@Order(1)
	void getReviews() {
		user = UsersImpl.getUser(TEST_USER);
		List<Object> reviews = user.getGames();
		
		assertEquals(1, reviews.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Order(2)
	void getCommentsForReviewWithoutGivenGameObj() {
		user = UsersImpl.getUser(TEST_USER);
		List<Object> reviews = user.getGames();
		List<Object> review = (List<Object>) reviews.get(0);
		Game game = GameData.getGame((int) review.get(0));
		List<Object> comments = Review.getAllCommentsForReview(user, game);
		
		assertEquals(2, comments.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Order(3)
	void deleteReviewViaMyReviews() {
		List<Object> reviews = user.getGames();
		List<Object> review = (List<Object>) reviews.get(0);
		Game game = GameData.getGame((int) review.get(0));
		
		Review.DeleteReview(TEST_USER, game);
		
		assertFalse(Review.AlreadyReviewed(game, TEST_USER));
	}
}
