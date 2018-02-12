package engine.common_net;

import engine.server.core.Player;

public interface MessageListener
{
	public void receiveMessage(AbstractMessage msg, Player source);
}
