package engine.server.tcp;

import engine.client.tcp.ClientSenderTCP;

import java.net.Socket;

public class ClientSenderThreadTCP extends Thread{
    Socket SCSocket = null;
    public ClientSenderThreadTCP(Socket SCSocket){
        this.SCSocket = SCSocket;
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
