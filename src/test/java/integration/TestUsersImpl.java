package integration;

import database.UsersImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mygaminglist.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestUsersImpl {

    private User user;
    private User friend;
    private static final UsersImpl users = new UsersImpl();

    private static final String TEST_USER = "test.user";
    private static final String TEST_PASSWORD = "test.password";

    private static final String TEST_FRIEND_PRIVATE = "test.friendPrivate";
    private static final String TEST_FRIEND_PUBLIC = "test.friendPublic";
    private static final boolean TEST_IS_PRIVATE = true;



    @BeforeEach
    public void setup() {
        user = new User(TEST_USER, TEST_PASSWORD,TEST_IS_PRIVATE);

        User friendPrivateAccount = new User(TEST_FRIEND_PRIVATE,TEST_PASSWORD,TEST_IS_PRIVATE);
        User friendPublicAccount =  new User(TEST_FRIEND_PUBLIC,TEST_PASSWORD,false);
        // insert a record
        users.createAccount(user);
        users.createAccount(friendPrivateAccount);
        users.createAccount(friendPublicAccount);
    }

    @AfterEach
    public void clean() {
        users.delete(TEST_USER);
        users.delete(TEST_FRIEND_PRIVATE);
        users.delete(TEST_FRIEND_PUBLIC);
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
    public void testAddFriends_allowed() {
        // validate friends is empty
        List<String> friends = users.listFriend(TEST_USER);
        assertTrue(friends.isEmpty());

        // validate friend was added
        users.updateFriend(TEST_USER, TEST_FRIEND_PUBLIC, "add");
        friends = users.listFriend(TEST_USER);
        assertEquals(1, friends.size());
        assertEquals(TEST_FRIEND_PUBLIC, friends.get(0));

        // validate friend was removed
        users.updateFriend(TEST_USER, TEST_FRIEND_PUBLIC, "remove");
        friends = users.listFriend(TEST_USER);
        assertTrue(friends.isEmpty());
    }

    @Test
    public void testAddFriends_notAllowed() {
        assertThrows(IllegalArgumentException.class,
                () -> users.updateFriend(TEST_USER, TEST_FRIEND_PRIVATE, "add"),
                "This user can not be added");
    }
}
