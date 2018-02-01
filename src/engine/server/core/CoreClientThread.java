package engine.server.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class CoreClientThread extends Thread {
    Socket clientSocket;
    ByteArrayOutputStream baos;
    int timeout = 2000;
    _PlayerMonitor playerMonitor;
    CoreClientThread(Socket clientSocket, _PlayerMonitor pMonitor){
        this.clientSocket = clientSocket;
        this.playerMonitor = pMonitor;
    }

    public void run(){
        playerMonitor.addPlayer();

        // Naive client cutoff
        while(true){
                if(clientSocket.isConnected()) {
                    try {
                        OutputStream os = clientSocket.getOutputStream();
                        os.write(1);
                    } catch (IOException e) {
                        Thread.currentThread().interrupt();
                        playerMonitor.removePlayer();
                    }
                    try {
                        Thread.currentThread().sleep(100);
                    } catch (InterruptedException e) {
                    }
                }

        }
    }
}
