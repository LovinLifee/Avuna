package net.avuna.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.http.HttpClient;

public abstract class RestConsumer {
    protected static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    protected static HttpClient client = HttpClient.newBuilder().build();
}
