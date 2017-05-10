package space.gatt.kbb.commands.user;

import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import space.gatt.kbb.KingMain;
import space.gatt.kbb.annotations.*;
import space.gatt.kbb.commandmanager.ReturnMessage;

import java.util.Random;

@Command("serverinfo")
@Syntax("serverinfo")
@Permissions(ranks = {"309931902612144128"}, ranksById = true)
@Usage("serverinfo")
@Description("Get that Server Information")
@Group("user")
public class ServerInfoCommand {
	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		StringBuilder bldr = new StringBuilder();
		Random generator = new Random();
		Emote e1 = KingMain.getBajoGuild().getEmotes().get(generator.nextInt(KingMain.getBajoGuild().getEmotes().size()));
		Emote e2 = KingMain.getBajoGuild().getEmotes().get(generator.nextInt(KingMain.getBajoGuild().getEmotes().size()));
		bldr.append("╔═════════╝" + e1.getAsMention() + "╚═════════╗\n");
		bldr.append("║ **[Server Name:]()** __" + message.getGuild().getName() + "__\n");
		bldr.append("║ \n");
		bldr.append("║ **[User Count:]()** __" + message.getGuild().getMembers().size() + "__\n");
		bldr.append("║ \n");
		bldr.append("║ **[Role Count:]()** __" + message.getGuild().getRoles().size() + "__\n");
		bldr.append("║ \n");
		bldr.append("║ **[Owner:]()** __" + message.getGuild().getOwner().getEffectiveName() + "__\n");
		bldr.append("║ \n");
		bldr.append("║ **[Emote Count:]()** __" + message.getGuild().getEmotes().size() + "__\n");
		bldr.append("╚═════════╗" + e2.getAsMention() + "╔═════════╝");

		return new ReturnMessage().setColor(user.getColor()).setMessage(bldr.toString()).setThumbnailURL(message
				.getGuild()
				.getIconUrl()).setTitle("Server Information");
	}
}
