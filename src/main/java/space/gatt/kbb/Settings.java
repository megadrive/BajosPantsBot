package space.gatt.kbb;

/**
 * Settings. Change things about Javacord Commander here.
 */
public class Settings {

    private static String defaultGroup = "";
    private static String commandStarter = "";
    private static String altCommandStarter = "";
    private static String msgStarter = "";

    private static String game = "";

	/**
     * @return Game
     */
    public static String getGame() {
        return game;
    }

    /**
     * Set's the game. Only included because Gasai Bot needed it.
     * @param game Value to set the game to
     */
    public static void setGame(String game) {
        Settings.game = game;
    }

	/**
     * @return Message Starter
     */
    public static String getMsgStarter() {
        return msgStarter;
    }

	/**
     * The starting symbol (or string) for all of JavacordCommanders' messages.
     * @param msgStarter The Message Starter
     */
    public static void setMsgStarter(String msgStarter) {
        Settings.msgStarter = msgStarter;
    }
    /**
     * @return Alternative Command Starter
     */
    public static String getAltCommandStarter() {
        return altCommandStarter;
    }
    /**
     * @param str Alternative Command Starter
     */
    public static void setAltCommandStarter(String str) {
        altCommandStarter = str;
    }
    /**
     * @return Default Group
     */

    public static String getCommandStarter() {
        return commandStarter;
    }
    /**
     * @param str Command Starter
     */
    public static void setCommandStarter(String str) {
        commandStarter = str;
    }
    /**
     * @return Default Group
     */

    public static String getDefaultGroup() {
        return defaultGroup;
    }
    /**
     * @param str Default Group
     */
    public static void setDefaultGroup(String str) {
        defaultGroup = str;
    }

    private static boolean loadedSettings = false;

    private static String buildString(){
        return "";
    }
}
