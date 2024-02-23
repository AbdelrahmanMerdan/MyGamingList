
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.bson.conversions.Bson;


public class MongoClientConnection {
	static String connectionString = "mongodb+srv://2311team5:qn431J86d7xMEdpc@mygaminglist.igshqok.mongodb.net/?retryWrites=true&w=majority&appName=MyGamingList";
	

    static ServerApi serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build();

    static MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .serverApi(serverApi)
            .build();
	
	public static void main(String[] args) {
       


        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("MyGamingList");
                MongoCollection<Document> collection = database.getCollection("Games");
   
                
//                finditerable.filter(null);
//                finditerable.find(exists("description", false));

                
//                Bson equalComparison = eq("name", "Dead Space");
//                collection.find(equalComparison).forEach(doc -> System.out.println(doc.toJson()));
                
                Bson filter = and(exists("name"), eq("name", "Dead Space"));
                
                if(filter.equals(null)) {
                	System.out.print("its null");
                }
                
                FindIterable<Document> finditerable = collection.find(filter);
                System.out.println(finditerable.first().toJson());
       

                
//                finditerable.forEach(doc -> System.out.println(doc.toJson()));
                
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
            } catch (MongoException e) {
                e.printStackTrace();
            }
            
            
        }
        
        GetGame("Dead Space");
    }
    
    public static void GetGame(String name) {
    	MongoClient mongoClient = MongoClients.create(settings);
    	MongoDatabase database = mongoClient.getDatabase("MyGamingList");
        MongoCollection<Document> collection = database.getCollection("Games");
        
        Bson filter = and(exists("description"), eq("name", name));
        
        FindIterable<Document> finditerable = collection.find(filter);
        
        if(finditerable.first() == null) {
//        	Call API
        	System.out.print("its null");
        }
        else {
//        	Call MongoDB
        	System.out.print("not null");
        }
        
    }
}
