package engine.server.tcp;

import engine.common_net.AbstractMessage;
import engine.server.core.GameServer;
import engine.server.core._PlayerMonitor;
import game.server.Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

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

        }

    }

    public boolean sendMessage(AbstractMessage message){
        try {
            oos.writeObject(message);
            return true;
        } catch (SocketException se){
            server.notifyConnectionListenersDisconnected(SCSocket);
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
