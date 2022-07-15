package net.avuna.event.net.donation.woocommerce;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.avuna.Avuna;
import net.avuna.config.Configurable;
import net.avuna.event.impl.donations.WoocommerceDonatedEvent;
import net.avuna.event.net.RestConsumer;
import net.avuna.event.net.donation.woocommerce.beans.WoocommerceData;
import net.avuna.event.net.donation.DonationStrategy;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@Getter
@RequiredArgsConstructor
public class WoocommerceHandler extends RestConsumer implements DonationStrategy, Configurable<WoocommerceConfig> {

    private final WoocommerceConfig config;

    @Override
    public void checkDonations(String username) {
        String consumerKey = config.getConsumerKey();
        String consumerSecret = config.getConsumerSecret();
        String authToken = Base64.getEncoder().encodeToString(String.format("%s:%s", consumerKey, consumerSecret).getBytes());
        boolean autoClaim = config.isAutoClaim();
        final String URL = String.format("%s/wp-json/wc/v3/rsps_orders?rsn=%s&autoclaim=%s", config.getDomain(), username, String.valueOf(autoClaim));
        HttpRequest request = HttpRequest.newBuilder(URI.create(URL))
                .header("Authorization", "Basic " + authToken).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(response -> {
            WoocommerceData woocommerce = null;
            try {
                woocommerce = mapper.readValue(response.body(), WoocommerceData.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            Avuna.getEventManager().submit(new WoocommerceDonatedEvent(username, woocommerce));
        });
    }

    //TODO: Send PUT request to woocommerce API
    public void setClaimed(int orderId) {

    }
}
