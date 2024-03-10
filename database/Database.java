package database;

import com.mongodb.*;
import com.mongodb.client.*;

public interface Database {
	//Client settings for real database
	static String connection = "mongodb+srv://2311team5:qn431J86d7xMEdpc@mygaminglist.igshqok.mongodb.net/?retryWrites=true&w=majority&appName=MyGamingList";

	final static ServerApi serverApi = ServerApi.builder()
			.version(ServerApiVersion.V1)
			.build();

	final static MongoClientSettings settings = MongoClientSettings.builder()
			.applyConnectionString(new ConnectionString(connection))
			.serverApi(serverApi)
			.build();

	final static MongoClient client = MongoClients.create(settings);

	
	final static MongoDatabase database = client.getDatabase("MyGamingList");
}
