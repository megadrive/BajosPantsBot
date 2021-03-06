package space.gatt.kbb;

import com.google.gson.JsonElement;
import net.dv8tion.jda.bot.JDABot;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import space.gatt.kbb.commandmanager.CommandListener;
import space.gatt.kbb.commandmanager.CommandManager;
import space.gatt.kbb.commandmanager.ReturnMessage;
import space.gatt.kbb.streamtracker.StreamTracker;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class KingMain {

	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private static SelfUser selfuser;
	private static JDABot botObject;
	private static Guild bajoGuild;
	private static TextChannel debugChannel, colorAssignChannel;
	private static JDA jda;
	private static MessageManager msgMan;
	private static HashMap<String, Emote> emoteStorage = new HashMap<>();
	private static Role subMateRole, verifiedMember, cycleColor;
	private static CommandManager cmdMan;
	private static StreamTracker tracker;
	private static boolean debugMode = false;
	private static HashMap<String, String> messageStorage = new HashMap<>();
	private static boolean bajoLastStreamState = false, gattLastStreamState = false, stankyLastStreamState = false;
	public static void setMessage(String key, String mes) {
		messageStorage.put(key, mes);
	}
	public static void main(String[] args) throws LoginException, RateLimitedException, InterruptedException {
		Settings.setCommandStarter("_");
		if (args.length > 2){
			if (args[2].equals("debug")){
				Settings.setCommandStarter("__");
				for (int x = 0; x < 50; x++) {
					System.out.println("Debug mode enabled!");
				}
				debugMode = true;
			}
		}

		msgMan = new MessageManager();
		cmdMan = new CommandManager();
		cmdMan.getAdminusers().add("113462564217683968");
		cmdMan.enableSnooper("space.gatt.kbb.commands");
		jda = new JDABuilder(AccountType.BOT).setToken(args[0])
				.addListener(new CommandListener())
				.addListener(new EventListener())
				.setAutoReconnect(true)
				.buildBlocking();
		if (!debugMode) {
			jda.getPresence().setGame(Game.of("in the Washing Machine"));
		}else{
			jda.getPresence().setGame(Game.of("in the evil debugging Washing Machine"));
		}
		selfuser = jda.getSelfUser();
		botObject = jda.asBot();
		bajoGuild = jda.getGuildById("290285351099039747");
		if (bajoGuild.checkVerification()) {
			debugChannel = botObject.getJDA().getTextChannelById("302980774511247366");
			colorAssignChannel = botObject.getJDA().getTextChannelById("301941402932346882");
			subMateRole = getBajoGuild().getRolesByName("Sub-Mate!", false).size() > 0 ? getBajoGuild()
					.getRolesByName("Sub-Mate!", false).get(0) : getBajoGuild().getRoleById("299549151945818112");
			verifiedMember = getBajoGuild().getRolesByName("Verified Member", true).size() > 0 ?
					getBajoGuild().getRolesByName("Verified Member", true).get(0) : getBajoGuild().getRoleById
					("309931902612144128");
			cycleColor = getBajoGuild().getRolesByName("C: Cycling", true).size() > 0 ?
					getBajoGuild().getRolesByName("C: Cycling", true).get(0) : getBajoGuild().getRoleById
					("304966482440486912");
			if (cycleColor != null) {
				startCycleRotator();
			}
			for (Emote e : bajoGuild.getEmotes()) {
				emoteStorage.put(e.getName().toLowerCase(), e);
				if (debugMode) {
					System.out.println(
							"Registered " + e.getName().toLowerCase() + " for " + e + " (M: " + e.getAsMention() + "  ID: "
									+ e.getId() + ")");
				}
			}
		}
		tracker = new StreamTracker(args[1]);
		if (!debugMode) {
			startStreamTracker();
		}
		if (debugMode) {
			System.out.println("Running from: " + System.getProperty("user.dir"));
		}
	}

	public static boolean isDebugMode() {
		return debugMode;
	}

	public static MessageManager getMsgMan() {
		return msgMan;
	}

	public static StreamTracker getStreamTracker() {
		return tracker;
	}

	private static void startStreamTracker() {
		getScheduler().scheduleAtFixedRate(() -> {
			System.out.println("Checking for streams... bajostream");
			JsonElement currentBajo = tracker.getStreamData("bajostream");
			boolean isBajoStreaming = tracker.isStreamLive(currentBajo);
			System.out.println("Got the data: " + isBajoStreaming);
			if (bajoLastStreamState != isBajoStreaming) {
				bajoLastStreamState = isBajoStreaming;
				if (isBajoStreaming) {
					getMsgMan().sendMessage(bajoGuild.getTextChannelById("294443160581701632"),
							new ReturnMessage().setColor(Color.GREEN).setMessage(getEmoteStorage().get("gasp")
									.getAsMention() + " @everyone Our lord and glorious saviour **[Bajo]" +
									"(https://twitch.tv/bajostream)** has " +
									"started streaming! Head on over!" +
									"\n\n__**[Now Playing:]()**__ " + currentBajo.getAsJsonObject().get("game")
									.getAsString()
									+ "\n__**[Stream Title:]()**__ " + currentBajo.getAsJsonObject().get(
									"channel").getAsJsonObject().get("status").getAsString()
									+ "\n__**[Viewers:]()**__ " + currentBajo.getAsJsonObject().get("viewers")
									.getAsString())
									.setThumbnailURL(currentBajo.getAsJsonObject().get("channel").getAsJsonObject()
											.get("logo").getAsString())
									.setDisplayURL("https://twitch.tv/bajostream").setTitle("Twitch.TV Updates")
									.setImageURL(currentBajo.getAsJsonObject().get("preview").getAsJsonObject()
											.get("large").getAsString()));
				}
			}
			JsonElement currentGatt = tracker.getStreamData("reallygatt");
			boolean isGattStreaming = tracker.isStreamLive(currentGatt);
			if (gattLastStreamState != isGattStreaming) {
				gattLastStreamState = isGattStreaming;
				if (isGattStreaming) {

					getMsgMan().sendMessage(bajoGuild.getTextChannelById("294443160581701632"),
							new ReturnMessage().setColor(Color.GREEN).setMessage(getEmoteStorage().get("gasp")
									.getAsMention() + " Oh wow, my master **[Gatt](https://twitch.tv/reallygatt)** " +
									"is" +
									" now streaming?! You should [go watch!](https://twitch.tv/reallygatt)" +
									"\n\n__**[Now Playing:]()**__ " +
									currentGatt.getAsJsonObject().get("game").getAsString()
									+ "\n__**[Stream Title:]()**__ " + currentGatt.getAsJsonObject().get(
									"channel").getAsJsonObject().get("status").getAsString()
									+ "\n__**[Viewers:]()**__ " + currentGatt.getAsJsonObject().get("viewers")
									.getAsString())
									.setThumbnailURL(currentGatt.getAsJsonObject().get("channel").getAsJsonObject()
											.get("logo").getAsString())
									.setDisplayURL("https://twitch.tv/reallygatt").setTitle("Twitch.TV Updates")
									.setImageURL(currentGatt.getAsJsonObject().get("preview").getAsJsonObject()
											.get("large").getAsString()));

				}
			}
			boolean checkStanky = false;
			if (checkStanky){
				System.out.println("check stanky");
				currentGatt = tracker.getStreamData("dspstanky");
				boolean isStankyStreaming = tracker.isStreamLive(currentGatt);
				if (stankyLastStreamState != isStankyStreaming) {
					stankyLastStreamState = isStankyStreaming;
					if (isStankyStreaming) {

						getMsgMan().sendMessage(bajoGuild.getTextChannelById("294443160581701632"),
								new ReturnMessage().setColor(Color.GREEN).setMessage(getEmoteStorage().get("gasp")
										.getAsMention() + " Oh wow. **[DSPStanky](https://twitch.tv/dspstanky)**" +
										" " +
										"is" +
										" now streaming?! You should [go watch!](https://twitch.tv/dspstanky)" +
										"\n\n__**[Now Playing:]()**__ " +
										currentGatt.getAsJsonObject().get("game").getAsString()
										+ "\n__**[Stream Title:]()**__ " + currentGatt.getAsJsonObject().get(
										"channel").getAsJsonObject().get("status").getAsString()
										+ "\n__**[Viewers:]()**__ " + currentGatt.getAsJsonObject().get("viewers")
										.getAsString())
										.setThumbnailURL(
												currentGatt.getAsJsonObject().get("channel").getAsJsonObject()
														.get("logo").getAsString())
										.setDisplayURL("https://twitch.tv/reallygatt").setTitle("Twitch.TV Updates")
										.setImageURL(currentGatt.getAsJsonObject().get("preview").getAsJsonObject()
												.get("large").getAsString()));


					}
				}
			}

		}, 10, 10, TimeUnit.SECONDS);
	}

	private static void startCycleRotator() {
		Random rnd = new Random();
		getScheduler().scheduleAtFixedRate(() -> {
			if (rnd.nextBoolean()) {
				int r = rnd.nextInt(256);
				rnd.nextInt();
				int g = rnd.nextInt(256);
				rnd.nextInt();
				int b = rnd.nextInt(256);
				rnd.nextInt();
				Color c = new Color(r, g, b);
				cycleColor.getManager().setColor(c).complete();
			}
		}, 0, 60, TimeUnit.SECONDS);
	}

	public static Role getCycleColor() {
		return cycleColor;
	}

	public static Role getVerifiedMember() {
		return verifiedMember;
	}

	public static HashMap<String, Emote> getEmoteStorage() {
		return emoteStorage;
	}

	public static Role getSubMateRole() {
		return subMateRole;
	}

	public static CommandManager getCmdMan() {
		return cmdMan;
	}

	public static ScheduledExecutorService getScheduler() {
		return scheduler;
	}

	public static JDA getJda() {
		return jda;
	}

	public static SelfUser getSelfuser() {
		return selfuser;
	}

	public static JDABot getBotObject() {
		return botObject;
	}

	public static Guild getBajoGuild() {
		return bajoGuild;
	}

	public static TextChannel getDebugChannel() {
		return debugChannel;
	}

	public static TextChannel getColorAssignChannel() {
		return colorAssignChannel;
	}
}
