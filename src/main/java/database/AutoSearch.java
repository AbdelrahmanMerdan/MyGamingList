package database;

import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import mygaminglist.GUIGame;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

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
				.append("autocomplete", new Document("query", query).append("path", "name")));

		GameData.games.aggregate(Arrays.asList(agg, limit(5), project(fields(include("name"))))).forEach(doc -> {
			try {
				result.add(GameData.map.readTree(doc.toJson()).get("name").asText());
			} catch (JsonProcessingException e) {}
		});
		return result;
	}

	public static void search(String name) {
		Pattern namePattern = Pattern.compile("^" + name + "$");
		Bson filter = regex("name", namePattern);
		FindIterable<Document> result = GameData.games.find(filter);
		
		if (result.first() != null) {
			int id = result.first().getInteger("_id");
			GUIGame.loadGame(id);
			((CardLayout) card.getLayout()).show(card, "game");
		}
	}
}
