package game.method;

import game.Main;

public class UpdateLobbyPlayer extends Method
{
	private int slot, selection;
	
	public UpdateLobbyPlayer(int slot, int selection)
	{
		this.slot = slot;
		this.selection = selection;
	}
	
	public void execute()
	{
		Main.lobbyState.updatePlayer(slot, selection);
	}
	
}
