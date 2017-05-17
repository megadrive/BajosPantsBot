package space.gatt.kbb.commands.user;

import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import space.gatt.kbb.KingMain;
import space.gatt.kbb.annotations.*;
import space.gatt.kbb.commandmanager.ReturnMessage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

@Command("changepants")
@Syntax("changepants")
@Permissions(ranks = {"309931902612144128"}, ranksById = true)
@Usage("changepants")
@Description("Change the Pants!")
@Group("user")
public class ChangePantsCommand {

	private static File currentPants = null;

	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		File pantsFolder = new File(System.getProperty("user.dir") + "/avatars/");
		try {
			if (pantsFolder.listFiles().length > 0) {
				if (currentPants == null) {
					currentPants = pantsFolder.listFiles()[new Random().nextInt(pantsFolder.listFiles().length)];
				} else {
					File targetPants = pantsFolder.listFiles()[new Random().nextInt(pantsFolder.listFiles().length)];
					while (currentPants == targetPants) {
						targetPants = pantsFolder.listFiles()[new Random().nextInt(pantsFolder.listFiles().length)];
					}
					currentPants = targetPants;
				}
				KingMain.getSelfuser().getManager().setAvatar(Icon.from(currentPants)).complete(false);
			}

		} catch (ErrorResponseException e) {
			KingMain.getMsgMan().sendMessage(message.getTextChannel(),
					new ReturnMessage(Color.RED, "Please wait a bit " +
							"before changing who I am T_T' (ERE)"));
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			KingMain.getMsgMan().sendMessage(message.getTextChannel(),
					new ReturnMessage(Color.RED, "Please wait a bit " +
							"before changing who I am T_T' (IOE)"));
			e.printStackTrace();
			return null;
		} catch (RateLimitedException e) {
			KingMain.getMsgMan().sendMessage(message.getTextChannel(),
					new ReturnMessage(Color.RED, "Please wait a bit " +
							"before changing who I am T_T' (RLE)"));
			e.printStackTrace();
			return null;

		} catch (NullPointerException e) {
			KingMain.getMsgMan().sendMessage(message.getTextChannel(),
					new ReturnMessage(Color.RED, "Please wait a bit " +
							"before changing who I am T_T' (NPE)"));
			e.printStackTrace();
			return null;
		}
		return new ReturnMessage(Color.WHITE, "Ok, let's switch it up a bit.");
	}
}
