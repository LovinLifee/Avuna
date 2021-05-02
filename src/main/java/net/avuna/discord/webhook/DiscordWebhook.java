package net.avuna.discord.webhook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author https://gist.github.com/k3kdude/fba6f6b37594eae3d6f9475330733bdb
 *
 */
@Accessors
public class DiscordWebhook {

	private static final HttpClient httpClient = HttpClient.newHttpClient();
	private static final Gson gson = new GsonBuilder().create();

	private final String url;
	private String content;
	private String username;
	private String avatarUrl;
	private boolean tts;
	private List<Embed> embeds = new ArrayList<>();

	/**
	 * Constructs a new DiscordWebhook instance
	 *
	 * @param url
	 *                The webhook URL obtained in Discord
	 */
	public DiscordWebhook(String url) {
		this.url = url;
	}

	public DiscordWebhook setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
		return this;
	}

	public DiscordWebhook setTTS(boolean tts) {
		this.tts = tts;
		return this;
	}

	public DiscordWebhook setUsername(String username) {
		this.username = username;
		return this;
	}

	public DiscordWebhook setContent(String content) {
		this.content = content;
		return this;
	}

	public DiscordWebhook addEmbed(Embed embed) {
		this.embeds.add(embed);
		return this;
	}

	public void executeAsync() {
		CompletableFuture.runAsync(() -> {
			execute();
		});
	}

	public void execute() {
		if (this.content == null && this.embeds.isEmpty()) {
			throw new IllegalArgumentException("Set content or add at least one Embed");
		}
		JsonObject json = new JsonObject();
		json.addProperty("content", this.content);
		json.addProperty("username", this.username);
		json.addProperty("avatar_url", this.avatarUrl);
		json.addProperty("tts", this.tts);
		if (!this.embeds.isEmpty()) {
			List<JsonObject> embeds = new ArrayList<>();
			for (Embed embed : this.embeds) {
				JsonObject jsonEmbed = new JsonObject();
				jsonEmbed.addProperty("title", embed.getTitle());
				jsonEmbed.addProperty("description", embed.getDescription());
				jsonEmbed.addProperty("url", embed.getUrl());
				jsonEmbed.addProperty("color", embed.getColor());
				Embed.Footer footer = embed.getFooter();
				Embed.Image image = embed.getImage();
				Embed.Thumbnail thumbnail = embed.getThumbnail();
				Embed.Author author = embed.getAuthor();
				List<Embed.Field> fields = embed.getFields();
				if (footer != null) {
					JsonObject jsonFooter = new JsonObject();
					jsonFooter.addProperty("text", footer.getText());
					jsonFooter.addProperty("icon_url", footer.getIconUrl());
					jsonEmbed.add("footer", jsonFooter);
				}
				if (image != null) {
					JsonObject jsonImage = new JsonObject();
					jsonImage.addProperty("url", image.getUrl());
					jsonEmbed.add("image", jsonImage);
				}
				if (thumbnail != null) {
					JsonObject jsonThumbnail = new JsonObject();
					jsonThumbnail.addProperty("url", thumbnail.getUrl());
					jsonEmbed.add("thumbnail", jsonThumbnail);
				}
				if (author != null) {
					JsonObject jsonAuthor = new JsonObject();
					jsonAuthor.addProperty("name", author.getName());
					jsonAuthor.addProperty("url", author.getUrl());
					jsonAuthor.addProperty("icon_url", author.getIconUrl());
					jsonEmbed.add("author", jsonAuthor);
				}
				List<JsonObject> jsonFields = new ArrayList<>();
				for (Embed.Field field : fields) {
					JsonObject jsonField = new JsonObject();
					jsonField.addProperty("name", field.getName());
					jsonField.addProperty("value", field.getValue());
					jsonField.addProperty("inline", field.isInline());
					jsonFields.add(jsonField);
				}
				jsonEmbed.add("fields", gson.toJsonTree(jsonFields.toArray()));
				embeds.add(jsonEmbed);
			}
			json.add("embeds", gson.toJsonTree(embeds.toArray()));
		}
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(json.toString()))
				.build();
		try {
			httpClient.send(request, HttpResponse.BodyHandlers.discarding());
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
}