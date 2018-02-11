package engine.server.core;

import engine.common_net.PeerList;
import game.server.Server;
import org.w3c.dom.ls.LSSerializerFilter;

public class ServerStateManager extends Thread {
    private Server server;
    public ServerStateManager(Server server){
        this.server = server;
    }

    public void run(){

         while(true){
             try {
                 Thread.currentThread().sleep(1);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }

             if(server.getNoPlayers()>0) {
                System.out.println(server.getNoPlayers());
                try {

                    server.broadcastTCP(new PeerList(server.getNoPlayers()), server.players);
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}