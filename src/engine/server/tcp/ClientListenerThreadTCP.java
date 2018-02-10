package engine.server.tcp;

import engine.common_net.AbstractMessage;
import engine.server.core._PlayerMonitor;
import game.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientListenerThreadTCP extends Thread{
    ObjectInputStream ois = null;
    Socket SCSocket;
    Server server;
    public ClientListenerThreadTCP(Socket SCSocket, Server server) {
        this.SCSocket = SCSocket;
        this.server = server;
    }
    public void run(){
        try {
            ois = new ObjectInputStream(SCSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
            try {
                server.notifyMessageListeners((AbstractMessage) ois.readObject());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }

        }

    }
}
