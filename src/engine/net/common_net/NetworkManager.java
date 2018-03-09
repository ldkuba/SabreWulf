package engine.net.common_net;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.NetworkEntity;
import engine.entity.component.NetDataComponent;
import engine.entity.component.NetIdentityComponent;
import engine.entity.component.NetTransformComponent;
import engine.net.common_net.networking_messages.AbstractMessage;
import engine.net.common_net.networking_messages.EntityUpdateMessage;
import engine.net.server.core.NetPlayer;
import engine.scene.Scene;

public class NetworkManager {

    private boolean networkType; // False is Client, True is Server
    private MessageListener messageListener;
    private ConnectionListener connectionListener;
    private ArrayList<NetPlayer> players;
    private CopyOnWriteArrayList<NetworkEntity> networkEntities;

    public NetworkManager(ArrayList<NetPlayer> players, Application app){
        this.networkType = true;
        this.players = players;
        
        networkEntities = new CopyOnWriteArrayList<>();
    }

    public NetworkManager(Application app){
        this.networkType = false;
        networkEntities = new CopyOnWriteArrayList<>();
    }
    
    public void registerNetEntity(NetIdentityComponent netIdentity)
    {
    	NetworkEntity netEntity = new NetworkEntity();
    	netEntity.setNetIdentity(netIdentity);
    	
    	networkEntities.add(netEntity);    	
    }
    
    //on server
    public void updateEntityInNetworkManager(Entity entity, int networkId)
    {
    	//only execute updates on server
    	if(networkType == false)
    		return;
    	
    	for(NetworkEntity e : networkEntities)
    	{
    		if(e.getNetIdentity().getNetworkId() == networkId)
    		{    			
    			if(entity.hasComponent(NetTransformComponent.class))
    			{
    				e.setNetTransform((NetTransformComponent)entity.getComponent(NetTransformComponent.class));
    			}
    			
    			if(entity.hasComponent(NetDataComponent.class))
    			{
    				e.setNetData((NetDataComponent)entity.getComponent(NetDataComponent.class));
    			}
    			
    			return;
    		}
    	}
    	
    	//if not present add to netowrk entity list
    	System.out.println("Wrong network setup");
    }
    
    //on client
    public void updateEntityInNetworkManager(NetworkEntity netEntity)
    {
    	for(NetworkEntity e : networkEntities)
    	{
    		if(e.getNetIdentity().getNetworkId() == netEntity.getNetIdentity().getNetworkId())
    		{
    			e.setNetTransform(netEntity.getNetTransform());
    			e.setNetData(netEntity.getNetData());
    			
    			return;
    		}
    	}
    	
    	System.out.println();
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
    		//server - send snapshot
    		for(NetworkEntity e : networkEntities)
    		{
    			EntityUpdateMessage msg = new EntityUpdateMessage();
    			msg.setEntity(e);
    			
    			for(NetPlayer player : players)
    			{
    				player.addMsg(msg);
    			}
    		}
    	}else
    	{
    		//client - update snapshot
    		for(NetworkEntity entity : networkEntities)
    		{
    			int netId = entity.getNetIdentity().getNetworkId();
    			Entity sceneEntity = getEntityByNetId(netId, scene);
    			
    			if(sceneEntity.hasComponent(NetTransformComponent.class))
    			{
    				NetTransformComponent comp = (NetTransformComponent) sceneEntity.getComponent(NetTransformComponent.class);
    				sceneEntity.removeComponent(comp);
    			}
    			
    			if(sceneEntity.hasComponent(NetDataComponent.class))
    			{
    				NetDataComponent comp = (NetDataComponent) sceneEntity.getComponent(NetDataComponent.class);
    				sceneEntity.removeComponent(comp);
    			}
    			
    			if(entity.getNetTransform() != null)
    			{
    				sceneEntity.addComponent(entity.getNetTransform());
    			}
    			
    			if(entity.getNetData() != null)
    			{
    				sceneEntity.addComponent(entity.getNetData());
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

    public void addMessage(AbstractMessage message, NetPlayer player){
        if(networkType){
            messageListener.addMessage(message,player);
        }
        else{
            messageListener.addMessage(message);
        }
    }

    public void addConnectionEvent(NetPlayer player, boolean connected){
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

    public void setPlayers(ArrayList<NetPlayer> players) {
        this.players = players;
    }

	public ArrayList<NetPlayer> getNetPlayers()
	{
		return players;
	}
}