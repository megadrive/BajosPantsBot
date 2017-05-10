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
@Permissions(ranks = {"299549151945818112"}, ranksById = true)
@Usage("removecolour")
@Description("Remove's a colour from a Sub-Mate")
@Group("Sub-Mate")
@CommandSettings( sendResponseViaPM = false)
public class ColorRemoveCommand {

	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		String combinedArgs = KingMain.getCmdMan().combineArguments(args);
		String color = "C: " + combinedArgs;
		ReturnMessage returnMessage = new ReturnMessage(Color.WHITE, "");
		if (message.getTextChannel().getId().equalsIgnoreCase(KingMain.getColorAssignChannel().getId())) {
			try {
				Role foundRole = (message.getGuild().getRolesByName(color, true).size() > 0) ? message.getGuild()
						.getRolesByName(color, true).get(0) : null;
				if (foundRole != null) {
					if (foundRole.getPositionRaw() < KingMain.getSubMateRole().getPositionRaw()) {
						if (user.getRoles().contains(foundRole)) {
							message.getGuild().getController().removeRolesFromMember(user, foundRole).complete(true);
							returnMessage.setMessage("The `" + foundRole.getName() + "` colour has been taken!");
							returnMessage.setColor(Color.GREEN);
						} else {
							returnMessage.setMessage(":broken_heart: You don't have that colour...");
							returnMessage.setColor(Color.RED);
						}
					} else {
						returnMessage.setMessage(":broken_heart: That is not a role available to you!");
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
		}else{
			return null;
		}
		return returnMessage;
	}
}