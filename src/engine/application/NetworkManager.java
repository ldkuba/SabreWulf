package engine.application;

public class NetworkManager {
    public NetworkManager(boolean networkType, Application app){
        if(networkType){
            setupServerNetwork();
        }

        else{
            setupClientNetwrok();
        }
    }
}
