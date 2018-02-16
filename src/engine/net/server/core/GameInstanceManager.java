package engine.net.server.core;

import engine.net.common_net.NetworkManager;
import engine.net.common_net.networking_messages.BattleBeginMessage;
import engine.net.common_net.networking_messages.TimerEventMessage;
import game.server.GameServer;
import game.server.states.ServerMain;

public class GameInstanceManager extends Thread {
    private GameInstance instance;
    private GameServer server;
    private boolean countdownTrigger;
    private ServerMain gameEngine;
    private NetworkManager netMan;

    public GameInstanceManager(GameInstance instance,  GameServer server){
        this.server = server;
        this.instance = instance;
        netMan = new NetworkManager(gameEngine);
        gameEngine = new ServerMain();
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
    public void notifyEndOfCountdown(){
        server.broadcastTCP(new BattleBeginMessage(), instance.getPlayersInLobby());
        gameEngine.run();
    }


}