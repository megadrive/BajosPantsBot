package space.gatt.kbb.commands.moderation;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
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

@Command("updatecolours")
@Syntax("updatecolours")
@Usage("updatecolours")
@Permissions(ranks = {"294441010077892608"}, ranksById = true)
@Description("Update colours")
@Group("moderation")
@CommandSettings(deleteInitatingMsg = true)
public class UpdateColorsMessageCommand {

	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		if (message.getTextChannel().getId().equalsIgnoreCase("302980774511247366")) {
			TextChannel roleAssign = KingMain.getColorAssignChannel();
			List<Message> msgs = roleAssign.getHistory().retrievePast(50).complete();
			for (Message msg : msgs) {
				msg.delete().complete();
			}

			Role verifieduserRole = KingMain.getSubMateRole();

			ArrayList<MessageEmbed> messagesToSend = new ArrayList<>();

			ArrayList<Role> roles = new ArrayList<>();
			for (Role r : message.getGuild().getRoles()) {
				if (!r.getName().contains("everyone")) {
					if (r.getPositionRaw() < verifieduserRole.getPositionRaw() && r.getName().startsWith("C: ")) {
						roles.add(r);
					}
				}
			}

			MessageEmbed intro = new EmbedBuilder().setColor(Color.WHITE).setDescription(
					"Are you a **Sub-Mate**?! Then you can assign yourself a colour here!\n" +
							"Simply use the command `_addcolour <ColourName>` and you'll be given the colour!\n" +
							"\n" +
							"To remove a colour use `_removecolour <ColourName>`\n\n" +
							"**Below is a list of colours you can give yourself using the commands**"
			).build();
			messagesToSend.add(intro);

			if (roles.size() > 0) {
				for (Role rank : roles) {
					MessageEmbed roleMsg = new EmbedBuilder().setColor(rank.getColor()).setDescription(
							"**" + rank.getName().replaceAll("C: ", "") + "**"
					).build();
					messagesToSend.add(roleMsg);
				}
			} else {
				MessageEmbed roleMsg = new EmbedBuilder().setColor(Color.RED).setDescription(
						"**There are currently no colours that you can give yourself!~**"
				).build();
				messagesToSend.add(roleMsg);
			}

			try {
				Message msg = roleAssign.sendMessage("==============** Last Updated : " + message.getCreationTime
						().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy HH:mm")
						.parseLenient().appendOffset("+HH:MM", "Z").toFormatter()).replaceAll("Z", "") + "** " +
						"==============")
						.complete
								(true);
				msg.delete().complete();
				roleAssign.sendFile(new File(System.getProperty("user.dir") + "/colorassign.png"), msg).complete
						(true).addReaction("👍").complete(true);
			} catch (RateLimitedException | IOException e) {
				e.printStackTrace();
			}
			for (MessageEmbed emb : messagesToSend) {
				try {
					Message msg = roleAssign.sendMessage(emb).complete(true);
					msg.addReaction("👍").complete(true);
				} catch (RateLimitedException e) {
				}
			}

		}
		return new ReturnMessage(Color.WHITE, "Sent messages.");
	}

}
