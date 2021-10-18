package com.severalcircles.flaja;

import com.severalcircles.flaja.data.user.UserNotFoundException;
import com.severalcircles.flames.data.user.FlamesUser;
import com.severalcircles.flames.data.user.UserFunFacts;
import com.severalcircles.flames.data.user.UserStats;
import com.severalcircles.flames.features.rank.Rank;
import org.json.JSONObject;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;

/**
 * Represents a connection to the API
 */
public class Flaja {
    public HttpClient getClient() {
        return client;
    }

    private HttpClient client;

    public Flaja() {
        client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    /**
     * Because Flaja doesn't keep-alive, this function only confirms that its able to connect to the API, although you should still run it when you create an instance of Flaja.
     */
    public void connect() throws IOException {

    }
    public FlamesUser getUserByID(String id) throws IOException, InterruptedException, UserNotFoundException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://flamesapi.severalcircles.com/user/" + id))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response =
                getClient().send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 404) throw new UserNotFoundException();
        JSONObject userObject = new JSONObject(response.body());
        request = HttpRequest.newBuilder()
                .uri(URI.create("https://flamesapi.severalcircles.com/user/" + id + "/funfacts"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        response =
                getClient().send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 404) throw new UserNotFoundException();
        JSONObject funFactsObject = new JSONObject(response.body());
        request = HttpRequest.newBuilder()
                .uri(URI.create("https://flamesapi.severalcircles.com/user/" + id + "/stats"))
                .timeout(Duration.ofMinutes(1))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        response =
                getClient().send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 404) throw new UserNotFoundException();
        JSONObject statsObject = new JSONObject(response.body());
        UserStats stats = new UserStats(statsObject.getInt("exp"), statsObject.getInt("level"), statsObject.getInt("POW"), statsObject.getInt("RES"), statsObject.getInt("LUCK"), statsObject.getInt("RISE"), statsObject.getInt("CAR"));
        UserFunFacts funFacts = new UserFunFacts(Instant.parse(funFactsObject.getString("sadDay")), funFactsObject.getFloat("lowestEmotion"), Instant.parse(funFactsObject.getString("happyDay")), funFactsObject.getFloat("highestEmotion"), funFactsObject.getInt("highScore"), funFactsObject.getInt("lowScore"), Rank.valueOf(funFactsObject.getString("bestRank").toUpperCase(Locale.ROOT).replace(" ", "_")), funFactsObject.getInt("frenchToastScore"));
        return new FlamesUser(userObject.getInt("score"), userObject.getFloat("emotion"), Instant.parse(userObject.getString("lastSeen")), userObject.getInt("streak"), userObject.getString("discordId"), stats, userObject.getInt("consent"), funFacts, userObject.getDouble("version"));
    }
}