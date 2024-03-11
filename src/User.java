package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bson.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	//Instance variables
	private Object id;
	private String username, password;
	private List<Object> games;
	private List<Object> friends;

	@JsonCreator
	public User(@JsonProperty("_id") Object id, @JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("Games") List<Object> games, @JsonProperty("Friends") List<Object> friends )
			 {
		this.id = id;
		this.username = username;
		this.password = password;
		this.games = games;
		this.friends = friends;
	}

	public User(String username, String password)
	 {
		this.id = 0;
		this.username = username;
		this.password = password;
		this.games = new ArrayList<>();
	    this.friends = new ArrayList<>();
		}
	


	public Object getId() {
		return id;
	}



	public void setId(Object id) {
		this.id = id;
	}



	public String getUsername() {
		return username;
	}



	public void setName(String name) {
		this.username = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	
	public void setPassword(String password2) {
		this.password = password2;
		
	}
	
	public List<Object> getFriends() {
		return friends;
	}
	
	public List<Object> getGames() {
		return games;
	}
	

	@Override
	public String toString() {
		return " userame=" + username + "\n id=" + id + "\n password=" + password + "\n Games=" + games + "\n friends=" + friends;
				
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(username, other.username);
	}
}
