package engine.net.common_net.networking_messages;

public class RemoveEntityMessage extends AbstractMessage
{
	private int netId;
	
	public RemoveEntityMessage() {}
	
	public int getNetId()
	{
		return netId;
	}
	
	public void setNetId(int netId)
	{
		this.netId = netId;
	}
}
