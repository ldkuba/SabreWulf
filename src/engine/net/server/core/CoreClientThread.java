package engine.net.server.core;

import engine.net.server.tcp.ServerReceiverTCP;
import engine.net.server.tcp.ServerSenderTCP;
import game.server.GameServer;

public class CoreClientThread extends Thread {
    NetPlayer player;
    ServerSenderTCP CSTTCP=null;
    ServerReceiverTCP CLTTCP = null;
    GameServer gameServer;

    CoreClientThread(NetPlayer player, GameServer gameServer){
        this.player=player;
        this.gameServer = gameServer;
    }

    public void run(){
        CSTTCP = new ServerSenderTCP(player, gameServer);
        CSTTCP.setName("player."+player.getSocket().getInetAddress()+".sender");
        CSTTCP.start();

        CLTTCP = new ServerReceiverTCP(player, gameServer);
        CLTTCP.setName("player."+player.getSocket().getInetAddress()+".listener");
        CLTTCP.start();
    }
}
