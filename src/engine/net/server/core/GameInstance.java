package engine.net.server.core;
import game.server.GameServer;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameInstance {
    int MAX_SIZE_GAME_SIZE = 6;

    private ArrayList<Player> playersInLobby;
    private GameInstanceManager GIManager;
    private BlockingQueue<AbstractMethodError> messages = new LinkedBlockingQueue<>(150);

    public GameInstance(GameServer server) {
        playersInLobby = new ArrayList<>(MAX_SIZE_GAME_SIZE);
        this.GIManager = new GameInstanceManager(this, server);
        this.GIManager.setName("GameInstanceManager " + server.games.size());
        this.GIManager.start();
    }

    public ArrayList<Player> getPlayersInLobby() {
        return playersInLobby;
    }

    public void addPlayer(Player player) {
        playersInLobby.add(player);
    }

    public boolean isFull() {
        return (playersInLobby.size() >= 6);
    }

    public ArrayList<PlayerPayload> getPlayerPayload() {
        ArrayList<PlayerPayload> pld = new ArrayList<PlayerPayload>();
        for (int i = 0; i < playersInLobby.size(); i++) {
            pld.add(playersInLobby.get(i).getPayload());
        }
        return pld;
    }

    public void removePlayer(Player player) {
        playersInLobby.remove(player);
    }

    public boolean isReady() {
        if (playersInLobby.size()==1) {
            for (int i = 0; i < playersInLobby.size(); i++) {
                if (!playersInLobby.get(i).getReady()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void initializeDatagramSockets(){
        for(int i=0; i<playersInLobby.size(); i++){
            try {
                playersInLobby.get(i).generateDatagramSocket();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }

    public GameInstanceManager getGIManager() {
        return GIManager;
    }
}
