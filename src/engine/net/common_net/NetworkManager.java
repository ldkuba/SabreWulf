package engine.net.common_net;

import java.io.Serializable;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.NetworkEntity;
import engine.entity.component.NetDataComponent;
import engine.entity.component.NetIdentityComponent;
import engine.entity.component.NetTransformComponent;
import engine.net.client.udp.ClientReceiverUDP;
import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.server.core.Player;
import engine.net.server.udp.ServerSenderUDP;
import engine.scene.Scene;

public class NetworkManager {

    private boolean networkType; // False is Client, True is Server
    private MessageListener messageListener;
    private ConnectionListener connectionListener;
    private ArrayList<Player> players;
    private CopyOnWriteArrayList<Entity> networkEntities;
    
    private ServerSenderUDP udpSender;
    private ClientReceiverUDP udpReceiver;

    public NetworkManager(ArrayList<Player> players, Application app){
        this.networkType = true;
        this.players = players;
        
        networkEntities = new CopyOnWriteArrayList<>();
        
    	udpSender = new ServerSenderUDP(players);
    	udpSender.setName("UDP Sender");
    	udpSender.start();
        
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
        networkEntities = new CopyOnWriteArrayList<>();
    }
    
    public void startUDPReceiver()
    {
    	udpReceiver = new ClientReceiverUDP();
        udpReceiver.start();
    }
    
    public void updateEntityInNetworkManager(Entity entity, int networkId)
    {
    	//only execute updates on server
    	if(networkType == false)
    		return;
    	
    	for(Entity e : networkEntities)
    	{
    		if(((NetIdentityComponent)e.getComponent(NetIdentityComponent.class)).getNetworkId() == networkId)
    		{
    			if(e.hasComponent(NetTransformComponent.class))
    			{
    				NetTransformComponent comp = (NetTransformComponent) e.getComponent(NetIdentityComponent.class);
    				e.removeComponent(comp);
    			}
    			
    			if(e.hasComponent(NetDataComponent.class))
    			{
    				NetDataComponent comp = (NetDataComponent) e.getComponent(NetDataComponent.class);
    				e.removeComponent(comp);
    			}
    			
    			if(entity.hasComponent(NetTransformComponent.class))
    			{
    				e.addComponent((NetTransformComponent)entity.getComponent(NetTransformComponent.class));
    			}
    			
    			if(entity.hasComponent(NetDataComponent.class))
    			{
    				e.addComponent((NetDataComponent)entity.getComponent(NetDataComponent.class));
    			}
    			
    			return;
    		}
    	}
    	
    	//if not present add to netowrk entity list
    	networkEntities.add(entity);
    }
    
    private Entity getEntityByNetId(int networkId, Scene scene)
    {
    	for(Entity e : scene.getEntities())
    	{
    		if(e.hasComponent(NetIdentityComponent.class))
    		{
    			if(((NetIdentityComponent)(e.getComponent(NetIdentityComponent.class))).getNetworkId() == networkId)
    			{
    				return e;
    			}
    		}
    	}
    	
    	return null;
    }
    
    //updates local snapshot if on client or sends snapshot is on server
    public void synchronize(Scene scene)
    {
    	if(scene == null)
    		return;
    	
    	if(networkType)
    	{
    		for(Entity e : networkEntities)
    		{
    			NetworkEntity netEntity = new NetworkEntity();
    			netEntity.setNetIdentity((NetIdentityComponent)e.getComponent(NetIdentityComponent.class));
    			
    			if(e.hasComponent(NetTransformComponent.class))
    			{
    				netEntity.setNetTransform((NetTransformComponent)e.getComponent(NetTransformComponent.class));
    			}
    			
    			if(e.hasComponent(NetDataComponent.class))
    			{
    				netEntity.setNetData((NetDataComponent)e.getComponent(NetDataComponent.class));
    			}
    			
    			udpSender.addNetworkEntity(netEntity);
    		}
    	}else
    	{
    		//client - update snapshot
    		for(Entity entity : networkEntities)
    		{
    			int netId = ((NetIdentityComponent)(entity.getComponent(NetIdentityComponent.class))).getNetworkId();
    			Entity sceneEntity = getEntityByNetId(netId, scene);
    			
    			if(sceneEntity.hasComponent(NetTransformComponent.class))
    			{
    				NetTransformComponent comp = (NetTransformComponent) sceneEntity.getComponent(NetIdentityComponent.class);
    				sceneEntity.removeComponent(comp);
    			}
    			
    			if(sceneEntity.hasComponent(NetDataComponent.class))
    			{
    				NetDataComponent comp = (NetDataComponent) sceneEntity.getComponent(NetDataComponent.class);
    				sceneEntity.removeComponent(comp);
    			}
    			
    			if(entity.hasComponent(NetTransformComponent.class))
    			{
    				sceneEntity.addComponent((NetTransformComponent)entity.getComponent(NetTransformComponent.class));
    			}
    			
    			if(entity.hasComponent(NetDataComponent.class))
    			{
    				sceneEntity.addComponent((NetDataComponent)entity.getComponent(NetDataComponent.class));
    			}
    		}
    	}
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

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void addEntityEvent(Serializable entityUpdateMessage) {
    }
}