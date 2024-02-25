package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import database.Database;
import src.Game;

class TestGame {

	//Example ids
	int [] gameIds = {1086940, 271590, 1049590, 739630};

	//Tests cases for initializing
	@Test
	void initGame() {
		Game firstGame = Database.getGame(gameIds[0]);
		assertEquals("Baldur's Gate 3", firstGame.getName());
	}
	@Test
	void initGame2() {
		Game secondGame = Database.getGame(gameIds[1]);
		assertEquals("Grand Theft Auto V", secondGame.getName());
	}
	@Test
	void initGame3() {
		Game thirdGame = Database.getGame(gameIds[2]);
		assertEquals("Eternal Return", thirdGame.getName());
	}

	@Test
	void initGame4() {
		Game fourthGame = Database.getGame(gameIds[3]);
		assertEquals("Phasmophobia", fourthGame.getName());
	}
	
	
	//Test cases for critic score
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

	//Tests cases for description
	@Test
	void setDescription1() {
		Game firstGame = new Game(gameIds[0]);
		assertTrue(firstGame.getDescription().startsWith("<img src=\"https://cdn.akamai.steamstatic.com/steam/apps/1086940/"));
	}
	@Test
	void setDescription2() {
		Game secondGame = new Game(gameIds[1]);
		assertTrue(secondGame.getDescription().startsWith("When a young street hustler,"));
	}
	@Test
	void setDescription3() {
		Game thirdGame = new Game(gameIds[2]);
		assertTrue(thirdGame.getDescription().startsWith("<img src=\"https://cdn.akamai.steamstatic.com/steam/apps/1049590/"));
	}

	@Test
	void setDescription4() {
		Game fourthGame = new Game(gameIds[3]);
		assertTrue(fourthGame.getDescription().startsWith("Phasmophobia is a 4-player"));
	}




}
