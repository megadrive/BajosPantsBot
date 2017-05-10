package space.gatt.kbb;

import net.dv8tion.jda.bot.JDABot;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import space.gatt.kbb.commandmanager.CommandListener;
import space.gatt.kbb.commandmanager.CommandManager;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class KingMain {

	private static SelfUser selfuser;
	private static JDABot botObject;
	private static Guild bajoGuild;
	private static TextChannel debugChannel, colorAssignChannel;
	private static JDA jda;

	private static MessageManager msgMan;
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	private static HashMap<String, Emote> emoteStorage = new HashMap<>();

	private static Role subMateRole, verifiedMember, cycleColor;

	private static CommandManager cmdMan;

	private static HashMap<String, String> messageStorage = new HashMap<>();

	public static void setMessage(String key, String mes){
		messageStorage.put(key, mes);
	}

	public static void main(String[] args) throws LoginException, RateLimitedException, InterruptedException {
		Settings.setCommandStarter("_");
		msgMan = new MessageManager();
		cmdMan = new CommandManager();
		cmdMan.getAdminusers().add("113462564217683968");
		cmdMan.enableSnooper("space.gatt.kbb.commands");
		jda = new JDABuilder(AccountType.BOT).setToken(args[0])
				.addListener(new CommandListener())
				.addListener(new EventListener())
				.setAutoReconnect(true)
				.buildBlocking();
		jda.getPresence().setGame(Game.of("in the Washing Machine"));
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
			if (cycleColor != null){
				startCycleRotator();
			}
			for (Emote e : bajoGuild.getEmotes()){
				emoteStorage.put(e.getName(), e);
				System.out.println("Registered " + e.getName() + " for " + e + " (M: " + e.getAsMention() +"  ID: "
						+ e.getId() + ")");
			}
		}
		System.out.println("Running from: " + System.getProperty("user.dir"));
	}

	public static MessageManager getMsgMan() {
		return msgMan;
	}

	private static void startCycleRotator(){
		Random rnd = new Random();
		getScheduler().scheduleAtFixedRate(()->{
			if (rnd.nextBoolean()){
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
