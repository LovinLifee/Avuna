package net.avuna.discord.webhook;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
	private static final ObjectMapper mapper = new ObjectMapper();

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
		ObjectNode json = mapper.createObjectNode();
		json.put("content", this.content);
		json.put("username", this.username);
		json.put("avatar_url", this.avatarUrl);
		json.put("tts", this.tts);
		if (!this.embeds.isEmpty()) {
			List<ObjectNode> embeds = new ArrayList<>();
			for (Embed embed : this.embeds) {
				ObjectNode jsonEmbed = mapper.createObjectNode();
				jsonEmbed.put("title", embed.getTitle());
				jsonEmbed.put("description", embed.getDescription());
				jsonEmbed.put("url", embed.getUrl());
				jsonEmbed.put("color", embed.getColor());
				Embed.Footer footer = embed.getFooter();
				Embed.Image image = embed.getImage();
				Embed.Thumbnail thumbnail = embed.getThumbnail();
				Embed.Author author = embed.getAuthor();
				List<Embed.Field> fields = embed.getFields();
				if (footer != null) {
					ObjectNode jsonFooter = mapper.createObjectNode();
					jsonFooter.put("text", footer.getText());
					jsonFooter.put("icon_url", footer.getIconUrl());
					jsonEmbed.set("footer", jsonFooter);
				}
				if (image != null) {
					ObjectNode jsonImage = mapper.createObjectNode();
					jsonImage.put("url", image.getUrl());
					jsonEmbed.set("image", jsonImage);
				}
				if (thumbnail != null) {
					ObjectNode jsonThumbnail = mapper.createObjectNode();
					jsonThumbnail.put("url", thumbnail.getUrl());
					jsonEmbed.set("thumbnail", jsonThumbnail);
				}
				if (author != null) {
					ObjectNode jsonAuthor = mapper.createObjectNode();
					jsonAuthor.put("name", author.getName());
					jsonAuthor.put("url", author.getUrl());
					jsonAuthor.put("icon_url", author.getIconUrl());
					jsonEmbed.set("author", jsonAuthor);
				}
				List<JsonNode> jsonFields = new ArrayList<>();
				for (Embed.Field field : fields) {
					ObjectNode jsonField = mapper.createObjectNode();
					jsonField.put("name", field.getName());
					jsonField.put("value", field.getValue());
					jsonField.put("inline", field.isInline());
					jsonFields.add(jsonField);
				}
				ArrayNode f = mapper.createArrayNode();
				f.addAll(jsonFields);
				jsonEmbed.set("fields", f);
				embeds.add(jsonEmbed);
			}
			ArrayNode a = mapper.createArrayNode();
			a.addAll(embeds);
			json.set("embeds", a);
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