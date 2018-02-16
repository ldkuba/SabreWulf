package engine.net.server.core;
import game.server.GameServer;

import java.util.ArrayList;

public class GameInstance {
    int MAX_SIZE_GAME_SIZE = 6;

    private ArrayList<Player> playersInLobby;
    private GameInstanceManager GIManager;

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
        if (playersInLobby.size()!=0) {
            for (int i = 0; i < playersInLobby.size(); i++) {
                if (!playersInLobby.get(i).getReady()) {
                    {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
