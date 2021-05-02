package net.avuna.net.donation.runepay;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.avuna.Avuna;
import net.avuna.config.Configurable;
import net.avuna.event.impl.donations.PlayerDonatedEvent;
import net.avuna.net.RestConsumer;
import net.avuna.net.donation.DonationStrategy;
import net.avuna.net.donation.Transaction;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@Getter
@RequiredArgsConstructor
public class RunePayHandler extends RestConsumer implements DonationStrategy, Configurable<RunePayConfig> {

    private final RunePayConfig config;

    @Override
    public void checkDonations(String username) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        requestBuilder.uri(URI.create(String.format("https://api.runepay.app/payments/user/%s/pending?autoClaim=true", username)));
        requestBuilder.setHeader("Authorization", "Bearer " + config.getBearerToken());
        HttpRequest request = requestBuilder.build();
        CompletableFuture<HttpResponse<String>> future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        future.thenAccept(response -> {
            JsonArray arr = gson.fromJson(response.body(), JsonArray.class);
            arr.iterator().forEachRemaining(jsonElement -> {
                JsonObject order = jsonElement.getAsJsonObject();
                int quantity = order.get("quantity").getAsInt();
                JsonObject product = order.get("stores_product").getAsJsonObject();
                String itemName = product.get("item_name").getAsString();
                int productId = product.get("id").getAsInt();
                double price = product.get("price").getAsDouble();
                Transaction transaction = new Transaction(username, itemName, productId, quantity, price);
                PlayerDonatedEvent event = new PlayerDonatedEvent(transaction);
                Avuna.getEventManager().submit(event);
            });
        });
    }
}
