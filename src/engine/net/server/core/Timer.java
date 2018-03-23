package engine.net.server.core;

public class Timer extends Thread {
    int time;
    GameInstanceManager instanceManager;
    public Timer(int time, GameInstanceManager GIManager){
        this.instanceManager = GIManager;
        this.setName("lobby timer");
        this.time = time;
    }

    public void run(){
        for(int i=time; i>=0; i--){
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            instanceManager.notifyTick(i);
        }
        instanceManager.notifyEndOfCountdown();

        //System.out.print("is this the end");
    }

}
