package space.gatt.kbb.commands.user;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import space.gatt.kbb.KingMain;
import space.gatt.kbb.annotations.*;
import space.gatt.kbb.commandmanager.ReturnMessage;

import java.util.List;


@Command("whois")
@Syntax("whois")
@Permissions(ranks = {"309931902612144128"}, ranksById = true)
@Usage("whois")
@Description("Who is he?")
@Group("user")
public class WhoIsCommand {

	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {

		Member targetUser = user;
		if (message.getMentionedUsers().size() > 0){
			targetUser = message.getGuild().getMember(message.getMentionedUsers().get(0));
		}

		if (args.length > 0 && message.getMentionedUsers().size() == 0){
			String userToFind = KingMain.getCmdMan().combineArguments(args);

			List<Member> scanned = message.getGuild().getMembersByEffectiveName(userToFind, true);
			if (scanned.size() > 0){
				targetUser = scanned.get(0);
			}else{
				scanned = message.getGuild().getMembersByNickname(userToFind, true);
				if (scanned.size() > 0){
					targetUser = scanned.get(0);
				}else{
					scanned = message.getGuild().getMembersByName(userToFind, true);
					if (scanned.size() > 0){
						targetUser = scanned.get(0);
					}
				}
			}
		}

		StringBuilder display = new StringBuilder();

		display.append("**__[Nickname:]()__** " + (targetUser.getNickname() != null ? targetUser.getNickname() :
				"No Nickname") +
				"\n");
		display.append("\n");
		display.append("**__[ID:]()__** " + targetUser.getUser().getId() + "\n");
		display.append("\n");
		display.append("**__[Roles:]()__**\n");
		for (Role r : targetUser.getRoles()) {
			display.append("  __" + r.getName() + "__\n");
		}

		EmbedBuilder builder = new EmbedBuilder();
		builder.setThumbnail(targetUser.getUser().getAvatarUrl());
		builder.setColor(targetUser.getColor());
		builder.addField("User Information for " + targetUser.getUser().getName() + "#" + targetUser.getUser().getDiscriminator(),
				display.toString(),
				true);

		try {
			message.getTextChannel().sendMessage(builder.build()).complete(true);
		}catch (RateLimitedException e){

		}
		return null;
	}
}
