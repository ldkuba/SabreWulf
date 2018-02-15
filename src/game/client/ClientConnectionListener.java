package game.client;

import engine.net.client.Client;
import engine.net.common_net.ConnectionListener;
import engine.net.server.core.Player;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientConnectionListener implements ConnectionListener
{
	private Main main;
	private CopyOnWriteArrayList<Boolean> connectionEventQueue;

	public ClientConnectionListener(Main main){
		connectionEventQueue = new CopyOnWriteArrayList<Boolean>();
		this.main = main;
	}
	public void addConnectionEvent(boolean connected){
		connectionEventQueue.add(new Boolean(connected));
	}

	@Override
	public void addConnectionEvent(boolean connected, Player player) {

	}

	@Override
	public void handleConnectionQueue(){
		for(Boolean b : connectionEventQueue){
			if(b){
				clientConnected();
			}

			else {
				clientDisconnected();
			}
		}
		connectionEventQueue.clear();
	}

	@Override
	public void clientConnected(){
	}

	@Override
	public void clientConnected(Player player) {

	}

	@Override
	public void clientDisconnected(){
	}

	@Override
	public void clientDisconnected(Player player) {

	}
}
