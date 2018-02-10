package engine.server.tcp;

import engine.common_net.AbstractMessage;
import game.server.Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ServerSenderTCP extends Thread{
    Socket SCSocket;
    Server server;
    ObjectOutputStream oos;
    public ServerSenderTCP(Socket SCSocket, Server server){
        this.SCSocket = SCSocket;
        this.server = server;
    }

    public void run(){
        boolean connection=true;
        try {
            oos = new ObjectOutputStream(SCSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!SCSocket.isClosed()){
            try {
                sleep(123);
                sendMessage(new AbstractMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
