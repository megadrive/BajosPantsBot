package space.gatt.kbb.commands.user;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import space.gatt.kbb.KingMain;
import space.gatt.kbb.annotations.*;
import space.gatt.kbb.commandmanager.ReturnMessage;

import java.awt.*;

@Command("verifyme")
@Syntax("verifyme")
@Usage("verifyme")
@Description("Verify me please daddy")
@Group("user")
@CommandSettings(deleteInitatingMsg = false, sendResponseViaPM = false, requiresPM = false)

public class VerifyUserCommand {

	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		if (message.getTextChannel().getId().equalsIgnoreCase("309932250114293761")) {
			Role verifieduserRole = message.getGuild().getRolesByName("Verified Member", true).size() > 0 ?
					message.getGuild().getRolesByName("Verified Member", true).get(0) : message.getGuild().getRoleById
					("309931902612144128");
			ReturnMessage returnMessage = new ReturnMessage().setColor(Color.RED).setMessage("Oh no...");
			if (!KingMain.getCmdMan().hasRole(user, "Verified Member", false)) {
				try {
					message.delete().complete(true);
					message.getGuild().getController().addRolesToMember(user, verifieduserRole).complete(true);
				} catch (PermissionException | RateLimitedException e) {
					returnMessage.setColor(Color.RED);
					returnMessage.setMessage("Something went wrong... Try again in 5 seconds!");
					return returnMessage;
				}
				returnMessage.setColor(Color.GREEN);
				returnMessage.setMessage("Well done! Welcome to the club " + user.getEffectiveName() + "! " +
						KingMain.getEmoteStorage().get("bajoKisses").getAsMention());
			} else {
				returnMessage.setColor(Color.RED);
				returnMessage.setMessage("You're already verified!");
			}
			return returnMessage;
		} else {
			return null;
		}

	}

}
