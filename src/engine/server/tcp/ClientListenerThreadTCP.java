package engine.server.tcp;

import engine.common_net.TCPTalks_trial;
import engine.server.core._PlayerMonitor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientListenerThreadTCP extends Thread{
    TCPTalks_trial trial = null;
    ObjectInputStream ois = null;
    Socket SCSocket = null;
    _PlayerMonitor playerMonitor;

    public ClientListenerThreadTCP(Socket SCSocket, _PlayerMonitor playerMonitor) {
        this.playerMonitor = playerMonitor;
        this.SCSocket = SCSocket;
    }
    public void run(){
        try {
            ois = new ObjectInputStream(SCSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
            try {

                trial = (TCPTalks_trial) ois.readObject();
                System.out.println(trial.getMyX());
                System.out.println(trial.getMyY());

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }

        }

    }
}
