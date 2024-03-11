package test;

import database.UsersImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUsersImpl {

    private User user;
    private static final UsersImpl users = new UsersImpl();

    private static final String TEST_USER = "test.user";
    private static final String TEST_PASSWORD = "test.password";

    private static final String TEST_FRIEND = "test.friend";

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

    @Test
    public void testLogin() {
        // get the record via user
        users.login(user);
        // get the record via username
        User returnedUser = users.get(user.getUsername());
        assertEquals(TEST_USER, user.getUsername());
        assertEquals(TEST_PASSWORD, user.getPassword());
    }

    @Test
    public void testUpdateFriends() {
        // validate friends is empty
        List<String> friends = users.listFriend(TEST_USER);
        assertTrue(friends.isEmpty());

        // validate friend was added
        users.updateFriend(TEST_USER, TEST_FRIEND, "add");
        friends = users.listFriend(TEST_USER);
        assertEquals(1, friends.size());
        assertEquals(TEST_FRIEND, friends.get(0));

        // validate friend was removed
        users.updateFriend(TEST_USER, TEST_FRIEND, "remove");
        friends = users.listFriend(TEST_USER);
        assertTrue(friends.isEmpty());
    }
}
