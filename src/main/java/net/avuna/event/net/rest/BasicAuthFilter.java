package net.avuna.event.net.rest;

import lombok.RequiredArgsConstructor;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Base64;

@RequiredArgsConstructor
public class BasicAuthFilter implements Filter {

    private final String username;
    private final String password;

    @Override
    public void handle(Request request, Response response)  {
        if (!request.headers().contains("Authorization") || !authenticated(request)) {
            response.header("WWW-Authenticate", "Basic");
            Spark.halt(401, "Not authenticated");
        }
    }

    private boolean authenticated(Request request) {
        String encodedHeader = request.headers("Authorization");
        encodedHeader = encodedHeader.replaceAll("Basic\s", "");
        String[] submittedCredentials = extractCredentials(encodedHeader);
        if(submittedCredentials.length != 2) {
            return false;
        }
        String submittedUsername = submittedCredentials[0];
        String submittedPassword = submittedCredentials[1];
        return submittedUsername.equals(username) && submittedPassword.equals(password);
    }

    private String[] extractCredentials(String encodedHeader) {
        if (encodedHeader != null) {
            String decodedHeader = new String(Base64.getDecoder().decode(encodedHeader));
            return decodedHeader.split(":");
        } else {
            return new String[0];
        }
    }
}
