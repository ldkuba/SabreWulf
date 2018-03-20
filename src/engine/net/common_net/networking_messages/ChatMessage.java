package engine.net.common_net.networking_messages;

public class ChatMessage extends AbstractMessage{
    String source;
    String msg;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
