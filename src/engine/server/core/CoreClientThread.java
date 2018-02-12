package engine.server.core;

import engine.server.tcp.ServerListenerTCP;
import engine.server.tcp.ServerSenderTCP;
import game.server.Server;

import java.net.Socket;

public class CoreClientThread extends Thread {
    Player player;
    ServerSenderTCP CSTTCP=null;
    ServerListenerTCP CLTTCP = null;
    Server server;

    CoreClientThread(Player player, Server server){
        this.player=player;
        this.server = server;
    }

    public void run(){
        CSTTCP = new ServerSenderTCP(player, server);
        CSTTCP.setName("player."+player.getSocket().getInetAddress()+".sender");
        CSTTCP.start();

        CLTTCP = new ServerListenerTCP(player, server);
        CLTTCP.setName("player."+player.getSocket().getInetAddress()+".listener");
        CLTTCP.start();
    }
}
