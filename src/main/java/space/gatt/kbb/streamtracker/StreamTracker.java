package space.gatt.kbb.streamtracker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jdk.nashorn.internal.objects.NativeUint8Array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class StreamTracker {

	protected String TWITCH_STREAM = "https://api.twitch.tv/kraken/streams/$c$";

	private String key;

	public StreamTracker(String key) {
		this.key = key;
	}

	private String insertChannel(String url, String channel)
	{
		return url.replace("$c$", channel );
	}

	public JsonElement getStreamData(String targetChannel){
		try
		{
			URL url = new URL(  insertChannel(TWITCH_STREAM, targetChannel) );
			URLConnection conn = url.openConnection();
			conn.addRequestProperty("Client-id", key);
			BufferedReader br = new BufferedReader( new InputStreamReader( conn.getInputStream() ));
			String inputLine = br.readLine();
			JsonObject jsonObj = new Gson().fromJson(inputLine, JsonObject.class);
			br.close();

			System.out.println(jsonObj.get("stream").toString());
			return jsonObj.has("stream") ? jsonObj.get("stream") : null;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public boolean isStreamLive(JsonElement jsonObj)
	{
		return jsonObj != null && !jsonObj.toString().equalsIgnoreCase("null");
	}

	public boolean isStreamLive(String targetChannel)
	{
		JsonElement jsonObj = getStreamData(targetChannel);
		return jsonObj != null && !jsonObj.toString().equalsIgnoreCase("null");
	}
}
