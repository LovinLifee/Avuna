package net.avuna.tests;

import lombok.SneakyThrows;
import net.avuna.Avuna;
import net.avuna.discord.webhook.DiscordWebhook;
import net.avuna.discord.webhook.Embed;
import net.avuna.event.impl.donations.PlayerDonatedEvent;
import net.avuna.event.impl.donations.WoocommerceDonatedEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DonationTest {

    public static void main(String[] args) throws InterruptedException {
        Avuna.bootstrap();
        Avuna.getEventManager().registerEventListener(WoocommerceDonatedEvent.class, DonationTest::onDonate);
        Avuna.getDonationHandler().checkDonations("hayden");
        Thread.sleep(4000L);
        Avuna.shutdown();
    }

    private static void onDonate(WoocommerceDonatedEvent event) {
        System.out.println(event.getUsername());
        event.getData().getUnclaimedOrders().forEach(System.out::println);
    }
}
