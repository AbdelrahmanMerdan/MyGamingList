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
}
