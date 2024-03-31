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

    private static final String TEST_USER_TO_PUBLIC = "test.userPrivateToPublic";
    private static final String TEST_USER_TO_PRIVATE = "test.userPublicToPrivate";

    private static final String TEST_FRIEND_PRIVATE = "test.friendPrivate";
    private static final String TEST_FRIEND_PUBLIC = "test.friendPublic";


    private static final boolean TEST_IS_PRIVATE = true;



    @BeforeEach
    public void setup() {
        user = new User(TEST_USER, TEST_PASSWORD,TEST_IS_PRIVATE);
        User privateToPublicAccount = new User(TEST_USER_TO_PUBLIC,TEST_PASSWORD,TEST_IS_PRIVATE);
        User publicToPrivateAccount =  new User(TEST_USER_TO_PRIVATE,TEST_PASSWORD,false);
        User friendPrivateAccount = new User(TEST_FRIEND_PRIVATE,TEST_PASSWORD,TEST_IS_PRIVATE);
        User friendPublicAccount =  new User(TEST_FRIEND_PUBLIC,TEST_PASSWORD,false);

        // insert a record
        users.createAccount(user);
        users.createAccount(privateToPublicAccount);
        users.createAccount(publicToPrivateAccount);
        users.createAccount(friendPrivateAccount);
        users.createAccount(friendPublicAccount);
    }

    @AfterEach
    public void clean() {
        users.delete(TEST_USER);
        users.delete(TEST_FRIEND_PRIVATE);
        users.delete(TEST_FRIEND_PUBLIC);
        users.delete(TEST_USER_TO_PUBLIC);
        users.delete(TEST_USER_TO_PRIVATE);
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
    public void testChangeAccountToPublic() {
        users.updateAccountPrivacy(TEST_USER_TO_PUBLIC, "Make Account Public");
        List<String> friends = users.listFriend(TEST_USER);
        assertTrue(friends.isEmpty());

        // validate this account could be added as a friend
        users.updateFriend(TEST_USER, TEST_FRIEND_PUBLIC, "add");
        friends = users.listFriend(TEST_USER);
        assertEquals(1, friends.size());
        assertEquals(TEST_FRIEND_PUBLIC, friends.get(0));

    }

    @Test
    public void testChangeAccountToPrivate() {
        users.updateAccountPrivacy(TEST_USER_TO_PRIVATE, "Make Account Private");
        assertThrows(IllegalArgumentException.class,
                () -> users.updateFriend(TEST_USER, TEST_USER_TO_PRIVATE, "add"),
                "This user can not be added");
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
