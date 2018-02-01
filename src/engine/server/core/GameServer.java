package engine.server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer extends Thread{

    private static ServerSocket coreSocket; // Server Socket, named core (it's only one there)
    private static Socket SCSocket; //(SERVER_CLIENT_SOCKET)
    private static CoreClientThread clientCoreThread;
    private static _PlayerMonitor pMonitor;
    public void run(){
        // Creating a server socket on some random port for TCP
        try {
            pMonitor = new _PlayerMonitor();
            pMonitor.start();
            System.out.println("hi");
            coreSocket = new ServerSocket(5800);
        } catch (IOException e) {
            System.out.println("Port busy. Try another one");
            e.printStackTrace();
        }

        try {
            // While(true) for the moment, listen to incoming connections
            while(true) {
                SCSocket = coreSocket.accept();
                clientCoreThread = new CoreClientThread(SCSocket, pMonitor);
                clientCoreThread.setName("Player_Thread"+pMonitor.getNoPlayers());
                clientCoreThread.start();
            }
        } catch (IOException e) {
                e.printStackTrace();
        }


    }
}
