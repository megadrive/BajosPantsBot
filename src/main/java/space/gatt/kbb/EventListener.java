package space.gatt.kbb;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceSelfDeafenEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import space.gatt.kbb.commandmanager.ReturnMessage;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class EventListener extends ListenerAdapter {
	private static Date lastThanked = null;
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		if (event.getTextChannel().getId().equalsIgnoreCase(KingMain.getColorAssignChannel().getId())
				|| event.getTextChannel().getId().equalsIgnoreCase("302459681421656065") ||
				event.getTextChannel().getId().equalsIgnoreCase("309932250114293761")) {
			KingMain.getScheduler().schedule(() -> {
				try {
					if (event.getTextChannel().getMessageById(event.getMessage().getId()).complete(true).getReactions()
							.size() > 0){
						event.getTextChannel().getMessageById(event.getMessage().getId()).complete(true)
								.clearReactions().complete(true);
						return;
					}
					event.getMessage().delete().complete(true);
				} catch (RateLimitedException e) {
				}
			}, 5, TimeUnit.SECONDS);
		}

		if (!event.getMember().getRoles().contains(KingMain.getVerifiedMember()) &&(
				event.getTextChannel().getId().equalsIgnoreCase("290285351099039747")
			|| event.getTextChannel().getId().equalsIgnoreCase("311473256911732736")
			|| event.getTextChannel().getId().equalsIgnoreCase("311473902981480449"))){
			KingMain.getMsgMan().sendMessage(event.getMember(), Color.YELLOW, "Hey! I noticed you haven't verified " +
					"yourself yet! Head over to <#309932250114293761> and verify yourself to get access to cool " +
					"colours and custom roles!");
		}

		if (event.getMessage().getContent().toLowerCase().contains("thank") && event.getMessage().getContent().toLowerCase().contains("the")
				&&
				event.getMessage().getContent().toLowerCase().contains("maker")) {


			Message message = event.getMessage();
			Member user = event.getMember();

			Date cur = Calendar.getInstance(TimeZone.getTimeZone("Australia/Melbourne")).getTime();

			if (event.getMember().getUser().getId().equalsIgnoreCase("113462564217683968") && event.getMessage()
					.getContent().toLowerCase().contains("force")){

				Role thankedTheMaker = message.getGuild().getRoleById("311447038279811076");

				KingMain.getMsgMan().sendMessage(event.getTextChannel(), new ReturnMessage().setColor
						(Color.YELLOW).setTitle("THANK THE MAKER").setThumbnailURL
								("https://cdn.discordapp.com/emojis/311466093329776641.png").setMessage(
				KingMain.getEmoteStorage().get("thankthemaker").getAsMention() + user.getEffectiveName() + " cheated " +
						"and **Thanked the Maker**!" + KingMain.getEmoteStorage().get("thankthemaker").getAsMention()));

				for (Member u : message.getGuild().getMembersWithRoles(thankedTheMaker)) {
					message.getGuild().getController().removeRolesFromMember(u, thankedTheMaker).complete();
				}

				message.getGuild().getController().addRolesToMember(user, thankedTheMaker).complete();
				return;
			}

			if (cur.getMinutes() == 17) {
				Role thankedTheMaker = message.getGuild().getRoleById("309993171939753984");
				if (lastThanked == null || (lastThanked.before(cur) && !(lastThanked.getHours() == cur.getHours() &&
						lastThanked.getDay() == cur.getDay()))) {
					lastThanked = cur;


					KingMain.getMsgMan().sendMessage(event.getTextChannel(), new ReturnMessage().setColor(Color.YELLOW)
							.setTitle
									("THANK THE " +
											"MAKER")
							.setThumbnailURL
									("https://cdn.discordapp.com/emojis/311466093329776641.png").setMessage(
									KingMain.getEmoteStorage().get("thankthemaker").getAsMention() + user
											.getEffectiveName() + " was the first to **Thank the Maker** this hour! " +
											KingMain.getEmoteStorage().get("thankthemaker").getAsMention()));

					for (Member u : message.getGuild().getMembersWithRoles(thankedTheMaker)) {
						message.getGuild().getController().removeRolesFromMember(u, thankedTheMaker).complete();
					}

					message.getGuild().getController().addRolesToMember(user, thankedTheMaker).complete();
				} else {
					KingMain.getMsgMan().sendMessage(event.getTextChannel(),
							new ReturnMessage().setColor(Color.RED).setMessage("Someone beat you too it!"));
				}
			}
		}

	}

	@Override
	public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
		if (event.getChannelJoined().getUserLimit() > 0 && event.getVoiceState().isDeafened()){
			event.getMember().getGuild().getController().moveVoiceMember(event.getMember(), event.getGuild()
					.getVoiceChannelById("290288490128736257")).complete();
			KingMain.getMsgMan().sendMessage(event.getMember(), Color.RED, "You've been moved to the AFK Channel as " +
					"you joined a User-Limited voice chat with your speakers muted! *That wastes space and makes " +
					"people sad...*");
		}
	}

	@Override
	public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
		if (event.getChannelJoined().getUserLimit() > 0 && event.getVoiceState().isDeafened()){
			event.getMember().getGuild().getController().moveVoiceMember(event.getMember(), event.getGuild()
					.getVoiceChannelById("290288490128736257")).complete();
			KingMain.getMsgMan().sendMessage(event.getMember(), Color.RED, "You've been moved to the AFK Channel as " +
					"you joined a User-Limited voice chat with your speakers muted! *That wastes space and makes " +
					"people sad...*");
		}
	}

	@Override
	public void onGuildVoiceSelfDeafen(GuildVoiceSelfDeafenEvent event) {
		if (event.getMember().getVoiceState().getChannel().getUserLimit() > 0 && event.getVoiceState().isDeafened()){
			event.getMember().getGuild().getController().moveVoiceMember(event.getMember(), event.getGuild()
					.getVoiceChannelById("290288490128736257")).complete();
			KingMain.getMsgMan().sendMessage(event.getMember(), Color.RED, "You've been moved to the AFK Channel as " +
					"you're in a User-Limited voice chat with your speakers muted! *That wastes space and makes " +
					"people sad...*");
		}
	}

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		KingMain.getMsgMan().log(KingMain.getDebugChannel(), Color.GREEN, event.getMember().getEffectiveName() + " has " +
				"joined the server!");
		KingMain.getMsgMan().sendMessage(event.getGuild().getTextChannelById("290285351099039747"), Color.GREEN, KingMain
				.getEmoteStorage().get("omg").getAsMention() + " Welcome to the Bajo Community Discord, " + event.getMember()
				.getAsMention() + "!\n" +
				"Message any Mod-Mate your questions or queries.\n" +
				"Remember to read <#309672559237136385> and <#294443160581701632>! " + KingMain.getEmoteStorage().get("thanks").getAsMention());
	}


	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
		KingMain.getMsgMan().log(KingMain.getDebugChannel(), Color.BLUE, event.getMember().getEffectiveName() + " " +
				"(" + event.getMember().getUser().getName() + "#" + event.getMember().getUser().getDiscriminator() + ") has " +
				"left the server!");
	}

}
