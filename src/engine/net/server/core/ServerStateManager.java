package engine.net.server.core;

import engine.net.server.GameServer;

public class ServerStateManager extends Thread {
    private GameServer gameServer;
    public ServerStateManager(GameServer gameServer){
        this.gameServer = gameServer;
    }

    public void run(){

         while(true){
             try {
                 Thread.currentThread().sleep(1);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             if(gameServer.getNoPlayers()>0){
                try {
                    //gameServer.broadcastTCP(new PeerCountMessage(gameServer.getNoPlayers()));
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}