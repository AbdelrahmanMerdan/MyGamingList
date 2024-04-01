package unit;
import database.UsersImpl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import mygaminglist.User;


import static org.junit.jupiter.api.Assertions.*;

public class TestModerator {

    private static User moderator;
    private static User user;
    private static final UsersImpl users = new UsersImpl();

    private static final String TEST_MODERATOR = "test.moderator";
    private static final String TEST_MODERATOR_PASSWORD = "test.password";
    
    private static final String TEST_USER = "test.user";
    private static final String TEST_PASSWORD = "test.password";

    private static final boolean TEST_IS_PRIVATE = true;



    @BeforeAll
    public static void setup() {
        moderator = new User(TEST_MODERATOR, TEST_MODERATOR_PASSWORD);
        user = new User(TEST_USER, TEST_PASSWORD);
        
        // insert a record
        users.createAccount(moderator);
        users.createAccount(user);

    }

    @AfterAll
    public static void clean() {
    	moderator.unbanUser(TEST_USER);
        users.delete(TEST_MODERATOR);
    	users.delete(TEST_USER);
        
    }
    
    @Test
    @Order(1)
    public void makeModeratorTest() {
    	moderator.setModerator(true);
    	
    	assertEquals(true, moderator.isModerator());
    	
    }
    
    @Test
    @Order(2)
    public void banUserTest() {
    	moderator.banUser(TEST_USER);
    	
    	assertEquals(true, user.isBanned(TEST_USER));
    	
    }
    
    @Test
    @Order(3)
    public void unbanUserTest() {
    	moderator.unbanUser(TEST_USER);
    	
    	assertEquals(false, user.isBanned(TEST_USER));
    	
    }
    
    @Test
    @Order(4)
    public void userBanUserTest() {
    	moderator.setModerator(false);
    	moderator.unbanUser(TEST_USER);
    	Boolean banned = user.isBanned(TEST_USER);
    	
    	assertEquals(false, banned);
    	
    }
    
}