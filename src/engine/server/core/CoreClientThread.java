package engine.server.core;

import java.net.Socket;

public class CoreClientThread extends Thread {
    Socket clientSocket;
    int timeout = 2000;
    _PlayerMonitor playerMonitor;
    CoreClientThread(Socket clientSocket, _PlayerMonitor pMonitor){
        this.clientSocket = clientSocket;
        this.playerMonitor = pMonitor;
        playerMonitor.setName("Player Monitor");
        playerMonitor.run();

    }

    public void run(){
        playerMonitor.addPlayer();
        while(true){
            if(!clientSocket.isConnected()){
                try {
                    sleep(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                playerMonitor.removePlayer();
                Thread.currentThread().interrupt();
            }
        }

    }
}
