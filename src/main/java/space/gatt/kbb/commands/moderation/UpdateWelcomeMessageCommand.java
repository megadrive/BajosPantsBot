package space.gatt.kbb.commands.moderation;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import space.gatt.kbb.KingMain;
import space.gatt.kbb.annotations.*;
import space.gatt.kbb.commandmanager.ReturnMessage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

@Command("updateverify")
@Syntax("updateverify")
@Usage("updatecolours")
@Permissions(ranks = {"294441010077892608"}, ranksById = true)
@Description("Update colours")
@Group("moderation")
@CommandSettings(deleteInitatingMsg = true)
public class UpdateWelcomeMessageCommand {

	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		if (message.getTextChannel().getId().equalsIgnoreCase("302980774511247366")) {
			TextChannel roleAssign = KingMain.getBajoGuild().getTextChannelById("309932250114293761");
			List<Message> msgs = roleAssign.getHistory().retrievePast(50).complete();
			for (Message msg : msgs){
				msg.delete().complete();
			}

			ArrayList<MessageEmbed> messagesToSend = new ArrayList<>();

			MessageEmbed intro = new EmbedBuilder().setColor(Color.WHITE).setDescription(
					"You've been here for at least 10 minutes! It's time to **verify** yourself as a " +
							"human-being!\nSimply type `_verifyme` and it will all be over!\n\n*Just remember; You do" +
							"not talk about ~~fight~~ Bajo Club!*"
			).build();
			messagesToSend.add(intro);


			try {
				Message msg = roleAssign.sendMessage("==============** Last Updated : " + message.getCreationTime
						().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy HH:mm")
						.parseLenient().appendOffset("+HH:MM", "Z").toFormatter()).replaceAll("Z", "") + "** " +
						"==============")
						.complete
						(true);
				msg.delete().complete();
				roleAssign.sendFile(new File(System.getProperty("user.dir") + "/welcome.png"), msg).complete
						(true).addReaction("👍").complete(true);
			}catch (RateLimitedException|IOException e){
				e.printStackTrace();
			}
			for (MessageEmbed emb : messagesToSend){
				try {
					Message msg = roleAssign.sendMessage(emb).complete(true);
					msg.addReaction("👍").complete(true);
				}catch (RateLimitedException e){
				}
			}

		}
		return new ReturnMessage(Color.WHITE, "Sent messages.");
	}

}
