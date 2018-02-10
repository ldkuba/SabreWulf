package engine.server.core;

import engine.server.tcp.ClientListenerThreadTCP;
import engine.server.tcp.ClientSenderThreadTCP;
import game.server.Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class CoreClientThread extends Thread {
    Socket SCSocket=null;
    ClientSenderThreadTCP CSTTCP=null;
    ClientListenerThreadTCP CLTTCP = null;
    Server server;
    Player player;

    CoreClientThread(Socket clientSocket, Server server){
        this.SCSocket = clientSocket;
        this.server = server;
        this.player = player;
    }

    public void run(){


        CSTTCP = new ClientSenderThreadTCP(SCSocket, server);
        CSTTCP.setName("player."+SCSocket.getInetAddress()+".sender");
        CSTTCP.start();


        CLTTCP = new ClientListenerThreadTCP(SCSocket, server);
        CLTTCP.setName("player."+SCSocket.getInetAddress()+".listener");
        CLTTCP.start();

        while(true){
            try {
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }
}
