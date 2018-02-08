package engine.server.tcp;

import engine.server.core._PlayerMonitor;
import game.server.Server;

import java.net.Socket;

public class ClientSenderThreadTCP extends Thread{
    Socket SCSocket;
    Server server;
    public ClientSenderThreadTCP(Socket SCSocket, Server server){
        this.SCSocket = SCSocket;
        this.server = server;
    }

    public void run(){
        while(true){
            try {
                // Just sleeps for now

                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
