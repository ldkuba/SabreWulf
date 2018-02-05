package engine.server.tcp;

import engine.server.core._PlayerMonitor;

import java.net.Socket;

public class ClientSenderThreadTCP extends Thread{
    Socket SCSocket = null;
    _PlayerMonitor pMonitor;
    public ClientSenderThreadTCP(Socket SCSocket, _PlayerMonitor pMonitor){
        this.SCSocket = SCSocket;
        this.pMonitor = pMonitor;
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
