package engine.server.core;

import engine.common_net.AbstractMessage;
import game.networking.PeerList;

import java.net.Socket;
import java.util.concurrent.*;

public class Player {
    private Socket socket;
    private String name;
    private boolean isReady=false;
    private int characterSelection = -1;
    private BlockingQueue<AbstractMessage> pbq;

    public Player(Socket socket){
        this.socket=socket;
        pbq = new LinkedBlockingQueue<>(1000);
    }

    public Socket getSocket() {
        return socket;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void setCharacterSelection(int selection)
    {
    	characterSelection = selection;
    }
    
    public int getCharacterSelection()
    {
    	return characterSelection;
    }

    public AbstractMessage takeMessage() {
        try {
            return pbq.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addMsg(AbstractMessage msg){
            pbq.add(msg);
    }

    public void setReady(boolean ready){
        isReady=ready;
    }

    public boolean getReady(){
        return isReady;
    }
}
