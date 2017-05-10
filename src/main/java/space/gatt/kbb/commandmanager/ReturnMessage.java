package space.gatt.kbb.commandmanager;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;

public class ReturnMessage {

	private Color color = null;
	private String message = null;
	private String imageURL = null, thumbnailURL = null;
	private String title = null;

	public ReturnMessage() {
		this.color = Color.WHITE;
		this.message = "Oh.";
	}

	@Deprecated
	public ReturnMessage(Color color, String message) {
		this.color = color;
		this.message = message;
	}
	@Deprecated
	public ReturnMessage(Color color, String message, String imageURL) {
		this.color = color;
		this.message = message;
		this.imageURL = imageURL;
	}
	@Deprecated
	public ReturnMessage(Color color, String message, String imageURL, String title) {
		this.color = color;
		this.message = message;
		this.imageURL = imageURL;
		this.title = title;
	}

	public String getImageURL() {
		return imageURL;
	}

	public Color getColor() {
		return color;
	}

	public String getMessage() {
		return message;
	}

	public ReturnMessage setColor(Color color) {
		this.color = color;
		return this;
	}

	public ReturnMessage setMessage(String message) {
		this.message = message;
		return this;
	}

	public ReturnMessage setImageURL(String imageURL) {
		this.imageURL = imageURL;
		return this;

	}

	public ReturnMessage setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public String getThumbnailURL() {
		return thumbnailURL;
	}

	public ReturnMessage setThumbnailURL(String thumbnailURL) {
		this.thumbnailURL = thumbnailURL;
		return this;
	}

	public MessageEmbed build(){
		EmbedBuilder embB = new EmbedBuilder();
		String title = getTitle() != null ? getTitle() : "";
		String msg = getMessage() != null ? getMessage() : "";
		Color clr = getColor() != null ? getColor() : Color.WHITE;
		String url = getImageURL() != null ? getImageURL() : null;
		String thumb = getThumbnailURL() != null ? getThumbnailURL() : null;
		if (!title.equalsIgnoreCase("")) {
			embB.addField(title, msg, true);
		}else{
			embB.setDescription(msg);
		}
		embB.setColor(clr);
		embB.setImage(url);
		embB.setThumbnail(thumb);
		return embB.build();
	}

}
