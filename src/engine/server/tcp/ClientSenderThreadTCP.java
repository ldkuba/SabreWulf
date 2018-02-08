package engine.server.tcp;

import engine.server.core.GameServer;
import engine.server.core._PlayerMonitor;
import game.server.Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSenderThreadTCP extends Thread{
    Socket SCSocket;
    Server server;
    ObjectOutputStream oos;
    public ClientSenderThreadTCP(Socket SCSocket, Server server){
        this.SCSocket = SCSocket;
        this.server = server;
    }

    public void run(){
        try {
            oos = new ObjectOutputStream(SCSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            try {
                oos.writeObject(server.abs.take());
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
