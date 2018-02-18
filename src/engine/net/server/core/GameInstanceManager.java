package engine.net.server.core;

import engine.net.common_net.NetworkManager;
import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.common_net.networking_messages.BattleBeginMessage;
import engine.net.common_net.networking_messages.TimerEventMessage;
import engine.net.server.udp.ServerSenderUDP;
import game.server.GameServer;
import game.server.states.ServerMain;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameInstanceManager extends Thread {
    private GameInstance instance;
    private GameServer server;
    private boolean countdownTrigger;
    private ServerMain gameEngine;
    private NetworkManager netMan;
    private BlockingQueue<AbstractMessage> messages;
    private boolean running = true;

    public GameInstanceManager(GameInstance instance,  GameServer server){
        this.server = server;
        this.instance = instance;
        netMan = new NetworkManager(gameEngine);

        instance.initializeDatagramSockets();
        messages = new LinkedBlockingQueue<>(150);
    }


    public void run(){
        countdownTrigger = false;

        while(running) {
            try {
                Thread.currentThread().sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (instance.isEmpty()){
                server.removeGameInstance(instance);
                running=false;
            }
            if (instance.isReady() && !countdownTrigger) {
                countdownTrigger = true;
                Timer time = new Timer(10, this);
                time.start();
            }
        }

    }

    public ServerMain getGameEngine() {
        return gameEngine;
    }

    public void notifyTick(int tickTime){
        server.broadcastTCP(new TimerEventMessage(tickTime), instance.getPlayersInLobby());
    }

    public void notifyEndOfCountdown(){
        System.out.println("Starting engine. Wroom!");
        server.broadcastTCP(new BattleBeginMessage(), instance.getPlayersInLobby());
        gameEngine = new ServerMain();
        gameEngine.run();
        running=false;
    }


}