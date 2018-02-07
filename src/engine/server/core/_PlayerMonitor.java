package engine.server.core;

import engine.common_net.TCPTalks_trial;

import java.net.Socket;

public class _PlayerMonitor extends Thread {
    private static int numberOfPlayers;
    _PlayerMonitor(){
        numberOfPlayers = 0;
        TCPTalks_trial tcps = new TCPTalks_trial(0,0);
    }

    synchronized public void addPlayer(){
        numberOfPlayers++;
    }

    synchronized public void removePlayer(){
        numberOfPlayers--;
    }

    public int getNoPlayers(){
        return numberOfPlayers;
    }

    public void run(){
        while(true){
            System.out.println(numberOfPlayers);
            try {
                sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
