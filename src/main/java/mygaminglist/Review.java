package mygaminglist;

import com.mongodb.*;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.*;
import database.GameData;
import database.UsersImpl;
import org.bson.*;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class Review {

	public static boolean UserExists(String name) {
		//Filtering
		Bson filter = eq("username", name);
		FindIterable<Document> result = UsersImpl.users.find(filter);

		//Checking if user exists
		if(result.first() == null)
		{
			return false;
		}

		return true;
	}

	public static boolean noAppReviews(int id) {
		//Filtering
		Bson filter = and(eq("_id", id), exists("num_of_reviews"));
		FindIterable<Document> result = GameData.games.find(filter);

		//Checking if details is unavailable for the game
		if(result.first() == null)
		{
			return false;
		}

		return true;
	}

	public static boolean CheckUpdateGame(int id) {
		Bson filter = and(eq("_id", id), exists("name"));
		FindIterable<Document> result = GameData.games.find(filter);

		//Checking if details is unavailable for the game
		if(result.first() != null)
		{
			if(noAppReviews(id) == false) {
				GameData.updateAppDetails(id);
				return true;
			}
			return false;
		}
		return false;
	}


	public static boolean AlreadyReviewed(Game game, String username){
		for(int i = 0; i < game.getComment().size();i++) {
			@SuppressWarnings("unchecked")
			List<Object> diffreviews =  (List<Object>) game.getComment().get(i);
			String specificuser = (String) diffreviews.get(0);

			if(specificuser.equals(username)) {
				System.out.println("This user already reviewed the game");
				return true;
			}	
		}
		
		return false;
	}


	public static void review_game(String username, Game game, int review, String comment, String reccomendation) 
			throws IOException {

		if(UserExists(username)) {

			//Find User
			Document user_found = find_user(username);


			//Find Game
			Document game_found = find_game(game);


			Bson update = Updates.combine(UpdateNumReview(game),
					UpdateSumOfReviews(game,review),
					UpdateCommentReview(game, username,review,comment,reccomendation)
					);

			Bson userupdate = addUserReview(game,username,review,comment,reccomendation);

			try {
				UpdateResult updateResult = GameData.games.updateOne(game_found, update);
				System.out.println("Review Game updated: "+updateResult.wasAcknowledged());

				try {
					UpdateResult updateResultUser = UsersImpl.users.updateOne(user_found, userupdate);
					System.out.println("Review User updated: "+updateResultUser.wasAcknowledged());

				}catch(MongoException e) {
					System.err.println("ERROR: "+e);
				}

			} catch(MongoException e) {
				System.err.println("ERROR: "+e);
			}
		}
		else {
			System.out.println("This user does not exist");
		}

	}
	
//	Delete Review

	public static boolean DeleteReview(String username, Game game) {
		//Find User
		if(AlreadyReviewed(game, username)) {
			
			User user = UsersImpl.getUser(username);
			DeleteReviewFromUser(user, game);
			DeleteReviewFromGame(username,game);
			
			return true;
		}

		return false;
	}
	
	private static void DeleteReviewFromUser(User user, Game game) {
		
		for(int i = 0; i < user.getGames().size(); i++) {
			
			@SuppressWarnings("unchecked")
			List<Object> review = (List<Object>) user.getGames().get(i);
			
//			System.out.println(game.getName());
			
			if(review.get(1).equals(game.getName())) {
				
				user.getGames().remove(i);
				Bson Update = Updates.set("Games", user.getGames());

			
				//Find User
				Document user_found = find_user(user.getUsername());

				try {
					UpdateResult updateResultUser = UsersImpl.users.updateOne(user_found, Update);
					System.out.println("Comment in User Deleted: "+updateResultUser.wasAcknowledged());

				} catch(MongoException e) {
					System.err.println("ERROR: "+e);
				}

			}
			
			
		}
	}
	
	
	private static void DeleteReviewFromGame(String username, Game game) {
		
		for(int i = 0; i < game.getComment().size(); i++) {
			
			@SuppressWarnings("unchecked")
			List<Object> review = (List<Object>) game.getComment().get(i);
			
			if(review.get(0).equals(username)) {
				
				game.getComment().remove(i);

				Bson UpdateComment = Updates.set("comments", game.getComment());
				
				int reviewscore = (int) review.get(1);
				reviewscore *= -1;
				
				Bson update = Updates.combine(UpdateDeletedNumReview(game),
						UpdateSumOfReviews(game,reviewscore),
						UpdateComment
						);

			
				//Find User
				Document game_found = find_game(game);

				try {
					UpdateResult updateResultUser = GameData.games.updateOne(game_found, update);
					System.out.println("Comment in Game Deleted: "+updateResultUser.wasAcknowledged());

				} catch(MongoException e) {
					System.err.println("ERROR: "+e);
				}

			}
			
			
		}
	}
	
	public static void DeleteAllUserReviews(String username) {
		//Find User
		
			
			User user = UsersImpl.getUser(username);
			
			for(int i = 0; i < user.getGames().size();i++) {
				
				@SuppressWarnings("unchecked")
				int removegameid = (int) ((List<Object>) user.getGames().get(i)).get(0);
				Game removegame = GameData.getGame(removegameid);
				System.out.println(removegame);
				DeleteReview(username,removegame);
			}

	}
	
	

	
	//Update the Game Review
	private static Bson UpdateNumReview(Game game) {

		int prevreviews = game.getNumOfReviews();
		prevreviews++;

		Bson Update = Updates.set("num_of_reviews", prevreviews);
		game.setNumOfReviews(prevreviews);
		return Update;


	}
	
	private static Bson UpdateDeletedNumReview(Game game) {

		int prevreviews = game.getNumOfReviews();
		prevreviews--;

		Bson Update = Updates.set("num_of_reviews", prevreviews);
		game.setNumOfReviews(prevreviews);
		return Update;


	}


	private static Bson UpdateSumOfReviews(Game game, int review) {

		int prevreviews = game.getSumOfAllReviews();
		prevreviews += review;

		Bson Update = Updates.set("sum_of_all_reviews", prevreviews);
		game.setSumOfAllReviews(prevreviews);

		return Update;


	}

	//Add Comment
	private static Bson UpdateCommentReview (Game game, String username, int review, String comment, String reccomendation) {

		List<Object> comment_list = new ArrayList<>();
		List<Object> sub_comment = new ArrayList<>();

		comment_list.add(username);
		comment_list.add(review);
		comment_list.add(reccomendation);
		comment_list.add(comment);
		comment_list.add(sub_comment);

		game.addComment(comment_list);

		Bson Update = Updates.set("comments", game.getComment());

		return Update;

	}


	//Find User
	private static Document find_user(String username) {

		Bson filter_user = eq("username", username);
		FindIterable<Document> result_user = UsersImpl.users.find(filter_user);
		Document user_found = result_user.first();

		return user_found;

	}

	//Find Game
	private static Document find_game(Game game) {

		Bson filter_game = eq("_id", game.getID());
		FindIterable<Document> result_game = GameData.games.find(filter_game);
		Document game_found = result_game.first();

		return game_found;
	}


	//Add Review to User Class
	private static Bson addUserReview(Game game, String username, int review, String comment, String reccomendation) {


		User user = UsersImpl.getUser(username);
		List<Object> newreview= new ArrayList<>();
		newreview.add(game.getID());
		newreview.add(game.getName());
		newreview.add(review);
		newreview.add(reccomendation);
		newreview.add(comment);

		List<Object> allreviews = user.getGames();

		allreviews.add(newreview);

		Bson Update = Updates.set("Games", allreviews);


		return Update;


	}




	@SuppressWarnings("unchecked")
	public static List<Object> getAllComments(Game game){

		List<Object> copy = new ArrayList<>();

		for(int i = 0; i < game.getComment().size(); i++) {

			int j = 0;

			for(j = 0; j < ((List<Object>) ((List<Object>) game.getComment().get(i)).get(4)).size(); j++) {

				if(j % 2 == 1) {

					copy.add(((List<Object>) ((List<Object>) game.getComment().get(i)).get(4)).get(j));

				}

			}


		}

		return copy;
	}


	@SuppressWarnings("unchecked")
	public static List<Object> getAllCommentsForReview(User userWithReview, Game game){

		List<Object> copy = new ArrayList<>();

		int index = retrieveIndex(userWithReview, game);


		for(int j = 0; j < ((List<Object>) ((List<Object>) game.getComment().get(index)).get(4)).size(); j++) {

			copy.add(((List<Object>) ((List<Object>) game.getComment().get(index)).get(4)).get(j));
		}

		return copy;
	}


	@SuppressWarnings("unchecked")
	public static int retrieveIndex(User user, Game game) {

		int index = -1;

		for(int i = 0; i < game.getComment().size(); i++) {

			if(((List<Object>) game.getComment().get(i)).get(0).equals(user.getUsername())) {

				index = i;
				break;

			}
		}

		return index;
	}


	@SuppressWarnings("unchecked")
	public Object getComment(User userWithReccomandation, Game game){

		int index = retrieveIndex(userWithReccomandation, game);
		List<Object> copy = new ArrayList<>();

		for(int j = 0; j < ((List<Object>) ((List<Object>) game.getComment().get(index)).get(4)).size(); j++) {

			if(j % 2 == 1) {

				copy.add(((List<Object>) ((List<Object>) game.getComment().get(index)).get(4)).get(j));

			}
		}

		return copy;
	}

	@SuppressWarnings("unchecked")
	public Object getUserForComment(User userWithReccomandation, Game game){

		int index = retrieveIndex(userWithReccomandation,game);
		List<Object> copy = new ArrayList<>();

		for(int j = 0; j < ((List<Object>) ((List<Object>) game.getComment().get(index)).get(4)).size(); j++) {

			if(j % 2 == 0) {

				copy.add(((List<Object>) ((List<Object>) game.getComment().get(index)).get(4)).get(j));

			}
		}

		return copy;
	}

	@SuppressWarnings("unchecked")
	public static void addCommentToUserReview(User user, String message, User userWithTheReview, Game game) {

		Document game_found = find_game(game);
		List<Object> comments = game.getComment();

		int reccomandationIndex = retrieveIndex(userWithTheReview, game);

		List<Object> toAdd = (List<Object>) ((List<Object>) comments.get(reccomandationIndex)).get(4);

		//the first index will have the username and the seccond will have the message of the comment
		toAdd.add(user.getUsername());
		toAdd.add(message);

		Bson Update = Updates.set("comments", comments);

		System.out.println(comments);

		try {
			UpdateResult updateResult = GameData.games.updateOne(game_found, Update);
			System.out.println("Updated comment: "+updateResult.wasAcknowledged());
		}catch(MongoException e) {
			System.err.println("ERROR: "+e);
		}


	}

	public static void main(String[] args) throws IOException {

		////			Get the Game from the database using it's ID
					
					Game game = GameData.getGame(271590);
					Game game2 = GameData.getGame(1086940);
					Game game3 = GameData.getGame(47780);
					Game game4 = GameData.getGame(1113000);
				
					User user = UsersImpl.getUser("BodoTest");
					
//					review_game("BodoTest", game, 9, "", "yes");
//					review_game("BodoTest", game2, 9, "", "yes");
//					review_game("BodoTest", game3, 9, "", "yes");
//					review_game("BodoTest", game4, 9, "", "yes");
				
					DeleteAllUserReviews(user.getUsername());
//		


	}
}
