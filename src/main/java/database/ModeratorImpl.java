package database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import mygaminglist.User;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ModeratorImpl extends UsersImpl implements Database {

//    private final MongoCollection<Document> users;
    
    public final static MongoCollection<Document> moderators= database.getCollection("Moderators");

    private static final String TABLE_NAME = "Users";

    private static final String USER_KEY = "username";
    private static final String PWD_KEY = "password";
    private static final String GAMES_KEY = "Games";
    private static final String FRIENDS_KEY = "Friends";


}