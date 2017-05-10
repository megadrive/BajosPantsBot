package space.gatt.kbb.commands.moderation;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import space.gatt.kbb.KingMain;
import space.gatt.kbb.annotations.*;
import space.gatt.kbb.commandmanager.ReturnMessage;

import java.awt.*;
import java.util.Random;

@Command("cyclecolour")
@Syntax("cyclecolour")
@Permissions(ranks = {"294441010077892608"}, ranksById = true)
@Usage("cyclecolour")
@Description("Change the Cycle Colour's Colour...")
@Group("user")
public class CycleColourCommand {

	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		Random rnd = new Random();
		int r = rnd.nextInt(256);
		rnd.nextInt();
		int g = rnd.nextInt(256);
		rnd.nextInt();
		int b = rnd.nextInt(256);
		rnd.nextInt();
		Color c = new Color(r, g, b);
		KingMain.getCycleColor().getManager().setColor(c).complete();
		return new ReturnMessage(c, "Ok, let's switch it up a bit.");
	}
}
