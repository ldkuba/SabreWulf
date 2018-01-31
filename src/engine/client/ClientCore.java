package engine.client;

import java.io.IOException;
import java.net.Socket;

public class ClientCore extends Thread{
    Socket CSSocket=null; //Client-Server Socket TCP
    public void run(){
        try {
            CSSocket = new Socket("8.8.8.8",5800);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true);
    }
}
