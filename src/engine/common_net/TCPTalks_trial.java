package engine.common_net;

public class TCPTalks_trial extends AbstractMessage {
    int myX, myY;
    public TCPTalks_trial(int initialX, int initialY){
        myX = initialX;
        myY = initialY;
    }

    public void setMyX(int myX) {
        this.myX = myX;
    }

    public void setMyY(int myY) {
        this.myY = myY;
    }

    public int getMyX() {
        return myX;
    }

    public int getMyY() {
        return myY;
    }
}
