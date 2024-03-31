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
import java.util.List;

import javax.swing.JPanel;

public class AutoSearch {
	
	private static JPanel card;
	
	public AutoSearch(JPanel panel) {
		card = panel;
	}
	
	public static List<String> getAutofill(String query) {
		List<String> result = new ArrayList<String>();
		if (query.equals("")) {
			return result;
		}
		Document agg = new Document("$search", 
				new Document("index", "searchGames")
				.append("text", new Document("query", query).append("path", "name")));
		Document agg2 = new Document("$search", 
				new Document("index", "searchGames")
				.append("autocomplete", new Document("query", query).append("path", "name")));

//		GameData.games.aggregate(Arrays.asList(agg, limit(1), project(fields(include("name"))))).forEach(doc -> {
//			try {
//				result.add(GameData.map.readTree(doc.toJson()).get("name").asText());
//			} catch (JsonProcessingException e) {}
//		});
		GameData.games.aggregate(Arrays.asList(agg2, limit(10), project(fields(include("name"))))).forEach(doc -> {
			try {
				result.add(GameData.map.readTree(doc.toJson()).get("name").asText());
			} catch (JsonProcessingException e) {}
		});
		return result;
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
