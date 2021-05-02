package net.avuna.tests;

import lombok.SneakyThrows;
import net.avuna.discord.webhook.DiscordWebhook;
import net.avuna.discord.webhook.Embed;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestWebhook {

    @SneakyThrows
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("If you want to manually test a webhook please pass an argument with the webhook");
            return;
        }
        DiscordWebhook webhook = new DiscordWebhook(args[0]);
        webhook.setAvatarUrl("https://vignette.wikia.nocookie.net/runescape2/images/2/22/White_partyhat_detail.png/revision/latest?cb=20120719110656");
        Embed embed = Embed.builder().color(0xc29c06).description("give them a big thanks for helping keep the server alive :)");
        embed.thumbnail("https://vignette.wikia.nocookie.net/runescape2/images/2/22/White_partyhat_detail.png/revision/latest?cb=20120719110656");


        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);


        embed.footer(formatDateTime, "");
        webhook.setContent("Hayden has donated $50 for a new puppy!");
        webhook.addEmbed(embed);
        webhook.execute();
    }
}
