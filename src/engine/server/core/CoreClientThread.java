package engine.server.core;

import engine.server.tcp.ClientListenerThreadTCP;
import engine.server.tcp.ClientSenderThreadTCP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class CoreClientThread extends Thread {
    Socket SCSocket=null;
    _PlayerMonitor playerMonitor;
    ClientSenderThreadTCP CSTTCP=null;
    ClientListenerThreadTCP CLTTCP = null;

    CoreClientThread(Socket clientSocket, _PlayerMonitor pMonitor){
        this.SCSocket = clientSocket;
        this.playerMonitor = pMonitor;
    }

    public void run(){

        playerMonitor.addPlayer();

        CSTTCP = new ClientSenderThreadTCP(SCSocket, playerMonitor);
        CSTTCP.setName("player."+SCSocket.getInetAddress()+".sender");
        CSTTCP.start();


        CLTTCP = new ClientListenerThreadTCP(SCSocket, playerMonitor);
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
