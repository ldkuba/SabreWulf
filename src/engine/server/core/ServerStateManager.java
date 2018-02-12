package engine.server.core;

import game.networking.PeerList;
import game.server.Server;

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
             if(server.getNoPlayers()>0){
                try {
                    //server.broadcastTCP(new PeerList(server.getNoPlayers()));
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}