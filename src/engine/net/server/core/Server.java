package engine.net.server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import game.server.GameServer;
/** This class creates the binds between TCP sockets
 *  and manages
 *
 *
 * */
public class Server extends Thread{

    private static ServerSocket coreSocket;
    private static Socket SCSocket; //(SERVER_CLIENT_SOCKET)
    private static CoreClientThread clientCoreThread;

    private GameServer gameServer; //For notifying listeners
    
    public Server(GameServer gs)
    {
    	this.gameServer = gs;
    }

    public void run(){
        try {
            coreSocket = new ServerSocket(4446);
        } catch (IOException e) {
            System.out.println("Port busy. Try another one");
            e.printStackTrace();
        }

        try {
            while(gameServer.players.size()<50) {
                SCSocket = coreSocket.accept();
                Player player = new Player(SCSocket);
                gameServer.addPlayer(player);
                gameServer.addConnectionEvent(player, true);
                clientCoreThread = new CoreClientThread(player, gameServer);
                clientCoreThread.setName("player_"+SCSocket.getInetAddress());
                clientCoreThread.start();
            }
        } catch (IOException e) {
                e.printStackTrace();
        }

    }
}
