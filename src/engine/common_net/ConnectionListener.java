package engine.common_net;

public interface ConnectionListener
{
	public void clientConnected(/* To be decided what kind of client identifier you use */);
	
	public void clientDisconnected(/* Same as above */);
}
