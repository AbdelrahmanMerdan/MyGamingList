package integration;

import static org.junit.jupiter.api.Assertions.*;

import database.GameData;
import org.junit.jupiter.api.Test;

import mygaminglist.Game;

class TestGame {

	//Example ids
	int [] gameIds = {1086940, 271590, 1049590, 739630};

	//Tests cases for initializing
	@Test
	void initGame() {
		Game firstGame = GameData.getGame(gameIds[0]);
		assertEquals("Baldur's Gate 3", firstGame.getName());
	}
	@Test
	void initGame2() {
		Game secondGame = GameData.getGame(gameIds[1]);
		assertEquals("Grand Theft Auto V", secondGame.getName());
	}
	@Test
	void initGame3() {
		Game thirdGame = GameData.getGame(gameIds[2]);
		assertEquals("Eternal Return", thirdGame.getName());
	}

	@Test
	void initGame4() {
		Game fourthGame = GameData.getGame(gameIds[3]);
		assertEquals("Phasmophobia", fourthGame.getName());
	}
	
	
	//Test cases for critic score
	@Test
	void setCriticRev1Score() {

		Game firstGame = GameData.getGame(gameIds[0]);
		assertEquals("96", firstGame.getMetaScore());

	}

	@Test
	void setCriticRev2Score() {

		Game secondGame = GameData.getGame(gameIds[1]);
		assertEquals("96", secondGame.getMetaScore());

	}

	@Test
	void setCriticRev3Score() {

		Game thirdGame = GameData.getGame(gameIds[2]);
		assertEquals("N/A", thirdGame.getMetaScore());

	}

	@Test
	void setCriticRev4Score() {

		Game fourthGame = GameData.getGame(gameIds[3]);
		assertEquals("N/A", fourthGame.getMetaScore());

	}

	@Test
	void setCriticRev1url() {

		Game firstGame = GameData.getGame(gameIds[0]);
		assertEquals("https://www.metacritic.com/game/pc/baldurs-gate-3?ftag=MCD-06-10aaa1f", firstGame.getMetaURL());

	}

	@Test
	void setCriticRev2url() {

		Game secondGame = GameData.getGame(gameIds[1]);
		assertEquals("https://www.metacritic.com/game/pc/grand-theft-auto-v?ftag=MCD-06-10aaa1f", secondGame.getMetaURL());

	}

	@Test
	void setCriticRev3url() {

		Game thirdGame = GameData.getGame(gameIds[2]);
		assertEquals("N/A", thirdGame.getMetaURL());

	}

	@Test
	void setCriticRev4url() {

		Game fourthGame = GameData.getGame(gameIds[3]);
		assertEquals("N/A", fourthGame.getMetaURL());

	}

	//Tests cases for description
	@Test
	void setDescription1() {
		Game firstGame = GameData.getGame(gameIds[0]);
		assertTrue(firstGame.getDesc().startsWith("<img src=\"https://cdn.akamai.steamstatic.com/steam/apps/1086940/"));
	}
	@Test
	void setDescription2() {
		Game secondGame = GameData.getGame(gameIds[1]);
		assertTrue(secondGame.getDesc().startsWith("When a young street hustler,"));
	}
	@Test
	void setDescription3() {
		Game thirdGame = GameData.getGame(gameIds[2]);
		assertTrue(thirdGame.getDesc().startsWith("<img src=\"https://cdn.akamai.steamstatic.com/steam/apps/1049590/"));
	}

	@Test
	void setDescription4() {
		Game fourthGame = GameData.getGame(gameIds[3]);
		assertTrue(fourthGame.getDesc().startsWith("Phasmophobia is a 4-player"));
	}




}
