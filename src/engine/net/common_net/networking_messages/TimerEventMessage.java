package engine.net.common_net.networking_messages;

public class TimerEventMessage extends AbstractMessage{
    int timePayload;
    public TimerEventMessage(int timePayload){
        this.timePayload = timePayload;
    }

    public int getTimePayload() {
        return timePayload;
    }
}
