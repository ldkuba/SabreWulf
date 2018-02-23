package game.common;

public class config {

    /**
     * Common
     */
    public static int UDPPort = 4556;
    public static int ServerUDPPort = 5252;
    public static int TCPPort = 4555;
    public static int UDPMaxPacketSize = 256;


    /**
     * Server specific
     */
    public static int lobbyCountdown = 10;
    public static int globalConnectionsLimit = 100;
    public static int gameConnectionLimit = 1;


    /**
     * Client specific
     */
    public static String host = "127.0.0.1";
    public static String hostAWS = "34.241.78.38";
    public static String windowName = "Sabrewulf";
    public static boolean clientFullScreen = false;
    public static int screenWidth = 1280;
    public static int screenHeight = 720;
}
