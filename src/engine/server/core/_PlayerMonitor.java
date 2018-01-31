package engine.server.core;

import java.net.Socket;

class _PlayerMonitor extends Thread {
    private static int numberOfPlayers;
    _PlayerMonitor(){
        numberOfPlayers = 0;
    }

    public void addPlayer(){
        numberOfPlayers++;
    }

    public void removePlayer(){
        numberOfPlayers--;
    }

    public int getNoPlayers(){
        return numberOfPlayers;
    }

    public void run(){
        while(true){
            System.out.println(  numberOfPlayers);
            try {
                sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
