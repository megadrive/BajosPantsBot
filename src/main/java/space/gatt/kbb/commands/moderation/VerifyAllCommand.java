package space.gatt.kbb.commands.moderation;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import space.gatt.kbb.KingMain;
import space.gatt.kbb.annotations.*;
import space.gatt.kbb.commandmanager.ReturnMessage;

import java.awt.*;

@Command("fixchannels")
@Syntax("verifyall")
@Usage("verifyall")
@Permissions(ranks = {"294441010077892608"}, ranksById = true)
@Description("Verify me please daddy")
@Group("user")
@CommandSettings(deleteInitatingMsg = true, sendResponseViaPM = false, requiresPM = false)

public class VerifyAllCommand {

	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		if (message.getTextChannel().getId().equalsIgnoreCase("302980774511247366")) {

			try {
				for (TextChannel chnl : message.getGuild().getTextChannels()) {
					if (!chnl.getName().contains("moderation") && !chnl.getName().contains("admin") && !chnl.getName
							().contains("boll-debug")&& !chnl.getName
							().contains("welcome")) {
						System.out.println(chnl.getName());
						if (chnl.getPermissionOverride(KingMain.getVerifiedMember()) == null) {
							chnl.createPermissionOverride(KingMain.getVerifiedMember()).complete()
									.getManagerUpdatable().grant(Permission.MESSAGE_READ).update().complete();
						}
					}
				}
			}catch (Exception e){
				e.printStackTrace();
			}
			return null;/*

			Role verifieduserRole = message.getGuild().getRolesByName("Verified Member", false).size() > 0 ?
					message.getGuild().getRolesByName("Verified Member", false).get(0) : message.getGuild().getRoleById
					("309931902612144128");
			ReturnMessage returnMessage = new ReturnMessage(Color.WHITE, "Oh no...");
			for (Member m : message.getGuild().getMembers()){
				if (!KingMain.getCmdMan().hasRole(m, "Verified Member")){
					message.getGuild().getController().addRolesToMember(m, verifieduserRole).queue();
				}
			}
			returnMessage.setMessage("Granting roles...");
			return returnMessage;*/
		}else{
			return null;
		}

	}

}
