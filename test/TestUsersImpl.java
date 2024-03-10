package test;

import database.UsersImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import src.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUsersImpl {

    private static final UsersImpl users = new UsersImpl();

    private static final String TEST_USER = "test.user";
    private static final String TEST_PASSWORD = "test.password";

    @AfterEach
    public void clean() {
        users.delete(TEST_USER);
    }

    @Test
    public void testInsertAndGet() {
        User user = new User(TEST_USER, TEST_PASSWORD);

        // insert an record
        users.insert(user);

        // get the record via user
        assertTrue(users.get(user));
        // get the record via username
        User returnedUser = users.get(user.getUsername());
        assertEquals(TEST_USER, user.getUsername());
        assertEquals(TEST_PASSWORD, user.getPassword());
    }
}
