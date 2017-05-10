package space.gatt.kbb.commands.moderation;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import space.gatt.kbb.annotations.*;
import space.gatt.kbb.commandmanager.ReturnMessage;

import java.awt.*;

@Command("lalala")
@Syntax("lalala")
@Permissions(ranks = {"This role doesn't exist"})
@Usage("lalala")
@Description("Lalala!!")
@Group("Lalala")
public class NobodyCanUseThisCommand {

	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		return new ReturnMessage(Color.WHITE, "Lala- wait what.");
	}
}
