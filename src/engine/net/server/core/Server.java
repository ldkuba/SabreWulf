package engine.net.server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import game.common.config;
import game.server.GameServer;
/** This class creates the binds between TCP sockets
 *  and manages
 *
 *
 * */
public class Server extends Thread{

    private static ServerSocket coreSocket;
    private static Socket SCSocket;
    private static CoreClientThread clientCoreThread;

    private GameServer gameServer;
    
    public Server(GameServer gs)
    {
    	this.gameServer = gs;
    }

    public void run(){
        try {
            coreSocket = new ServerSocket(config.TCPPort);
        } catch (IOException e) {
            System.out.println("Port busy. Try another one");
            e.printStackTrace();
        }

        try {
            while(gameServer.players.size()<config.globalConnectionsLimit) {
                SCSocket = coreSocket.accept();

                NetPlayer player = new NetPlayer(SCSocket);

                gameServer.addPlayer(player);
                gameServer.addConnectionEvent(player, true);

                clientCoreThread = new CoreClientThread(player, gameServer);
                clientCoreThread.setName("Player_"+SCSocket.getInetAddress());
                clientCoreThread.start();
            }
        } catch (IOException e) {
                e.printStackTrace();
        }

    }
}
