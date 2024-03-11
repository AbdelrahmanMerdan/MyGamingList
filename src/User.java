package src;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final String username;
    private final String password;

    private final List<String> games;
    private final List<String> friends;

    public User (final String username, final String password) {
        this.username = username;
        this.password = password;
        this.games = new ArrayList<>();
        this.friends = new ArrayList<>();
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
}
