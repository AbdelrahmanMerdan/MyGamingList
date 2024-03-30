package unit;


import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mongodb.client.FindIterable;
import database.*;

class TestTimeData {

	@BeforeEach
	void updateTime() {
		if(TimeData.isNextDay())
		{
			TimeData.updateTime();
		}
	}

	@Test
	void test1() {
		assertFalse(TimeData.isNextDay());
	}
	
	@Test
	void test2() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss");
		
		Bson filter = eq("_id", 0);
		FindIterable<Document> result = TimeData.time.find(filter);
		Document data = result.first();
		String initialDate = data.getString("initial_launch");
		long nextDay = (long) data.get("next_day");
		
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(df.parse(initialDate)); 
			cal.add(Calendar.DAY_OF_MONTH, 1);	
			
			assertEquals(cal.getTimeInMillis(), nextDay);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
