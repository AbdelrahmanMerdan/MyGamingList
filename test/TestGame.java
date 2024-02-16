import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class TestGame {
	
	//Example ids
    String [] gameIds = new String[] {"1086940", "271590", "1049590", "739630"};

	@Test
	void setCriticRev1Score() {
		
		Game firstGame = new Game(gameIds[0]);
		assertEquals("96", firstGame.getCriticScore());
		
	}
	
	@Test
	void setCriticRev2Score() {
		
		Game secondGame = new Game(gameIds[1]);
		assertEquals("96", secondGame.getCriticScore());
		
	}
	
	@Test
	void setCriticRev3Score() {
		
		Game thirdGame = new Game(gameIds[2]);
		assertEquals("N/A", thirdGame.getCriticScore());
		
	}
	
	@Test
	void setCriticRev4Score() {
		
		Game fourthGame = new Game(gameIds[3]);
		assertEquals("N/A", fourthGame.getCriticScore());
		
	}
	
	@Test
	void setCriticRev1url() {
		
		Game firstGame = new Game(gameIds[0]);
		assertEquals("https://www.metacritic.com/game/pc/baldurs-gate-3?ftag=MCD-06-10aaa1f", firstGame.getCriticURL());
		
	}
	
	@Test
	void setCriticRev2url() {
		
		Game secondGame = new Game(gameIds[1]);
		assertEquals("https://www.metacritic.com/game/pc/grand-theft-auto-v?ftag=MCD-06-10aaa1f", secondGame.getCriticURL());
		
	}
	
	@Test
	void setCriticRev3url() {
		
		Game thirdGame = new Game(gameIds[2]);
		assertEquals("N/A", thirdGame.getCriticURL());
		
	}
	
	@Test
	void setCriticRev4url() {
		
		Game fourthGame = new Game(gameIds[3]);
		assertEquals("N/A", fourthGame.getCriticURL());
		
	}
	
	//Other test cases here
	

}
