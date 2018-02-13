package game.server;

import engine.server.core.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GameInstance {
    int MAX_SIZE_GAME_SIZE=6;
    private ArrayList<Player> playersInLobby;

    public GameInstance(){
        playersInLobby = new ArrayList<>(MAX_SIZE_GAME_SIZE);
    }


    public ArrayList<Player> getPlayersInLobby(){
        return playersInLobby;
    }

    public void addPlayer(Player player){
            playersInLobby.add(player);
    }

    public boolean isFull(){
        return (playersInLobby.size()>=6);
    }


}
