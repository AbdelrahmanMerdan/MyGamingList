package test;

import src.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestMostPlayedPopReleases {

	@Test
	void test() {
		MostPlayed mostPlayed = new MostPlayed();
		assertEquals(100, mostPlayed.getSize());
	}
	
	@Test
	void test2() {
		PopReleases popReleases= new PopReleases();
		assertEquals(25, popReleases.getSize());
	}

}
