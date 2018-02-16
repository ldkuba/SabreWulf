package engine.net.common_net;

import java.net.SocketException;
import java.util.ArrayList;

import engine.application.Application;
import engine.net.client.udp.ClientReceiverUDP;
import engine.net.common_net.Synchronizable;
import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.server.core.Player;
import engine.net.server.udp.ServerSenderUDP;

public class NetworkManager {

    private ArrayList<Synchronizable> syncData;
    private boolean networkType; // False is Client, True is Server
    private MessageListener messageListener;
    private ConnectionListener connectionListener;
    private ArrayList<Player> players;

    public NetworkManager(ArrayList<Player> players, Application app){
        this.networkType = true;
        this.players = players;
        initializeDatagramSockets();
    }

    private void initializeDatagramSockets() {
        for(int i=0; i< players.size(); i++){
            try {
                players.get(i).generateDatagramSocket();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }

    public NetworkManager(Application app){
        this.networkType = false;
    }

    public void registerConnectionListener(ConnectionListener connectionListener){
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

    public void setupServer(){
        ServerSenderUDP ssudp;

    }

    public void setupClient(){
        ClientReceiverUDP crudp;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}