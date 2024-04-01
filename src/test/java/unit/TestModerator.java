import database.UsersImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mygaminglist.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestModerator {

    private User moderator;
    private User user;
    private static final UsersImpl users = new UsersImpl();

    private static final String TEST_MODERATOR = "test.user";
    private static final String TEST_MODERATOR_PASSWORD = "test.password";
    
    private static final String TEST_USER = "test.user";
    private static final String TEST_PASSWORD = "test.password";

    private static final boolean TEST_IS_PRIVATE = true;



    @BeforeEach
    public void setup() {
        user = new User(TEST_USER, TEST_PASSWORD);
        
        // insert a record
        users.createAccount(user);

    }

    @AfterEach
    public void clean() {
        users.delete(TEST_USER);
        
    }
}