package game.server;

import engine.server.core.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class GameInstance {
    int MAX_SIZE_GAME_SIZE=6;
    HashMap<Integer, Player> playersInLobby;

    public GameInstance(){
        playersInLobby = new HashMap<Integer, Player>(6);
    }
    
    public HashMap<Integer, Player> getPlayers()
    {
    	return playersInLobby;
    }

    public void addPlayer(Player player){
            playersInLobby.put(playersInLobby.size()+1, player);
    }

    public boolean isFull(){
        return (playersInLobby.size()>=6);
    }


}
