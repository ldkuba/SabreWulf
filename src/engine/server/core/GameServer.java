package engine.server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import engine.server.udp.ServerUDPManager;
import engine.common_net.AbstractMessage;
import game.server.Server;

public class GameServer extends Thread{

    private static ServerSocket coreSocket; // Server Socket, named core (it's only one there)
    private static Socket SCSocket; //(SERVER_CLIENT_SOCKET)
    private static CoreClientThread clientCoreThread;
    
    private static ServerUDPManager serverUDPManager = null;	//Receives and sends UDP packets.

    private Server server; //For notifying listeners
    
    public GameServer(Server gs)
    {
    	this.server = gs;
    }

    public void run(){
        // Creating a server socket on some random port for TCP
        try {
            coreSocket = new ServerSocket(4445);
        } catch (IOException e) {
            System.out.println("Port busy. Try another one");
            e.printStackTrace();
        }

        try {
            // While(true) for the moment, listen to incoming connections
            while(true) {
                SCSocket = coreSocket.accept();
                clientCoreThread = new CoreClientThread(SCSocket, server);

                clientCoreThread.setName("player_"+SCSocket.getInetAddress());
                server.notifyConnectionListenersConnected(SCSocket);
                clientCoreThread.start();
            }
        } catch (IOException e) {
                e.printStackTrace();


        }

    }
    
    public void startUDPManager() {
    	serverUDPManager.start();
    }
    
    public void sendUDP(AbstractMessage msg) {
    	serverUDPManager.addToQueueStates(msg);
    }
}
