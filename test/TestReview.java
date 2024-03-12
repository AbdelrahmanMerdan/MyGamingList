package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import database.GameData;
import database.UsersImpl;

import org.junit.jupiter.api.Test;

import src.Game;
import src.User;

class TestReview {
	
	  private User user;
	    private static final UsersImpl users = new UsersImpl();
	    

	    private static final String TEST_USER = "test.user";
	    private static final String TEST_PASSWORD = "test.password";

	    @BeforeEach
	    public void setup() {
	        user = new User(TEST_USER, TEST_PASSWORD);
	        // insert a record
	        users.createAccount(user);
	        Game game = GameData.getGame(271590);
	        
	    }

	    @AfterEach
	    public void clean() {
	        users.delete(TEST_USER);
	        GameData.updateAppDetails(271590);
	    }
	
}