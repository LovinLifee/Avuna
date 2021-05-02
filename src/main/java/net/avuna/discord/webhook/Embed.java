package net.avuna.discord.webhook;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Embed {

	private String title;
	private String description;
	private String url;
	private int color;

	private Footer footer;
	private Thumbnail thumbnail;
	private Image image;
	private Author author;

	private final List<Field> fields = new ArrayList<>();

	public static Embed builder() {
		return new Embed();
	}
	
	public Embed url(String url) {
		this.url = url;
		return this;
	}
	
	public Embed description(String description) {
		this.description = description;
		return this;
	}
	
	public Embed color(int color) {
		this.color = color;
		return this;
	}
	
	public Embed title(String title) {
		this.title = title;
		return this;
	}
	
	public Embed addField(String name, String value, boolean inline) {
		this.fields.add(new Field(name, value, inline));
		return this;
	}
	
	public Embed author(String name, String url, String iconUrl) {
		this.author = new Author(name, url, iconUrl);
		return this;
	}
	
	public Embed footer(String text, String iconUrl) {
		this.footer = new Footer(text, iconUrl);
		return this;
	}
	
	public Embed field(String name, String value, boolean inline) {
		this.fields.add(new Field(name, value, inline));
		return this;
	}
	
	public Embed image(String imageUrl) {
		this.image = new Image(imageUrl);
		return this;
	}
	
	public Embed thumbnail(String imageUrl) {
		this.thumbnail = new Thumbnail(imageUrl);
		return this;
	}
	
	@Getter
	@AllArgsConstructor
	class Author {
		private final String name;
		private final String url;
		private final String iconUrl;
	}
	
	@Getter
	@AllArgsConstructor
	class Field {
		private final String name;
		private final String value;
		private final boolean inline;
	}
	
	@Getter
	@AllArgsConstructor class Footer {
		private final String text;
		private final String iconUrl;
	}
	
	@Getter
	@AllArgsConstructor
	class Image {
		private final String url;
	}
	
	@Getter
	@AllArgsConstructor
	class Thumbnail {
		private final String url;
	}
}