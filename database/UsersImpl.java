package database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import src.User;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class UsersImpl implements Database {

    private final MongoCollection<Document> users;

    private static final String TABLE_NAME = "Users";

    private static final String ID_KEY = "_id";
    private static final String USER_KEY = "username";
    private static final String PWD_KEY = "password";
    private static final String GAMES_KEY = "games";
    private static final String FRIENDS_KEY = "friends";

    public UsersImpl() {
        users = database.getCollection(TABLE_NAME);
    }

    // username -> String
    // password -> String
    public boolean createAccount(User user) {
        // validate username doesn't exist
        User returnedUser = this.get(user.getUsername());
        if (returnedUser != null) {
            throw new IllegalArgumentException("Username already exist, please choose another one");
        }

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

    public void updateFriend(String username, String friendName, String operation) {
        Bson filter = eq(USER_KEY, username);
        Document document = users.find(filter).first();

        Bson update;
        if (operation.equals("add")) {
            update = Updates.addToSet(FRIENDS_KEY, friendName);
        } else {
            update = Updates.pull(FRIENDS_KEY, friendName);
        }

        try {
            UpdateResult result = users.updateOne(document, update);
            System.out.println("Friend " + " was " + operation + " for " + username + ": " + result.wasAcknowledged());
        } catch (Exception exp) {
            System.err.println("Error happened when adding new friend " + exp.getMessage());
        }
    }

    public List<String> listFriend(String username) {
        User returnedUser = this.get(username);
        System.out.println("Friends " + " was listed for " + username);
        return returnedUser.getFriends();
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

    // get only one user's username
    public boolean get(User user) {
        Bson filter = eq(USER_KEY, user.getUsername());
        Document result = users.find(filter).first();
        if (result == null) {
            return false;
        }

        User returnedUser = documentToUser(result);
        return returnedUser.getPassword().equals(user.getPassword());
    }

    // delete record for user
    public boolean delete(String username) {
        Bson filter = eq(USER_KEY, username);

        try {
            users.deleteOne(filter);
            System.out.println("User " + username + " was deleted");
            return true;
        } catch (Exception exp) {
            throw new RuntimeException("Error happened when deleting user account");
        }
    }

    private User documentToUser(Document doc) {
        String username = doc.getString(USER_KEY);
        String password = doc.getString(PWD_KEY);
        List<String> games = doc.getList(GAMES_KEY, String.class);
        List<String> friends = doc.getList(FRIENDS_KEY, String.class);

        return new User(username, password, games, friends);
    }

    private Document userToDocument(User user) {
        return new Document()
                .append(USER_KEY, user.getUsername())
                .append(PWD_KEY, user.getPassword())
                .append(GAMES_KEY, user.getGames())
                .append(FRIENDS_KEY, user.getFriends());
    }
}
