package net.avuna.event.net.vote;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static final ObjectMapper mapper = new ObjectMapper();
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
            VoteData[] data = new VoteData[0];
            try {
                data = mapper.readValue(json, VoteData[].class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
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
