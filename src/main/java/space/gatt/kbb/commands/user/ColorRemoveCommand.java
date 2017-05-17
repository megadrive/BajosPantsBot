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

@Command("removecolour")
@Syntax("removecolour <ColorRole>")
@Permissions(ranks = {"309931902612144128"}, ranksById = true)
@Usage("removecolour")
@Description("Remove's a colour from any user")
@Group("Sub-Mate")
@CommandSettings(sendResponseViaPM = false)
public class ColorRemoveCommand {

	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		String combinedArgs = KingMain.getCmdMan().combineArguments(args);
		String color = "C: " + combinedArgs;
		ReturnMessage returnMessage = new ReturnMessage();

		if (message.getTextChannel().getId().equalsIgnoreCase(KingMain.getColorAssignChannel().getId())) {
			try {

				Role foundRole = (message.getGuild().getRolesByName(color, true).size() > 0) ? message.getGuild()
						.getRolesByName(color, true).get(0) : null;

				if (foundRole != null) {
					if (user.getRoles().contains(foundRole)) {
						message.getGuild().getController().removeRolesFromMember(user, foundRole).complete(true);
						returnMessage.setMessage("The `" + foundRole.getName().replaceAll("C: ", "") + "` colour has been removed from you!");
						returnMessage.setColor(Color.GREEN);
						return returnMessage;
					} else {
						returnMessage.setMessage(":broken_heart: You already have that colour...");
						returnMessage.setColor(Color.RED);
					}
				} else {
					returnMessage.setMessage(":broken_heart: I couldn't find that colour... (Tried " +
							"searching for " + color + ")");
					returnMessage.setColor(Color.BLUE);
				}

			} catch (RateLimitedException | PermissionException e) {
				returnMessage.setMessage(":broken_heart: I can't give you that colour. Sorry~");
				returnMessage.setColor(Color.RED);
			}
		} else {
			return null;
		}
		return returnMessage;
	}
}
