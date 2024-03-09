package database;

import com.mongodb.*;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.*;
import org.bson.*;
import org.bson.conversions.Bson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import src.Game;

import java.net.*;
import java.net.http.*;
import java.io.IOException;


public class Database {
	//Client settings
	final static String connection = "mongodb+srv://2311team5:qn431J86d7xMEdpc@mygaminglist.igshqok.mongodb.net/?retryWrites=true&w=majority&appName=MyGamingList";

	final static ServerApi serverApi = ServerApi.builder()
			.version(ServerApiVersion.V1)
			.build();

	final static MongoClientSettings settings = MongoClientSettings.builder()
			.applyConnectionString(new ConnectionString(connection))
			.serverApi(serverApi)
			.build();

	final static MongoClient client = MongoClients.create(settings);

	//Database
	final static MongoDatabase database = client.getDatabase("MyGamingList");
}
