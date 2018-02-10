package engine.server.core;

import engine.server.tcp.ServerListenerTCP;
import engine.server.tcp.ServerSenderTCP;
import game.server.Server;

import java.net.Socket;

public class CoreClientThread extends Thread {
    Socket SCSocket=null;
    ServerSenderTCP CSTTCP=null;
    ServerListenerTCP CLTTCP = null;
    Server server;
    Player player;

    CoreClientThread(Socket clientSocket, Server server){
        this.SCSocket = clientSocket;
        this.server = server;
        this.player = player;
    }

    public void run(){


        CSTTCP = new ServerSenderTCP(SCSocket, server);
        CSTTCP.setName("player."+SCSocket.getInetAddress()+".sender");
        CSTTCP.start();


        CLTTCP = new ServerListenerTCP(SCSocket, server);
        CLTTCP.setName("player."+SCSocket.getInetAddress()+".listener");
        CLTTCP.start();

    }
}
