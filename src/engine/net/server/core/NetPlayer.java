package engine.net.server.core;

import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import engine.net.common_net.networking_messages.AbstractMessage;
import game.common.config;

public class NetPlayer implements Serializable {

    private PlayerPayload payload;
    private Socket socket;
    private BlockingQueue<AbstractMessage> pbq;
    private DatagramSocket datagramSocket = null;

    public NetPlayer(Socket socket){
    	this.socket=socket;
        pbq = new LinkedBlockingQueue<>(6000);
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
            //System.out.println(msg);
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

    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }
    
    public int getCurrentGame() {
        return payload.getCurrentGame();
    }
    
    public void setCurrentGame(int gameId)
    {
    	payload.setCurrentGame(gameId);
    }
    
    public int getPlayerId()
    {
    	return payload.getNetPlayerId();
    }
    
    public void setPlayerId(int playerId)
    {
    	payload.setNetPlayerId(playerId);
    }
}
