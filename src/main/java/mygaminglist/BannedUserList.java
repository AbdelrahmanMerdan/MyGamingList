
package mygaminglist;

import database.GameData;
import java.util.*;
import java.io.IOException;


public class BannedUserList{
	
	static ArrayList<String> usernames = new ArrayList<>();

	public BannedUserList() {
		

		
		 User.bannedusers.find().forEach(user -> {
		        // Assuming `map` is an ObjectMapper instance
		        try {
		            BannedUsers banned= GameData.map.readValue(user.toJson(), BannedUsers.class);
		            
		            usernames.add(banned.getUsername());
		           
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    });
	}
	
	public ArrayList<String> getBannesdUsers() {
		return usernames;
	}
	
	public String getuser(int i) {
		return usernames.get(i);
	}
	
	public int getSize() {
		return usernames.size();
	}
	
}
