package engine.server.core;

import engine.common_net.AbstractMessage;
import game.networking.PeerList;

import java.io.Serializable;
import java.net.Socket;
import java.util.concurrent.*;

public class Player implements Serializable {
    private PlayerPayload payload;
    private Socket socket;
    private BlockingQueue<AbstractMessage> pbq;

    public Player(Socket socket){
        this.socket=socket;
        pbq = new LinkedBlockingQueue<>(1000);
        payload = new PlayerPayload();
    }

    public Socket getSocket() {
        return socket;
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

    public String getName(){
        return payload.getName();
    }

    public void setName(String name){
        payload.setName(name);
    }

    public void setReady(boolean ready){
        payload.setReady(ready);
    }

    public boolean getReady(){
        return payload.getReady();
    }

    public int getChar(){
        return payload.getCharacterSelection();
    }

    public void setChar(int character){
        payload.setCharacterSelection(character);
    }

    public PlayerPayload getPayload() {
        return payload;
    }
}
