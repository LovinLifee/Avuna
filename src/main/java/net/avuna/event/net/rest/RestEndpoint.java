package net.avuna.event.net.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.avuna.Avuna;
import net.avuna.game.entity.player.IPlayer;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class RestEndpoint {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final RestEndpointConfig config;

    public void init() {
        Spark.port(config.getPort());
        Spark.staticFileLocation("/web");
        if(config.isEnableConsole()) {
            Spark.webSocket("/console", CommandWebSocketHandler.class);
        }
        Spark.before("users/", new BasicAuthFilter(config.getAuthUsername(), config.getAuthPassword()));

        Spark.get("/users", this::usersRoute, mapper::writeValueAsString);

        Spark.after((request, response) -> {
            response.header("Content-Encoding", "gzip");
        });
    }

    private Map<String, String> usersRoute(Request request, Response response) {
       return Avuna.getPlayers().getPlayers().collect(
               Collectors.toMap(IPlayer::getUsername, p -> p.getPermissions().toString()));
    }

    public static void main(String[] args) {
        Avuna.bootstrap();
    }
}
