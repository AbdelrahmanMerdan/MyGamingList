package src;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import org.json.*;

public class PopReleases {

	//Instance variable
	ArrayList<Game> games = new ArrayList<>();

	
	public PopReleases() {
		String jSON = "{\"id\":\"cat_newreleases\",\"name\":\"New Releases\",\"tabs\":{\"viewall\":{\"name\":\"View All\",\"total_item_count\":53591,\"items\":[{\"type\":0,\"id\":2358050},{\"type\":0,\"id\":2762940},{\"type\":0,\"id\":2626410},{\"type\":0,\"id\":2772300},{\"type\":0,\"id\":1126270},{\"type\":0,\"id\":2462100},{\"type\":0,\"id\":2684030},{\"type\":0,\"id\":2753330},{\"type\":0,\"id\":2682430},{\"type\":0,\"id\":2347040},{\"type\":0,\"id\":2756780},{\"type\":0,\"id\":2655130},{\"type\":0,\"id\":2570630},{\"type\":0,\"id\":2591380},{\"type\":0,\"id\":2218620},{\"type\":0,\"id\":2010160},{\"type\":0,\"id\":2647100},{\"type\":0,\"id\":2767280},{\"type\":0,\"id\":2834890},{\"type\":0,\"id\":2225830},{\"type\":0,\"id\":2762560},{\"type\":0,\"id\":2541400},{\"type\":0,\"id\":1658970},{\"type\":0,\"id\":2786830},{\"type\":0,\"id\":2468110},{\"type\":0,\"id\":2782390},{\"type\":0,\"id\":2810100},{\"type\":0,\"id\":2702760},{\"type\":0,\"id\":2397880},{\"type\":0,\"id\":2712630}]},\"topsellers\":{\"name\":\"Top Sellers\",\"total_item_count\":21230,\"items\":[{\"type\":0,\"id\":553850},{\"type\":0,\"id\":1623730},{\"type\":0,\"id\":1086940},{\"type\":0,\"id\":1203620},{\"type\":0,\"id\":2321470},{\"type\":0,\"id\":2519060},{\"type\":0,\"id\":899770},{\"type\":0,\"id\":881020},{\"type\":0,\"id\":1966720},{\"type\":0,\"id\":1778820},{\"type\":0,\"id\":1091500},{\"type\":0,\"id\":2161700},{\"type\":0,\"id\":1174180},{\"type\":0,\"id\":2731840},{\"type\":0,\"id\":1774580},{\"type\":0,\"id\":2138330},{\"type\":0,\"id\":1245620},{\"type\":0,\"id\":2140330},{\"type\":0,\"id\":1493640},{\"type\":0,\"id\":1144200},{\"type\":0,\"id\":311210},{\"type\":1,\"id\":586532},{\"type\":0,\"id\":2478970},{\"type\":0,\"id\":1888160},{\"type\":0,\"id\":1627720},{\"type\":0,\"id\":1332010},{\"type\":0,\"id\":359550},{\"type\":0,\"id\":221100},{\"type\":0,\"id\":2555190},{\"type\":0,\"id\":1326470}]},\"specials\":{\"name\":\"Specials\",\"total_item_count\":4003,\"items\":[{\"type\":0,\"id\":2682430},{\"type\":0,\"id\":2591380},{\"type\":0,\"id\":2804300},{\"type\":0,\"id\":2614070},{\"type\":0,\"id\":2529770},{\"type\":0,\"id\":2230980},{\"type\":0,\"id\":850190},{\"type\":0,\"id\":850190},{\"type\":0,\"id\":768200},{\"type\":0,\"id\":1295630},{\"type\":0,\"id\":1347760},{\"type\":0,\"id\":2585040},{\"type\":0,\"id\":1116080},{\"type\":0,\"id\":2428310},{\"type\":0,\"id\":2321880},{\"type\":0,\"id\":1901620},{\"type\":0,\"id\":1329360},{\"type\":0,\"id\":1335940},{\"type\":0,\"id\":2808770},{\"type\":0,\"id\":2808790},{\"type\":0,\"id\":2810110},{\"type\":0,\"id\":2441080},{\"type\":0,\"id\":2772900},{\"type\":0,\"id\":2810120},{\"type\":0,\"id\":2808760},{\"type\":0,\"id\":2321470},{\"type\":0,\"id\":2812910},{\"type\":0,\"id\":2507840},{\"type\":0,\"id\":2558750},{\"type\":0,\"id\":2414750}]},\"under_ten\":{\"name\":\"Under $10\",\"total_item_count\":39816,\"items\":[{\"type\":0,\"id\":2626410},{\"type\":0,\"id\":2772300},{\"type\":0,\"id\":2462100},{\"type\":0,\"id\":2684030},{\"type\":0,\"id\":2753330},{\"type\":0,\"id\":2682430},{\"type\":0,\"id\":2347040},{\"type\":0,\"id\":2756780},{\"type\":0,\"id\":2655130},{\"type\":0,\"id\":2570630},{\"type\":0,\"id\":2218620},{\"type\":0,\"id\":2010160},{\"type\":0,\"id\":2647100},{\"type\":0,\"id\":2767280},{\"type\":0,\"id\":2225830},{\"type\":0,\"id\":2762560},{\"type\":0,\"id\":2541400},{\"type\":0,\"id\":2786830},{\"type\":0,\"id\":2782390},{\"type\":0,\"id\":2810100},{\"type\":0,\"id\":2397880},{\"type\":0,\"id\":2712630},{\"type\":0,\"id\":2372220},{\"type\":0,\"id\":2751810},{\"type\":0,\"id\":2665380},{\"type\":0,\"id\":2815100},{\"type\":0,\"id\":2809110},{\"type\":0,\"id\":2807160},{\"type\":0,\"id\":1343730},{\"type\":0,\"id\":2809430}]},\"dlc\":{\"name\":\"DLC\",\"total_item_count\":9788,\"items\":[{\"type\":0,\"id\":1825941},{\"type\":0,\"id\":2832670},{\"type\":0,\"id\":2824220},{\"type\":0,\"id\":2804600},{\"type\":0,\"id\":2662410},{\"type\":0,\"id\":2805180},{\"type\":0,\"id\":2822220},{\"type\":0,\"id\":2750040},{\"type\":0,\"id\":2376680},{\"type\":0,\"id\":2818170},{\"type\":0,\"id\":2804870},{\"type\":0,\"id\":2815240},{\"type\":0,\"id\":2666240},{\"type\":0,\"id\":2583910},{\"type\":0,\"id\":2517380},{\"type\":0,\"id\":2540120},{\"type\":0,\"id\":2643800},{\"type\":0,\"id\":2783130},{\"type\":0,\"id\":2731850},{\"type\":0,\"id\":2733030},{\"type\":0,\"id\":2782850},{\"type\":0,\"id\":2660790},{\"type\":0,\"id\":2707800},{\"type\":0,\"id\":2747460},{\"type\":0,\"id\":2727990},{\"type\":0,\"id\":2681600},{\"type\":0,\"id\":2669360},{\"type\":0,\"id\":2399331},{\"type\":0,\"id\":2317940},{\"type\":0,\"id\":2764960}]}},\"status\":1}";
		
		JSONObject jSONResponse = new JSONObject(jSON);

		JSONArray list = jSONResponse.getJSONObject("tabs").getJSONObject("topsellers").getJSONArray("items");
		
		//Storing top releases
		for(int i = 0; i < list.length(); i++) 
		{
			games.add(new Game(list.getJSONObject(i).optString("id")));
		}

	}

	public Game getGame(int i) {
		return games.get(i);
	}

	
	public int getSize() {
		return games.size();
	}
	
	
	@Override
	public String toString() {
		return games.toString();
	}

}
