package unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import database.AutoSearch;

public class TestSearch {
	
	@Test
	void directSearch() {
		int expectedValue = 17470;
		int actualValue = AutoSearch.search("Dead Space (2008)");
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	void invalidSearch() {
		int expectedValue = -1;
		int actualValue = AutoSearch.search("testingthis function");
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	void nullSearch() {
		int expectedValue = -1;
		int actualValue = AutoSearch.search(null);
		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	void autoSuggest() { // only compares first 4 and likely to be out of date
		String[] expectedValue = {"Dead Space","Dead Space (2008)","Dead Space 2","Dead Space 2 Gameplay Trailer"};// manual results from database website https://www.mongodb.com as of 31/mar/24
		String[] actualValue = new String[4];
		ArrayList<String> values = AutoSearch.getAutofill("Dead Space");
		for (int i = 0; i < 4; i++) {
			actualValue[i] = values.get(i);
		}
		assertTrue(Arrays.equals(expectedValue, actualValue));
	}
}
