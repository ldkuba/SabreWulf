package engine.server.core;

import java.net.InetAddress;

public class Player {
    private int playerID;
    private InetAddress ip;
    private String name;

    public Player(InetAddress ip){
        this.name=name;
        this.ip = ip;
        this.playerID = playerID;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public InetAddress getIp() {
        return ip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPlayerID() {
        return this.playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }


}
