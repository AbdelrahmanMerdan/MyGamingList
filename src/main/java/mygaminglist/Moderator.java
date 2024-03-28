//<<<<<<< HEAD
//=======
//
//>>>>>>> branch 'Gradle' of https://github.com/AbdelrahmanMerdan/MyGamingList
package mygaminglist;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bson.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Moderator extends User {
	
	private List<String> banlist;
	
	public Moderator(@JsonProperty("_id") Object id, @JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("Games") List<Object> games, @JsonProperty("Friends") List<String> friends, @JsonProperty("BannedUsers") List<String> bannedusers )
	  {
		super(id, username, password, games, friends);
		this.setBanlist(bannedusers);
		// TODO Auto-generated constructor stub
	}
	
	public Moderator(String username, String password)
	 {
		super(username, password);
	    this.setBanlist(new ArrayList<>());
		}

	public List<String> getBanlist() {
		return banlist;
	}

	public void setBanlist(List<String> banlist) {
		this.banlist = banlist;
	}
	
	public boolean banUser(String username) {
		return false;
		
	}
	
	public boolean unbanUser(String username) {
		return false;
	}
	
}

	
