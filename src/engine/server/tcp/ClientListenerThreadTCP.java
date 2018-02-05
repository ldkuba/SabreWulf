package engine.server.tcp;

import engine.common_net.TCPTalks_trial;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientListenerThreadTCP extends Thread{
    TCPTalks_trial trial = null;
    ObjectInputStream ois = null;
    Socket SCSocket = null;

    public ClientListenerThreadTCP(Socket SCSocket) throws IOException {
        this.SCSocket = SCSocket;
        ois = new ObjectInputStream(SCSocket.getInputStream());
    }
    public void run(){
        while(true){

            try {

                trial = (TCPTalks_trial) ois.readObject();
                System.out.println(trial.getMyX());
                System.out.println(trial.getMyY());

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
