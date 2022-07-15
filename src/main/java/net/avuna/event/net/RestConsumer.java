package net.avuna.event.net;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;

public abstract class RestConsumer {
    protected static final ObjectMapper mapper = new ObjectMapper();
    protected static HttpClient client = HttpClient.newBuilder().build();
}
