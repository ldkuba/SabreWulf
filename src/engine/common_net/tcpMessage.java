package engine.common_net;

public class tcpMessage extends AbstractMessage
{
    String content;

    public tcpMessage(String content){
        this.content=content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
