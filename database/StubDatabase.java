package database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public interface StubDatabase {
	//Client settings for real database
	static String connection = "mongodb+srv://2311team5:qn431J86d7xMEdpc@gaminglist.a2jzcoi.mongodb.net/?retryWrites=true&w=majority&appName=GamingList";

	final static ServerApi serverApi = ServerApi.builder()
			.version(ServerApiVersion.V1)
			.build();

	final static MongoClientSettings settings = MongoClientSettings.builder()
			.applyConnectionString(new ConnectionString(connection))
			.serverApi(serverApi)
			.build();

	final static MongoClient client = MongoClients.create(settings);


	final static MongoDatabase stubDatabase = client.getDatabase("GamingList");
}
