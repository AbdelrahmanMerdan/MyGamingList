package mygaminglist;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BannedUsers {
	
	private Object id;
	private String username;
	

	@JsonCreator
	public BannedUsers(@JsonProperty("_id") Object id, @JsonProperty("username") String username){
		this.id = id;
		this.setUsername(username);
		
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

}
