package net.avuna.tests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class WoocommerceTest {

    private static final String consumerKey = "ck_5ae684798334e52552b1d4770619e55dc2a9332a";
    private static final String consumerSecret = "cs_9ee25a353436af8c35b10a915432f5e23d73e531";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) {
        HttpClient client = HttpClient.newBuilder().build();
        String authToken = Base64.getEncoder().encodeToString(String.format("%s:%s", consumerKey, consumerSecret).getBytes());
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://avuna.net/wp-json/wc/v3/rsps_orders?rsn=hayden&autoclaim=false"))
                .header("Authorization", "Basic " + authToken)
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //UnclaimedOrder order = gson.fromJson(response.body(), UnclaimedOrder.class);
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
