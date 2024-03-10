package database;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import src.User;

import static com.mongodb.client.model.Filters.eq;

public class UsersImpl implements Database {

    private final MongoCollection<Document> users;

    private static final String TABLE_NAME = "Users";

    private static final String USER_KEY = "username";
    private static final String PWD_KEY = "password";

    public UsersImpl() {
        users = database.getCollection(TABLE_NAME);
    }

    // username -> String
    // password -> String -> length between 4-16
    public boolean insert(User user) {
        // validation
        validateUsername(user.getUsername());
        validatePassword(user.getPassword());

        // user -> doc
        Document document = userToDocument(user);

        // insert
        try {
            users.insertOne(document);
            System.out.println("Added new user " + user.getUsername());
            return true;
        } catch (Exception exp) {
            System.err.println("Error happened when adding new user " + exp.getMessage());
            return false;
        }
    }

    // get only one user's username
    public User get(String username) {
        Bson filter = eq(USER_KEY, username);
        Document result = users.find(filter).first();

        if (result == null) {
            return null;
        }

        return documentToUser(result);
    }

    // delete record for user
    public boolean delete(String username) {
        Bson filter = eq(USER_KEY, username);

        try {
            users.deleteOne(filter);
            return true;
        } catch (Exception exp) {
            throw new RuntimeException("Error happened when deleting user account");
        }
    }

    private User documentToUser(Document doc) {
        String username = doc.getString(USER_KEY);
        String password = doc.getString(PWD_KEY);

        return new User(username, password);
    }

    private Document userToDocument(User user) {
        return new Document().append(USER_KEY, user.getUsername()).append(PWD_KEY, user.getPassword());
    }

    private void validatePassword(String password) {
        if (password.length() < 4 || password.length() > 16) {
            throw new IllegalArgumentException("Password doesn't meet requirement: 1) length between 4-16");
        }
    }

    private void validateUsername(String username) {
        User user = this.get(username);

        if (user != null) {
            throw new IllegalArgumentException("Username already exist, please choose another one");
        }
    }
}
