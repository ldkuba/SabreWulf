package engine.server.core;

import engine.common_net.AbstractMessage;
import game.networking.PeerList;

import java.net.Socket;
import java.util.concurrent.*;

public class Player {
    private Socket socket;
    private String name;
    private boolean isReady=false;
    private int slot;
    private BlockingQueue<AbstractMessage> pbq;

    public Player(Socket socket){
        this.socket=socket;
        pbq = new LinkedBlockingQueue<AbstractMessage>(1000);

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

    synchronized public int getMsgCount(){
        return pbq.size();
    }

    public void setReady(boolean ready){
        isReady=ready;
    }

    public boolean getReady(){
        return isReady;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
}
