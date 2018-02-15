package engine.net.common_net;

import engine.application.Application;
import engine.net.common_net.Synchronizable;
import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.server.core.Player;
import game.server.GameServer;

import java.util.ArrayList;

public class NetworkManager {

    private ArrayList<Synchronizable> syncData;
    private boolean networkType; // False is client, True is Server
    private MessageListener messageListener;
    private ConnectionListener connectionListener;
    public void registerConnectionListener(ConnectionListener connectionListener ){
        this.connectionListener = connectionListener;
    }

    public void registerMessageListener(MessageListener messageListener){
        this.messageListener = messageListener;
    }

    public void addMessage(AbstractMessage message, Player player){
        if(networkType){
            messageListener.addMessage(message,player);
        }
        else{
            messageListener.addMessage(message);
        }
    }

    public void addConnectionEvent(Player player, boolean connected){
        if(networkType){
            connectionListener.addConnectionEvent(connected,player);
        }
        else{
            connectionListener.addConnectionEvent(connected);
        }

    }

    public void handleMessagesAndConnections(){
        messageListener.handleMessageQueue();
        connectionListener.handleConnectionQueue();
    }

    public NetworkManager(boolean networkType, Application app){
        this.networkType = networkType;
    }

}