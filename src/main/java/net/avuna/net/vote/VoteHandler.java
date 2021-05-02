package net.avuna.net.vote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.avuna.Avuna;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

@Getter
@RequiredArgsConstructor
public class VoteHandler {

    private static final Gson gson = new GsonBuilder().create();
    private static final HttpClient client = HttpClient.newHttpClient();
    private final VoteConfig config;

    @SneakyThrows
    public void checkVotes(String username) {
        String url = String.format("%s/users/%s/votes", config.getApiUrl(),
                URLEncoder.encode(username, StandardCharsets.UTF_8.toString()));
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Authorization", "Bearer " + config.getBearerToken()).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(response -> {
            String json = response.body();
            VoteData[] data = gson.fromJson(json, VoteData[].class);
            PlayerVoteEvent voteEvent = new PlayerVoteEvent(username, data);
            Avuna.getEventManager().submit(voteEvent);
        }).exceptionally(e -> {
            System.err.println(e);
            return null;
        });
    }
    
    public CompletableFuture<HttpResponse<String>> viewVotes() {
        String url = String.format("%s/users", config.getApiUrl());
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .header("Authorization", "Bearer " + config.getBearerToken()).build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }
}
