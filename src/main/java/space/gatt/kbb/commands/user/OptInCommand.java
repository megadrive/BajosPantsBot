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

@Command("optin")
@Syntax("optin <Role>")
@Permissions(ranks = {"309931902612144128"}, ranksById = true)
@Usage("optin")
@Description("Opt-In to a role")
@Group("user")
@CommandSettings(sendResponseViaPM = false, requiresPM = false)
public class OptInCommand {

	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		String role = KingMain.getCmdMan().combineArguments(args);
		ReturnMessage returnMessage = new ReturnMessage(Color.WHITE, "");
		if (message.getTextChannel().getId().equalsIgnoreCase("302459681421656065")) {
			try {
				Role verifieduserRole = KingMain.getVerifiedMember();
				Role foundRole = (message.getGuild().getRolesByName(role, true).size() > 0) ? message.getGuild()
						.getRolesByName(role, true).get(0) : null;
				if (foundRole != null) {
					if (foundRole.getPositionRaw() < verifieduserRole.getPositionRaw()) {
						if (!user.getRoles().contains(foundRole)) {
							message.getGuild().getController().addRolesToMember(user, foundRole).complete(true);
							returnMessage.setMessage("You've been given the `" + foundRole.getName() + "` role!");
							returnMessage.setColor(Color.GREEN);
						} else {
							returnMessage.setMessage(":broken_heart: You already have that role...");
							returnMessage.setColor(Color.RED);
						}
					} else {
						returnMessage.setMessage(":broken_heart: That is not a role available to you!");
						returnMessage.setColor(Color.RED);
					}
				} else {
					returnMessage.setMessage(":broken_heart: I couldn't find that role... (Tried " +
							"searching for " + role + ")");
					returnMessage.setColor(Color.BLUE);
				}
			} catch (RateLimitedException | PermissionException e) {
				returnMessage.setMessage(":broken_heart: I can't give you that role. Sorry~");
				returnMessage.setColor(Color.RED);
			}
		}else{
			return null;
		}
		return returnMessage;
	}
}
