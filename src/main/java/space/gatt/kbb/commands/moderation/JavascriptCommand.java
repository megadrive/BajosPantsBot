package space.gatt.kbb.commands.moderation;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import space.gatt.kbb.KingMain;
import space.gatt.kbb.annotations.*;
import space.gatt.kbb.commandmanager.ReturnMessage;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;

@Command("js")
@Syntax("js <script>")
@Permissions(ranks = {"294441010077892608"}, ranksById = true, adminOnly = true)
@Usage("js")
@Description("Run some JS Code")
@Group("Admin")
public class JavascriptCommand {

	private static String[] blocked = new String[]{"leave", "Guilds", "Token"};

	@IMethod
	public static ReturnMessage command(Message message, Member user, String[] args) {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");

		engine.put("jda", message.getJDA());
		engine.put("message", message);
		engine.put("guild", message.getGuild());
		engine.put("textchannel", message.getTextChannel());
		engine.put("channel", message.getChannel());
		engine.put("main", KingMain.class);
		engine.put("messagemanager", KingMain.getMsgMan());

		String script = KingMain.getCmdMan().combineArguments(args);

		for (String check : blocked){
			if (script.contains(check)) {
				return new ReturnMessage().setColor(Color.RED).setMessage("JavaScript Eval Expression may be " +
						"malicious, canceling.");
			}
		}

		try {
			Object result = engine.eval(script);
			if (result != null) {
				return new ReturnMessage().setColor(Color.GREEN).setMessage("Running `" + script + "` " +
						".\n\n**Result:**\n\n" + result.toString()).setTitle("Javascript Executor");
			}
		} catch (ScriptException e) {
			return new ReturnMessage().setColor(Color.RED).setMessage("The error `" + e.getMessage() + "` occurred " +
					"while executing the " +
					"JavaScript statement.");
		}

		return new ReturnMessage().setColor(Color.RED).setMessage("Something happened!");

	}

}
