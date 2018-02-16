package engine.net.server.core;

import engine.net.common_net.networking_messages.TimerEventMessage;
import game.server.GameServer;

public class GameInstanceManager extends Thread {
    private GameInstance instance;
    private GameServer server;
    private boolean countdownTrigger;
    public GameInstanceManager(GameInstance instance,  GameServer server){
        this.server = server;
        this.instance = instance;
    }


    public void run(){
        countdownTrigger = false;
        while(true){
            try {
                Thread.currentThread().sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(instance.isReady() && !countdownTrigger){
                 countdownTrigger = true;
                 Timer time = new Timer(10, this);
                 time.start();
            }

        }
    }

    public void notifyTick(int tickTime){
        server.broadcastTCP(new TimerEventMessage(tickTime), instance.getPlayersInLobby());
    }


}