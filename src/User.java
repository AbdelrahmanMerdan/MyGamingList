package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private final String username;
    private final String password;

    private List<String> games = new ArrayList<>();
    private List<String> friends = new ArrayList<>();

    public User (final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public User (final String username,
                 final String password,
                 final List<String> games,
                 final List<String> friends) {
        this.username = username;
        this.password = password;
        this.games = games;
        this.friends = friends;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public List<String> getGames() {
        return this.games;
    }

    public List<String> getFriends() {
        return this.friends;
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
