package integration;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;

import database.GameData;
import database.UsersImpl;

import org.junit.jupiter.api.Test;

import mygaminglist.*;

class TestReview {
	
	  	private User user;
	  	private User user2;
	    private static final UsersImpl users = new UsersImpl();
	    

	    private static final String TEST_USER = "test.user";
	    private static final String TEST_PASSWORD = "test.password";
	    
	    private static final String TEST_USER2 = "test.user2";
	    private static final String TEST_PASSWORD2 = "test.password2";
	    
	    Game game = GameData.getGame(17470);

	    @BeforeEach
	    public void setup() {
	        user = new User(TEST_USER, TEST_PASSWORD);
	        user2 = new User(TEST_USER2, TEST_PASSWORD2);
	        // insert a record
	        users.createAccount(user);
	        users.createAccount(user2);
	    }

	    @AfterEach
	    public void clean() {
	    	Review.DeleteReview(TEST_USER, game);
	    	Review.DeleteReview(TEST_USER2, game);
	        users.delete(TEST_USER);
	        users.delete(TEST_USER2);
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

		@Test
		@Order(3)
		void CheckForNoCommentsForAGame(){
			//Should have no comments 
			List<Object> empty = new ArrayList<>();
			assertEquals(empty, Review.getAllComments(game));
		}
		
		@Test
		@Order(4)
		void CheckForACommentsForAGame() throws IOException{
			
			Review.review_game(TEST_USER, game, 9, "Game is Good!", "Yes" );
			User newUser = new User("Bob", "123");
			Review.addCommentToUserReview(newUser, "I don't agree!", user, game);
			assertNotNull(Review.getAllComments(game));
		}


		@Test
		@Order(5)
		void CheckForGettingAllUsersAndCommentsForRecommandation() throws IOException{
			
			//No need to check the other get methods for only getting the comments or users for one recommendation, or the whole game, since this is a base for the rest
			
			Review.review_game(TEST_USER, game, 9, "Game is Good!", "Yes" );
			
			User newUser = new User("Bob", "123");
			User newUser1 = new User("Jane", "abc");
			
			Review.addCommentToUserReview(newUser, "I don't agree!", user, game);
			Review.addCommentToUserReview(newUser1, "Hard to believe!", user, game);
			
			List<Object> theComments = new ArrayList<Object>();
			
			String user1 = "Bob";
			String user2 = "Jane";
			String message1 = "I don't agree!";
			String message2 = "Hard to believe!";
			
			theComments.add(user1);
			theComments.add(message1);
			theComments.add(user2);
			theComments.add(message2);
			
			assertEquals(theComments, Review.getAllCommentsForReview(user, game));
		}
		
		@Test
		@Order(6)
		void CheckReviewAverage() throws IOException{
			Review.review_game(TEST_USER, game, 9, "Game is Good!", "Yes" );
			Review.review_game(TEST_USER2, game, 1, "Game is Good!", "Yes" );
			
			System.out.println("This is the test:   " + game.getSumOfAllReviews());
			assertEquals(5, game.getAverage_of_reviews());
			
		}
		
		@Test
		@Order(7)
		void CheckReviewAverageAfterDelete() throws IOException{
			Review.review_game(TEST_USER, game, 9, "Game is Good!", "Yes" );
			Review.review_game(TEST_USER2, game, 1, "Game is BAD!", "No" );
			
			Review.DeleteReview(TEST_USER2, game);
			
			assertEquals(9, game.getAverage_of_reviews());
			
		}
		

}
