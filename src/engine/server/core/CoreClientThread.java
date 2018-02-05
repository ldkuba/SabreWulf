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

        CSTTCP = new ClientSenderThreadTCP(SCSocket);
        try {
            CLTTCP = new ClientListenerThreadTCP(SCSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        CSTTCP.start();
        CLTTCP.start();

        playerMonitor.addPlayer();

    }
}
