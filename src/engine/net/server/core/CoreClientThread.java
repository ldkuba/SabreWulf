package engine.net.server.core;

import engine.net.server.tcp.ServerListenerTCP;
import engine.net.server.tcp.ServerSenderTCP;
import engine.net.server.GameServer;

public class CoreClientThread extends Thread {
    Player player;
    ServerSenderTCP CSTTCP=null;
    ServerListenerTCP CLTTCP = null;
    GameServer gameServer;

    CoreClientThread(Player player, GameServer gameServer){
        this.player=player;
        this.gameServer = gameServer;
    }

    public void run(){
        CSTTCP = new ServerSenderTCP(player, gameServer);
        CSTTCP.setName("player."+player.getSocket().getInetAddress()+".sender");
        CSTTCP.start();

        CLTTCP = new ServerListenerTCP(player, gameServer);
        CLTTCP.setName("player."+player.getSocket().getInetAddress()+".listener");
        CLTTCP.start();
    }
}
