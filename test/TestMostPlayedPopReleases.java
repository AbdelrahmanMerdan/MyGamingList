package test;

import src.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestMostPlayedPopReleases {

	@Test
	void test() {
		MostPlayed mostPlayed = new MostPlayed();
		
		for(Game game : mostPlayed.getGames())
		{
			assertNotNull(game);
		}
	}
	
	@Test
	void test2() {
		PopReleases popReleases= new PopReleases();
		
		for(Game game : popReleases.getGames())
		{
			assertNotNull(game);
		}
		
	}

}
