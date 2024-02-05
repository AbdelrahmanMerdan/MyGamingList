import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Description {

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://steam-api7.p.rapidapi.com/appid/Grand%20Theft%20Auto%20V"))
                .header("X-RapidAPI-Key", "148bc7ae7emsh9ce2312cdf4c7e7p188d53jsn20da7f80baeb")
                .header("X-RapidAPI-Host", "steam-api7.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        getId("Grand Theft Auto V");
    }
    public static void getId(String gameTitle) throws IOException, InterruptedException {

        String replaceString = gameTitle.replace(" ", "%20");
        System.out.println(replaceString);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://steam-api7.p.rapidapi.com/appid/" + replaceString))
                .header("X-RapidAPI-Key", "148bc7ae7emsh9ce2312cdf4c7e7p188d53jsn20da7f80baeb")
                .header("X-RapidAPI-Host", "steam-api7.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//
        System.out.println(response.body());

    }

}
