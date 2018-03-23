package engine.net.server.core;

import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.common_net.networking_messages.BattleBeginMessage;
import engine.net.common_net.networking_messages.TimerEventMessage;
import game.common.config;
import game.server.GameServer;
import game.server.ingame.ServerMain;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameInstanceManager extends Thread {
    private GameInstance instance;
    private GameServer server;
    private boolean countdownTrigger;
    private ServerMain gameEngine;
    private BlockingQueue<AbstractMessage> messages;
    private boolean running = true;

    public GameInstanceManager(GameInstance instance,  GameServer server){
        this.server = server;
        this.instance = instance;
        messages = new LinkedBlockingQueue<>();
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
                instance.setUnavailable();
                countdownTrigger = true;
                Timer time = new Timer(config.lobbyCountdown, this);
                time.start();
            }
        }

        //System.out.println("Starting engine. Wroom!");

        for(NetPlayer player : instance.getPlayersInLobby())
        {
            player.setCurrentGame(instance.getGameId());
        }

        server.broadcastTCP(new BattleBeginMessage(), instance.getPlayersInLobby());
        gameEngine = new ServerMain(instance.getPlayersInLobby());
        gameEngine.run();
    }

    public ServerMain getGameEngine() {
        return gameEngine;
    }

    public void notifyTick(int tickTime){
        server.broadcastTCP(new TimerEventMessage(tickTime), instance.getPlayersInLobby());
    }

    public void notifyEndOfCountdown(){
        running = false;
    }

}