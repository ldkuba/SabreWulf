package engine.server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import game.server.GameServer;

public class Server extends Thread{

    private static ServerSocket coreSocket; // GameServer Socket, named core (it's only one there)
    private static Socket SCSocket; //(SERVER_CLIENT_SOCKET)
    private static CoreClientThread clientCoreThread;
    
   // private static ServerUDPManager serverUDPManager = null;	//Receives and sends UDP packets.

    private GameServer gameServer; //For notifying listeners
    
    public Server(GameServer gs)
    {
    	this.gameServer = gs;
    }

    public void run(){
        // Creating a gameServer socket on some random port for TCP
        try {
            coreSocket = new ServerSocket(4446);
        } catch (IOException e) {
            System.out.println("Port busy. Try another one");
            e.printStackTrace();
        }

        try {
            // While(true) for the moment, listen to incoming connections
            while(true) {
                SCSocket = coreSocket.accept();
                Player player = new Player(SCSocket);
                gameServer.addPlayer(player);
                gameServer.notifyConnectionListenersConnected(player);
                clientCoreThread = new CoreClientThread(player, gameServer);
                clientCoreThread.setName("player_"+SCSocket.getInetAddress());
                clientCoreThread.start();
            }
        } catch (IOException e) {
                e.printStackTrace();
        }

    }


   /* public void startUDPManager() {
    	serverUDPManager.start();
    }
    
    public void sendUDP(AbstractMessage msg) {
    	serverUDPManager.addToQueueStates(msg);
    }
    */
}
