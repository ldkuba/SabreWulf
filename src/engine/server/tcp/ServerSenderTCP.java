package engine.server.tcp;

import engine.common_net.AbstractMessage;
import engine.common_net.PeerList;
import engine.server.core.Player;
import game.server.Server;
import org.lwjgl.system.CallbackI;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ServerSenderTCP extends Thread{
    Player player;
    Server server;
    ObjectOutputStream oos;
    public ServerSenderTCP(Player player, Server server){
        this.player = player;
        this.server = server;
    }

    public void run(){
        try {
            oos = new ObjectOutputStream(player.getSocket().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!player.getSocket().isClosed()){
            try {
                sleep(1);
                oos.writeObject(player.takeMessage());

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void sendMessage(AbstractMessage message){
        player.addMsg(new PeerList(123));
        /*
        try {

            //oos.writeObject(player.takeMessage());

        } catch (SocketException se){
            server.notifyConnectionListenersDisconnected(player);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         */
    }
}
