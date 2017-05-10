package space.gatt.kbb;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import space.gatt.kbb.commandmanager.ReturnMessage;

import java.awt.*;

public class MessageManager {

	public void sendMessage(TextChannel chnl, String content){
		sendMessage(chnl, Color.WHITE, content);
	}

	public void sendMessage(TextChannel chnl, Color color, String content){
		try{

			MessageEmbed emb = new EmbedBuilder().setColor(color).setDescription(content).build();
			chnl.sendMessage(emb).complete(true);
		}catch (RateLimitedException e){

		}
	}

	public void sendMessage(TextChannel chnl, Color color, String content, String imageURL){
		try{

			MessageEmbed emb = new EmbedBuilder().setColor(color).setDescription(content).setImage(imageURL).build();
			chnl.sendMessage(emb).complete(true);
			MessageEmbed emb2 = new EmbedBuilder().setColor(Color.RED).addField("Sent a message to " + chnl.getName()
					, "Message Contents: \n```\n" + content + "\n```", true).build();
			KingMain.getDebugChannel().sendMessage(emb2).complete(true);
		}catch (RateLimitedException e){

		}
	}

	public void sendMessage(Member member, ReturnMessage msg){
		if (msg.getImageURL() != null){
			sendMessage(member, msg.getColor(), msg.getMessage(), msg.getImageURL());
			return;
		}
		sendMessage(member, msg.getColor(), msg.getMessage());
	}

	public void sendMessage(Member member, Color color, String content, String imageURL){
		PrivateChannel chnl = !member.getUser().hasPrivateChannel() ? member.getUser().openPrivateChannel().complete() : member
				.getUser().getPrivateChannel();
		try{
			MessageEmbed emb = new EmbedBuilder().setColor(color).setDescription(content).setImage(imageURL).build();
			chnl.sendMessage(emb).complete(true);
		}catch (RateLimitedException e){

		}
	}

	public void sendMessage(Member member, Color color, String content){

		PrivateChannel chnl = !member.getUser().hasPrivateChannel() ? member.getUser().openPrivateChannel().complete() : member
				.getUser().getPrivateChannel();
		try{
			MessageEmbed emb = new EmbedBuilder().setColor(color).setDescription(content).build();
			chnl.sendMessage(emb).complete(true);
		}catch (RateLimitedException e){

		}
	}

	public void sendMessage(PrivateChannel chnl, Color color, String content){
		try{
			MessageEmbed emb = new EmbedBuilder().setColor(color).setDescription(content).build();
			chnl.sendMessage(emb).complete(true);
			MessageEmbed emb2 = new EmbedBuilder().setColor(Color.RED).addField("Sent a message to " + chnl.getName()
					, "Message Contents: \n```\n" + content + "\n```", true).build();
			KingMain.getDebugChannel().sendMessage(emb2).complete(true);
		}catch (RateLimitedException e){

		}
	}

	public void deleteMessage(Message m){
		try{
			m.delete().complete(true);
		}catch (RateLimitedException e){

		}
	}

	public void sendMessage(TextChannel chnl, ReturnMessage msg){
		try{
			chnl.sendMessage(msg.build()).complete(true);
		}catch (RateLimitedException e){

		}
	}

	public void log(TextChannel chnl, String content){
		log(chnl, Color.RED, content);
	}

	public void log(TextChannel chnl, Color color, String content){
		try{

			MessageEmbed emb = new EmbedBuilder().setColor(color).setDescription(content).build();
			chnl.sendMessage(emb).complete(true);
		}catch (RateLimitedException e){

		}
	}

}
