package engine.server.tcp;

import engine.common_net.AbstractMessage;
import engine.common_net.TCPTalks_trial;
import engine.server.core._PlayerMonitor;
import game.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientListenerThreadTCP extends Thread{
    TCPTalks_trial trial = null;
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

                trial = (TCPTalks_trial) ois.readObject();
                server.notifyMessageListeners((AbstractMessage) ois.readObject());


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }

        }

    }
}
