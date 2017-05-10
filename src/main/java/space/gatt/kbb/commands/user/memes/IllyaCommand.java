package space.gatt.kbb.commands.user.memes;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import space.gatt.kbb.annotations.*;
import space.gatt.kbb.commandmanager.ReturnMessage;

import java.io.File;
import java.io.IOException;

@Command("qt")
@Syntax("qt")
@Permissions(ranks = {"309931902612144128"}, ranksById = true)
@Usage("qt")
@Description("IT'S A TRAP!")
@Group("user")
public class IllyaCommand {
	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		try {
			message.getChannel().sendFile(new File(System.getProperty("user.dir") + "/illya.gif"), null)
					.complete
					(true);
		}catch (RateLimitedException|IOException e){
			e.printStackTrace();
		}
		return null;
	}
}
