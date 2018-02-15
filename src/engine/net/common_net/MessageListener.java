package engine.net.common_net;

import engine.net.server.core.Player;

public interface MessageListener
{
	public void receiveMessage(AbstractMessage msg, Player source);
}
