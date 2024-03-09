package test;

import database.UsersImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        users.insert(new User(TEST_USER, TEST_PASSWORD));

        User user = users.get(TEST_USER);
        assertEquals(TEST_USER, user.getUsername());
        assertEquals(TEST_PASSWORD, user.getPassword());
    }
}
