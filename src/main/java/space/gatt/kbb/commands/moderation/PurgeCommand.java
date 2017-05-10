package space.gatt.kbb.commands.moderation;

import net.dv8tion.jda.core.entities.*;
import space.gatt.kbb.annotations.*;
import space.gatt.kbb.commandmanager.ReturnMessage;

import java.awt.*;
import java.util.List;

@Command("purge")
@Syntax("purge <amount>")
@Usage("purge <amount>")
@Permissions(ranks = {"294441010077892608"}, ranksById = true)
@Description("Purge a specified amount of messages")
@Group("moderation")
@CommandSettings(deleteInitatingMsg = true)
public class PurgeCommand {

	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		MessageHistory history = message.getChannel().getHistory();

		try {
			int amount = Integer.parseInt(args[0]);
			amount = Math.min(amount, 100);

			if (amount < 2) {
				return new ReturnMessage(Color.RED, "You need to delete 2 or more messages to use this command.");
			}

			List<Message> msgs = history.retrievePast(amount).complete();
			message.getTextChannel().deleteMessages(msgs).queue();
			return new ReturnMessage(Color.RED,
					"Attempted to delete **[" + msgs.size() + "]()** messages from this channel. (I'm probably " +
							"rate-limited now...)");
		}catch (NumberFormatException e) {
			return new ReturnMessage(Color.RED,"Improper arguments supplies, must be a number.");
		}
	}
}
