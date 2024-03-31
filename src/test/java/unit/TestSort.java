package unit;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import mygaminglist.GUIGameReviews;

@SuppressWarnings({ "serial", "unchecked" })
class TestSort {
	
	static ArrayList<Object> testArr;
	
	@BeforeAll
	static void setup() {
		ArrayList<String> item1 = new ArrayList<String>() {{
			add("1");
			add("");
			add("Yes");
			add("Yes");
		}};
		ArrayList<String> item2 = new ArrayList<String>() {{
			add("2");
			add("");
			add("No");
			add("No");
		}};
		ArrayList<String> item3 = new ArrayList<String>() {{
			add("3");
			add("");
			add("Yes");
			add("Yes");
		}};
		ArrayList<String> item4 = new ArrayList<String>() {{
			add("4");
			add("");
			add("No");
			add("No");
		}};
		ArrayList<String> item5 = new ArrayList<String>() {{
			add("5");
			add("");
			add("Yes");
			add("Yes");
		}};

		testArr = new ArrayList<Object>() {{
			add(item1);
			add(item2);
			add(item3);
			add(item4);
			add(item5);
		}};
	}
	
	@Test
	void testSortGame1() { // chronOrder + no sort
		List<Object> sortedArr = GUIGameReviews.testSort(true, 0, true, testArr);
		int expectedValue = 12345;
		String actualValue = "";
		for (Object i: sortedArr) {
			actualValue += (String) ((ArrayList<Object>) i).get(0);
		}
		assertEquals(expectedValue, Integer.parseInt(actualValue));
	}
	
	@Test
	void testSortGame2() { // chronOrder + recommend
		List<Object> sortedArr = GUIGameReviews.testSort(true, 1, true, testArr);
		int expectedValue = 135;
		String actualValue = "";
		for (Object i: sortedArr) {
			actualValue += (String) ((ArrayList<Object>) i).get(0);
		}
		assertEquals(expectedValue, Integer.parseInt(actualValue));
	}
	
	@Test
	void testSortGame3() { // chronOrder + not recommend
		List<Object> sortedArr = GUIGameReviews.testSort(true, 2, true, testArr);
		int expectedValue = 24;
		String actualValue = "";
		for (Object i: sortedArr) {
			actualValue += (String) ((ArrayList<Object>) i).get(0);
		}
		assertEquals(expectedValue, Integer.parseInt(actualValue));
	}
	
	@Test
	void testSortGame4() { // reverse chronOrder + no sort
		List<Object> sortedArr = GUIGameReviews.testSort(false, 0, true, testArr);
		int expectedValue = 54321;
		String actualValue = "";
		for (Object i: sortedArr) {
			actualValue += (String) ((ArrayList<Object>) i).get(0);
		}
		assertEquals(expectedValue, Integer.parseInt(actualValue));
	}
	
	@Test
	void testSortGame5() { // reverse chronOrder + recommend
		List<Object> sortedArr = GUIGameReviews.testSort(false, 1, true, testArr);
		int expectedValue = 531;
		String actualValue = "";
		for (Object i: sortedArr) {
			actualValue += (String) ((ArrayList<Object>) i).get(0);
		}
		assertEquals(expectedValue, Integer.parseInt(actualValue));
	}
	
	@Test
	void testSortGame6() { // reverse chronOrder + not recommend
		List<Object> sortedArr = GUIGameReviews.testSort(false, 2, true, testArr);
		int expectedValue = 42;
		String actualValue = "";
		for (Object i: sortedArr) {
			actualValue += (String) ((ArrayList<Object>) i).get(0);
		}
		assertEquals(expectedValue, Integer.parseInt(actualValue));
	}
	
	@Test
	void testSortUser1() { // chronOrder + no sort
		List<Object> sortedArr = GUIGameReviews.testSort(true, 0, false, testArr);
		int expectedValue = 12345;
		String actualValue = "";
		for (Object i: sortedArr) {
			actualValue += (String) ((ArrayList<Object>) i).get(0);
		}
		assertEquals(expectedValue, Integer.parseInt(actualValue));
	}
	
	@Test
	void testSortUser2() { // chronOrder + recommend
		List<Object> sortedArr = GUIGameReviews.testSort(true, 1, false, testArr);
		int expectedValue = 135;
		String actualValue = "";
		for (Object i: sortedArr) {
			actualValue += (String) ((ArrayList<Object>) i).get(0);
		}
		assertEquals(expectedValue, Integer.parseInt(actualValue));
	}
	
	@Test
	void testSortUser3() { // chronOrder + not recommend
		List<Object> sortedArr = GUIGameReviews.testSort(true, 2, false, testArr);
		int expectedValue = 24;
		String actualValue = "";
		for (Object i: sortedArr) {
			actualValue += (String) ((ArrayList<Object>) i).get(0);
		}
		assertEquals(expectedValue, Integer.parseInt(actualValue));
	}
	
	@Test
	void testSortUser4() { // reverse chronOrder + no sort
		List<Object> sortedArr = GUIGameReviews.testSort(false, 0, false, testArr);
		int expectedValue = 54321;
		String actualValue = "";
		for (Object i: sortedArr) {
			actualValue += (String) ((ArrayList<Object>) i).get(0);
		}
		assertEquals(expectedValue, Integer.parseInt(actualValue));
	}
	
	@Test
	void testSortUser5() { // reverse chronOrder + recommend
		List<Object> sortedArr = GUIGameReviews.testSort(false, 1, false, testArr);
		int expectedValue = 531;
		String actualValue = "";
		for (Object i: sortedArr) {
			actualValue += (String) ((ArrayList<Object>) i).get(0);
		}
		assertEquals(expectedValue, Integer.parseInt(actualValue));
	}
	
	@Test
	void testSortUser6() { // reverse chronOrder + not recommend
		List<Object> sortedArr = GUIGameReviews.testSort(false, 2, false, testArr);
		int expectedValue = 42;
		String actualValue = "";
		for (Object i: sortedArr) {
			actualValue += (String) ((ArrayList<Object>) i).get(0);
		}
		assertEquals(expectedValue, Integer.parseInt(actualValue));
	}
	
}
