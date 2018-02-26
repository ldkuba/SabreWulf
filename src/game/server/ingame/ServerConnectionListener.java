package game.server.ingame;

import engine.net.common_net.ConnectionListener;
import engine.net.server.core.NetPlayer;

import java.util.concurrent.CopyOnWriteArrayList;

public class ServerConnectionListener implements ConnectionListener
{
	private ServerMain main;
	private CopyOnWriteArrayList<Boolean> connectionEventQueue;

	public ServerConnectionListener(ServerMain main){
		connectionEventQueue = new CopyOnWriteArrayList<Boolean>();
		this.main = main;
	}
	public void addConnectionEvent(boolean connected){
		connectionEventQueue.add(new Boolean(connected));
	}

	@Override
	public void addConnectionEvent(boolean connected, NetPlayer player) {

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
	public void clientConnected(NetPlayer player) {

	}

	@Override
	public void clientDisconnected(){
	}

	@Override
	public void clientDisconnected(NetPlayer player) {

	}
}
