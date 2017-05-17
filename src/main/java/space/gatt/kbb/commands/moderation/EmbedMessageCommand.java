package space.gatt.kbb.commands.moderation;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import space.gatt.kbb.KingMain;
import space.gatt.kbb.annotations.*;
import space.gatt.kbb.commandmanager.ReturnMessage;

import java.awt.*;
import java.util.Random;

@Command("embedmsg")
@Syntax("embedmsg color||title||msg")
@Permissions(ranks = {"294441010077892608"}, ranksById = true)
@Usage("embedmsg")
@Description("Send an Embedded Message")
@Group("moderation")
public class EmbedMessageCommand {

	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		String[] content = KingMain.getCmdMan().combineArguments(args).split("\\|\\|");
		if (content.length < 3) {
			return new ReturnMessage(Color.RED, "Not enough arguments!");
		}
		Random r = new Random();
		Color clr = new Color(r.nextInt(255), r
				.nextInt(255), r.nextInt(255));
		try {
			if (Color.decode(content[0]) != null) {
				clr = Color.decode(content[0]);
			}
		} catch (NumberFormatException e) {
			clr = new Color(r.nextInt(255), r
					.nextInt(255), r.nextInt(255));
		}
		String title = content[1];
		String msg = content[2];

		MessageEmbed msgEm = new EmbedBuilder().addField(title, msg, true).setColor(clr).build();
		try {
			message.getTextChannel().sendMessage(msgEm).complete(true);
		} catch (RateLimitedException e) {
		}
		return null;
	}
}
