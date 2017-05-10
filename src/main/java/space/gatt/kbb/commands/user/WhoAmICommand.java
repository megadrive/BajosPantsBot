package space.gatt.kbb.commands.user;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import space.gatt.kbb.annotations.*;
import space.gatt.kbb.commandmanager.ReturnMessage;

import java.awt.*;
@Command("whoami")
@Syntax("whoami")
@Permissions(ranks = {"309931902612144128"}, ranksById = true)
@Usage("whoami")
@Description("Who... Who am I?")
@Group("user")
public class WhoAmICommand {
	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("getroles")) {
				ReturnMessage msg = new ReturnMessage(Color.WHITE, "Roles: \n");
				for (Role r : user.getRoles()) {
					msg.setMessage(msg.getMessage() + "" + r.getName() + " (ID: " + r.getId() + ")\n");
				}
				return msg;
			}
		}
		if (user.getUser().getId().equalsIgnoreCase("113462564217683968")) {
			return new ReturnMessage(Color.PINK, "You're Gatt! Duh. Everyone knows you!");
		} else if (user.getUser().getId().equalsIgnoreCase("162849396088766465")) {
			return new ReturnMessage(Color.YELLOW,
					"Ba-BA-BAJO!? Well it's- it's a pleasure to talk to you! I mean, " +
							"I've been on your glorious bottom for years now!");
		}
		return new ReturnMessage(Color.WHITE, "I'm not sure who you are to be honest... But you know who you're " +

				"not? Me.");
	}
}
