package space.gatt.kbb.commandmanager;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import space.gatt.kbb.KingMain;
import space.gatt.kbb.Settings;
import space.gatt.kbb.annotations.CommandSettings;
import space.gatt.kbb.annotations.Permissions;

import java.awt.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

public class CommandListener extends ListenerAdapter {
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		Message message = event.getMessage();
		String[] args = message.getContent().split(" ");
		boolean isCommandStarter = false;
		if (message.getContent().startsWith(Settings.getCommandStarter())) {
			args[0] = args[0].replaceFirst(Settings.getCommandStarter(), "").toLowerCase();
			isCommandStarter = true;
		}
		String cmd = args[0];
		if (KingMain.getCmdMan().getCommandList().contains(cmd) && isCommandStarter) {
			Class<?> enclosingClass = KingMain.getCmdMan().getCommandRegistrar().get(cmd);
			args = Arrays.copyOfRange(args, 1, args.length);
			if (enclosingClass != null) {
				boolean adminOnly = false;
				boolean deleteMsg = false;
				boolean sendPM = false;
				boolean requiresPM = false;
				boolean ranksById = false;
				String[] ranks = new String[]{};

				for (Annotation a : enclosingClass.getAnnotations()) {
					if (a instanceof Permissions) {
						ranks = ((Permissions) a).ranks();
						adminOnly = ((Permissions) a).adminOnly();
						ranksById = ((Permissions) a).ranksById();
					}
					if (a instanceof CommandSettings) {
						deleteMsg = ((CommandSettings) a).deleteInitatingMsg();
						sendPM = ((CommandSettings) a).sendResponseViaPM();
						requiresPM = ((CommandSettings) a).requiresPM();
					}
				}

				if (requiresPM) {
					if (!message.isFromType(ChannelType.PRIVATE)) {
						return;
					}
				}

				if (deleteMsg) {
					try {
						message.delete().complete(true);
					} catch (RateLimitedException e) {
					}
				}

				if (adminOnly) {
					if (!(KingMain.getCmdMan().getAdminusers().contains(message.getAuthor().getId()))) {
						String reply = Settings.getMsgStarter() + "You are not one on my Admin List! Sorry!";
						if (sendPM) {
							try {
								message.getAuthor().getPrivateChannel().sendMessage(reply).complete(true);
							} catch (RateLimitedException e) {
							}
						} else {
							KingMain.getMsgMan().sendMessage(message.getTextChannel(), reply);
						}
						return;
					}
				}
				if (ranks.length > 0 && !ranks[0].equals("null")) {
					for (String rank : ranks) {
						boolean hasRank = !ranksById ? KingMain.getCmdMan().hasRole(event.getMember(), rank, true) :
								KingMain.getCmdMan().hasRoleById(event.getMember(), rank);
						if (!hasRank) {
							String reply = Settings.getMsgStarter() + "You do not have one of the following ranks:";
							for (String r : ranks) {
								if (!ranksById) {
									reply = reply + " `" + r + "`";
								} else {
									reply = reply + " `" + message.getGuild().getRoleById(r).getName() + "`";
								}
							}
							if (sendPM) {
								try {
									message.getAuthor().getPrivateChannel().sendMessage(reply).complete(true);
								} catch (RateLimitedException e) {
								}
							} else {
								KingMain.getMsgMan().sendMessage(message.getTextChannel(), reply);
							}
							return;
						}
					}
				}

				Method method;

				Class<?> clz = KingMain.getCmdMan().getCommandRegistrar().get(cmd);
				String methodName = KingMain.getCmdMan().getMethodRegistrar().get(cmd).getName();

				ReturnMessage returnMessage = null;
				try {
					method = clz.getDeclaredMethod(methodName, Message.class, Member.class, String[].class);
					Object value = method.invoke(this, message, event.getMember(), args);
					if (value instanceof ReturnMessage) {
						returnMessage = ((ReturnMessage) value);
					} else if (value instanceof String) {
						returnMessage = new ReturnMessage(Color.WHITE, (String) value);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (returnMessage != null) {
					if (sendPM) {
						KingMain.getMsgMan().sendMessage(event.getMember(), returnMessage);
					} else {
						KingMain.getMsgMan().sendMessage(message.getTextChannel(), returnMessage);
					}
				}
			}
		}

	}
}
