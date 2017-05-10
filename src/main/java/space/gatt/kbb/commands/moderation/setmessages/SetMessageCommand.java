package space.gatt.kbb.commands.moderation.setmessages;

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

@Command("setmessage")
@Syntax("setmessage [type] [Message]")
@Permissions(ranks = {"294441010077892608"}, ranksById = true)
@Usage("setmessage")
@Description("Sets a message")
@Group("user")
public class SetMessageCommand {

	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		if (args.length < 2){
			return new ReturnMessage().setColor(Color.YELLOW).setTitle("Set Message help!")
					.setMessage("`Usage: ` _setmessage [Rules|Info|Welcome|Verify] {Updated Message Here}");
		}else{
			String type = args[0];
			if (type.toLowerCase().equalsIgnoreCase("rules") ||
					type.toLowerCase().equalsIgnoreCase("info") ||
					type.toLowerCase().equalsIgnoreCase("welcome") ||
					type.toLowerCase().equalsIgnoreCase("verify")){
				args[0] = "";
				String messageToSet = KingMain.getCmdMan().combineArguments(args).trim();
				KingMain.setMessage(type, messageToSet);
				return new ReturnMessage().setColor(Color.GREEN).setTitle("Updated the message for " + type)
						.setMessage("__[Set the message to:]()__\n\n" + messageToSet);
			}
		}
		return new ReturnMessage().setColor(Color.YELLOW).setTitle("Set Message help!")
				.setMessage("`Usage: ` _setmessage [Rules|Info|Welcome|Verify] {Updated Message Here}");
	}

}
