package space.gatt.kbb.commands.moderation;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import space.gatt.kbb.annotations.*;
import space.gatt.kbb.commandmanager.ReturnMessage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

@Command("updateroles")
@Syntax("updateroles")
@Usage("updateroles")
@Permissions(ranks = {"294441010077892608"}, ranksById = true)
@Description("Update roles")
@Group("moderation")
@CommandSettings(deleteInitatingMsg = true)
public class UpdateRolesMessageCommand {

	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		if (message.getTextChannel().getId().equalsIgnoreCase("302980774511247366")) {
			TextChannel roleAssign = message.getGuild().getTextChannelById("302459681421656065");
			List<Message> msgs = roleAssign.getHistory().retrievePast(50).complete();
			for (Message msg : msgs) {
				msg.delete().complete();
			}

			Role verifieduserRole = message.getGuild().getRolesByName("Verified Member", true).size() > 0 ?
					message.getGuild().getRolesByName("Verified Member", true).get(0) : message.getGuild().getRoleById
					("309931902612144128");

			ArrayList<MessageEmbed> messagesToSend = new ArrayList<>();

			ArrayList<Role> roles = new ArrayList<>();
			ArrayList<Role> raffles = new ArrayList<>();
			ArrayList<Role> submateRaffles = new ArrayList<>();
			for (Role r : message.getGuild().getRoles()) {
				if (!r.getName().contains("everyone")) {
					if (r.getPositionRaw() < verifieduserRole.getPositionRaw() && !r.getName().contains("RF: ")) {
						roles.add(r);
					}
					if (r.getName().contains("RF: ")) {
						if (r.getPositionRaw() < verifieduserRole.getPositionRaw()) {
							raffles.add(r);
						} else {
							submateRaffles.add(r);
						}
					}
				}
			}

			MessageEmbed intro = new EmbedBuilder().setColor(Color.WHITE).setDescription(
					"So, you've made your way here I see...\n" +
							"\n" +
							"Well, guess it's time to explain how to give yourself roles!\n" +
							"\n" +
							"Too grant yourself roles, you must be a `Verified Member`! If you are not a `Verified " +
							"Member`, then you'll recieve the Role after 10 minutes of being a part of the server!"
			).build();
			messagesToSend.add(intro);

			MessageEmbed ranksMessages = new EmbedBuilder().setColor(Color.WHITE).setDescription(
					"**Below is a list of roles you can opt-in or opt-out of using the commands `_optin <Role>` and " +
							"`_optout <Role>`**"
			).build();
			messagesToSend.add(ranksMessages);

			if (roles.size() > 0) {
				for (Role rank : roles) {
					MessageEmbed roleMsg = new EmbedBuilder().setColor(rank.getColor()).setDescription(
							"**" + rank.getName()
									+ "**"
					).build();
					messagesToSend.add(roleMsg);
				}
			} else {
				MessageEmbed roleMsg = new EmbedBuilder().setColor(Color.RED).setDescription(
						"**There are currently no roles that you can Opt-In or Out from!~**"
				).build();
				messagesToSend.add(roleMsg);
			}

			MessageEmbed raffleMessages = new EmbedBuilder().setColor(Color.WHITE).setDescription(
					"**Below is a list of Raffles that EVERYONE can join using the commands " +
							"`_rafflejoin " +
							"<RaffleName>` and leave using  `_raffleleave <RaffleName>`**"
			).build();
			messagesToSend.add(raffleMessages);

			if (raffles.size() > 0) {
				for (Role rank : raffles) {
					MessageEmbed roleMsg = new EmbedBuilder().setColor(rank.getColor()).setDescription("**" + rank
							.getName().replaceAll("RF: ", "")
							+ "**"
					).build();
					messagesToSend.add(roleMsg);
				}
			} else {
				MessageEmbed roleMsg = new EmbedBuilder().setColor(Color.RED).setDescription(
						"**There are currently no raffles going on right now!~**"
				).build();
				messagesToSend.add(roleMsg);
			}

			raffleMessages = new EmbedBuilder().setColor(Color.WHITE).setDescription(
					"**Below is a list of Raffles that SUB-MATES can join using the commands " +
							"`_rafflejoin <RaffleName>` and leave using  `_raffleleave <RaffleName>`**"
			).build();
			messagesToSend.add(raffleMessages);

			if (submateRaffles.size() > 0) {
				for (Role rank : submateRaffles) {
					MessageEmbed roleMsg = new EmbedBuilder().setColor(rank.getColor()).setDescription("**" + rank
							.getName().replaceAll("RF: ", "")
							+ "**"
					).build();
					messagesToSend.add(roleMsg);
				}
			} else {
				MessageEmbed roleMsg = new EmbedBuilder().setColor(Color.RED).setDescription(
						"**There are currently no Sub-Mate-exclusive raffles going on right now!~**"
				).build();
				messagesToSend.add(roleMsg);
			}

			try {
				Message msg = roleAssign.sendMessage("==============** Last Updated : " + message.getCreationTime
						().format(new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy HH:mm")
						.parseLenient().appendOffset("+HH:MM", "Z").toFormatter()).replaceAll("Z",
						"") + "** ==============").complete(true);
				msg.delete().complete();
				roleAssign.sendFile(new File(System.getProperty("user.dir") + "/roleassign.png"), msg).complete
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
