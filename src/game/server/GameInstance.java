package game.server;

import engine.server.core.Player;

import java.util.ArrayList;

public class GameInstance {
    int MAX_SIZE_GAME_SIZE=6;
    ArrayList<Player> instance_players;

    public GameInstance(Player player){
        instance_players = new ArrayList<Player>(6);
    }

    public boolean addPlayer(Player player){
        if (instance_players.size()<=MAX_SIZE_GAME_SIZE){
            instance_players.add(player);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean removePlayer(Player player){
        if(instance_players.contains(player)){
            instance_players.remove(player);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isFull(){
        return (instance_players.size()>=6);
    }

    public boolean isReady(){
        int readyCount=0;
        for(int i=0; i<5; i++){
            if(instance_players.get(i).getReady()){
                readyCount++;
            }
        }
        if(readyCount==6){
            return true;
        }
        else{
            return false;
        }

    }

}