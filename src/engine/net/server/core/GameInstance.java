package engine.net.server.core;

import engine.net.server.core.Player;
import engine.net.server.core.PlayerPayload;

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

    public ArrayList<PlayerPayload> getPlayerPayload(){
        ArrayList<PlayerPayload> pld = new ArrayList<PlayerPayload>();
        for(int i=0; i<playersInLobby.size(); i++){
            pld.add(playersInLobby.get(i).getPayload());
        }
        return pld;
    }

    public void removePlayer(Player player){
        playersInLobby.remove(player);
    }


}
