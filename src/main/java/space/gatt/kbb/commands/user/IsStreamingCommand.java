package space.gatt.kbb.commands.user;

import com.google.gson.JsonElement;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import space.gatt.kbb.KingMain;
import space.gatt.kbb.annotations.*;
import space.gatt.kbb.commandmanager.ReturnMessage;
import space.gatt.kbb.streamtracker.StreamTracker;

import java.awt.*;

@Command("isstreaming")
@Syntax("isstreaming")
@Permissions(ranks = {"309931902612144128"}, ranksById = true)
@Usage("isstreaming")
@Description("Is the guy streaming?")
@Group("user")

public class IsStreamingCommand {

	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		StreamTracker tracker = KingMain.getStreamTracker();
		JsonElement check = tracker.getStreamData(args[0].replaceAll("#", "").toLowerCase());
		boolean isStreaming = tracker.isStreamLive(check);
		if (isStreaming) {
				return new ReturnMessage().setColor(Color.GREEN).setMessage(KingMain.getEmoteStorage().get("gasp")
								.getAsMention() + " I found Streaming data for that user! Here it is!\n" +
								"\n\n__**[Now Playing:]()**__ " + check.getAsJsonObject().get("game")
								.getAsString()
								+ "\n__**[Stream Title:]()**__ " + check.getAsJsonObject().get(
								"channel").getAsJsonObject().get("status").getAsString()
								+ "\n__**[Viewers:]()**__ " + check.getAsJsonObject().get("viewers")
								.getAsString())
								.setDisplayURL("https://twitch.tv/" + args[0]).setTitle("Twitch.TV Updates")
								.setImageURL(check.getAsJsonObject().get("preview").getAsJsonObject()
										.get("large").getAsString());

		}else{
			return new ReturnMessage().setColor(Color.RED).setMessage(KingMain.getEmoteStorage().get("gasp")
					.getAsMention() + " " + args[0] + " isn't streaming currently...");
		}
	}


}
