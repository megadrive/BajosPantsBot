package space.gatt.kbb.commands.user.memes;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import space.gatt.kbb.KingMain;
import space.gatt.kbb.annotations.*;
import space.gatt.kbb.commandmanager.ReturnMessage;

import java.awt.*;

@Command("ttb")
@Syntax("ttb")
@Permissions(ranks = {"309931902612144128"}, ranksById = true)
@Usage("ttb")
@Description("Brick em")
@Group("user")
public class TextToBrickCommand {
	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		if (args.length <= 0) {
			return new ReturnMessage().setColor(Color.RED).setMessage("Please provide a query.");
		}
		StringBuilder sb = new StringBuilder();
		for (String a : KingMain.getCmdMan().combineArguments(args).split("")) {
			if (Character.isLetter(a.toLowerCase().charAt(0))) {
				sb.append(":regional_indicator_").append(a.toLowerCase()).append(":");
			} else {
				if (a.equals(" ")) {
					sb.append(" ");
				}
				sb.append(a);
			}
		}
		return new ReturnMessage().setColor(Color.GREEN).setTitle(user.getEffectiveName() + "'s Text to Brick Message" +
				".").setMessage(sb.toString());
	}
}
