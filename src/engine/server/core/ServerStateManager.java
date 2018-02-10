package engine.server.core;

import game.server.Server;

public class ServerStateManager extends Thread {
    private Server server;
    public ServerStateManager(Server server){
        this.server = server;
    }

    public void run(){
        while(true){
            try {
                Thread.currentThread().sleep(1000);
                System.out.println(server.getNoPlayers());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}