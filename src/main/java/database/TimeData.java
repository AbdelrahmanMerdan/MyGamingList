package database;

import static com.mongodb.client.model.Filters.eq;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

public class TimeData implements Database {

	public final static MongoCollection<Document> time = database.getCollection("Time");
	
	private final static SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss");
	
	public static boolean isNextDay() {
		Bson filter = eq("_id", 0);
		FindIterable<Document> result = time.find(filter);
		Document data = result.first();
		long nextTime = (long) data.get("next_day");
		
		if(System.currentTimeMillis() >= nextTime)
		{
			return true;
		}
		
		return false;
		
	}
	
	public static void updateTime() {
		try {
			updateEndTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private static void resetLaunchTime() {
		Bson filter = eq("_id", 0);
		FindIterable<Document> result = time.find(filter);
		Document data = result.first();
		long endPeriod = (long) data.get("next_day");
		
		String newTime = df.format(new Date(endPeriod));
		
		Bson update = Updates.set("initial_launch", newTime);
		
		//Updating
		try {
			UpdateResult updateResult = time.updateOne(data, update);
			System.out.println("Initialized Launch Time: "+updateResult.wasAcknowledged());
		} catch(MongoException e) {
			System.err.println("ERROR: "+e);
		} 
	}
	
	private static void updateEndTime() throws ParseException {
		resetLaunchTime();
		
		Bson filter = eq("_id", 0);
		FindIterable<Document> result = time.find(filter);
		Document data = result.first();
		String initialLaunch = data.getString("initial_launch");

		//Calculates the next day
		Calendar cal = Calendar.getInstance();
		cal.setTime(df.parse(initialLaunch)); 
		cal.add(Calendar.DAY_OF_MONTH, 1);

		Bson update = Updates.set("next_day", cal.getTimeInMillis());

		//Updating
		try {
			UpdateResult updateResult = time.updateOne(data, update);
			System.out.println("Initialized End Time: "+updateResult.wasAcknowledged());
		} catch(MongoException e) {
			System.err.println("ERROR: "+e);
		} 
	}
	
}
