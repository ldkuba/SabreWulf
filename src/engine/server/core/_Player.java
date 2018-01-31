package engine.server.core;

import javax.sound.sampled.Port;
import java.net.InetAddress;

public class _Player {
    private int port;
    private static InetAddress ip;
    private static String playerName;
    _Player(int port, InetAddress ip){
        this.ip = ip;
        this.port = port;
    }

    public int getPort(){
        return port;
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public static String getPlayerName() {
        return playerName;
    }

    public static InetAddress getIp() {
        return ip;
    }
}
