package integration;

import src.*;
import database.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestMostPlayedPopReleases {

	@Test
	void test() {
		MostPlayed mostPlayed = new MostPlayed();
		
		for(int id : mostPlayed.getIDs())
		{
			assertNotNull(GameData.getName(id));
		}
	}
	
	@Test
	void test2() {
		PopReleases popReleases= new PopReleases();
		
		for(int id : popReleases.getIDs())
		{
			assertNotNull(GameData.getName(id));
		}
		
	}

}
