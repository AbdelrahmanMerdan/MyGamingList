package database;

import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;

import mygaminglist.GUIGame;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

import javax.swing.JPanel;

public class AutoSearch {
	
	private static JPanel card;
	
	public AutoSearch(JPanel panel) {
		card = panel;
	}
	
	public static ArrayList<String> getAutofill(String query) {
		TreeSet<String> result = new TreeSet<>();
		
		if (query.equals("")) {
			return new ArrayList<>(result);
		}
		
		Document autoAgg = new Document("$search", 
				new Document("index", "searchGames")
				.append("autocomplete", new Document("query", query).append("path", "name")));

		GameData.games.aggregate(Arrays.asList(autoAgg, limit(10), project(fields(include("name"))))).forEach(doc -> {
			try {
				String name = GameData.map.readTree(doc.toJson()).get("name").asText();
				
				result.add(name);
				
			} catch (JsonProcessingException e) {}
		});
		return new ArrayList<>(result);
	}

	public static void search(String name) {
		Bson filter = Filters.eq("name", name);
		FindIterable<Document> result = GameData.games.find(filter);
		
		if (result.first() != null) {
			int id = result.first().getInteger("_id");
			GUIGame.loadGame(id);
			((CardLayout) card.getLayout()).show(card, "game");
		}
	}
}
