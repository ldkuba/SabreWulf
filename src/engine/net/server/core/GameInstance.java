package engine.net.server.core;
import game.common.config;
import game.server.GameServer;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameInstance {

	private int gameId;
	private boolean isAvailable;
    private ArrayList<NetPlayer> playersInLobby;
    private GameInstanceManager GIManager;
    private BlockingQueue<AbstractMethodError> messages = new LinkedBlockingQueue<>(150);

    public GameInstance(GameServer server, int gameId) {
        playersInLobby = new ArrayList<>();
        this.GIManager = new GameInstanceManager(this, server);
        this.GIManager.setName("GameInstanceManager " + server.getGames().size());
        this.GIManager.start();
        this.isAvailable = true;
        this.gameId = gameId;
    }

    public ArrayList<NetPlayer> getPlayersInLobby() {
        return playersInLobby;
    }

    public void addPlayer(NetPlayer player) {
        playersInLobby.add(player);
    }

    public boolean isFull() {
        return (playersInLobby.size() >= config.gameConnectionLimit);
    }

    public ArrayList<PlayerPayload> getPlayerPayload() {
        ArrayList<PlayerPayload> pld = new ArrayList<>();
        for (int i = 0; i < playersInLobby.size(); i++) {
            pld.add(playersInLobby.get(i).getPayload());
        }
        return pld;
    }

    public void removePlayer(NetPlayer player) {
        playersInLobby.remove(player);
    }

    public boolean isReady() {
        if (playersInLobby.size()==config.gameConnectionLimit) {
            for (int i = 0; i < playersInLobby.size(); i++) {
                if (!playersInLobby.get(i).getReady()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isEmpty(){
        if(playersInLobby.size()==0){
            return true;
        }
        else{
            return false;
        }
    }
    public void setUnavailable(){
        this.isAvailable = false;
    }

    public boolean isAvailable(){
        return isAvailable;
    }
    public int getGameId()
    {
    	return this.gameId;
    }

    public GameInstanceManager getGIManager() {
        return GIManager;
    }
}
