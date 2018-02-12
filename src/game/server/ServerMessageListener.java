package game.server;

import engine.common_net.AbstractMessage;
import engine.common_net.MessageListener;
import engine.server.core.Player;
import game.networking.ConnectionMessage;
import game.networking.NewLobbyPlayerMessage;
import game.networking.ServerConnectionReplyMessage;
import game.networking.UpdateLobbyPlayerMessage;

public class ServerMessageListener implements MessageListener
{
	private Server server;
	private GameInstance gameInstance;
	ServerMessageListener(Server server){
		this.server=server;
	}

	@Override
	public void receiveMessage(AbstractMessage msg, Player source) {

		if(msg != null){
			System.out.println("Got Messagetype: " + msg.getClass());
		}else
		{
			//System.out.println("Null message");
		}

		if(msg instanceof ConnectionMessage){
			ConnectionMessage m = (ConnectionMessage) msg;
			if(server.isFreeGameInstance()) {
				gameInstance = server.getFreeGameInstance();
				source.setName(m.getName());
				gameInstance.addPlayer(source);

				ServerConnectionReplyMessage scrm = new ServerConnectionReplyMessage();
				scrm.setAccepted(true);
				scrm.setMessage("");
				scrm.setSlot(source.getSlot());
				server.sendTCP(scrm, source);

				for(Player player : gameInstance.getPlayers())
				{
					if(!player.equals(source))
					{
						NewLobbyPlayerMessage n = new NewLobbyPlayerMessage();
						n.setName(player.getName());
						n.setSlot(player.getSlot());
						
						System.out.println("Catching up! " + player.getSlot());
						server.sendTCP(n, source);
						
						if(player.getReady())
						{
							UpdateLobbyPlayerMessage ulpm = new UpdateLobbyPlayerMessage();
							ulpm.setSlot(player.getSlot());
							ulpm.setSelection(player.getCharacterSelection());
							
							server.sendTCP(ulpm, source);
						}
					}
				}
				
				NewLobbyPlayerMessage npm = new NewLobbyPlayerMessage();
				npm.setName(source.getName());
				npm.setSlot(source.getSlot());

				server.informTheRest(npm, source);

			} else {
				ServerConnectionReplyMessage scrm = new ServerConnectionReplyMessage();
				scrm.setAccepted(false);
				scrm.setMessage("All games full");
				scrm.setSlot(-1);
				server.sendTCP(scrm, source);
			}
		} else if(msg instanceof UpdateLobbyPlayerMessage) {
			source.setReady(true);
			UpdateLobbyPlayerMessage ulpm = (UpdateLobbyPlayerMessage) msg;
			source.setCharacterSelection(ulpm.getSelection());
			ulpm.setSlot(source.getSlot());
			server.broadcastTCP(msg);
		}
	}
}
