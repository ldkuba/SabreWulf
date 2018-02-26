package engine.net.server.core;

import java.io.Serializable;

/**
 * This data structure hold the necessary components
 * of a player, that can be sent to the client in LobbyUpdateMessage
 */
public class PlayerPayload implements Serializable {
    private String name;
    private boolean isReady;
    private int characterSelection;
    private int currentGame;
	private int netPlayerId;
    
    public PlayerPayload(){
    	this.currentGame = -1;
        this.characterSelection = -1;
        this.isReady = false;
        this.name = "temp";
        this.netPlayerId = -1;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setCharacterSelection(int selection)
    {
        characterSelection = selection;
    }

    public int getCharacterSelection()
    {
        return characterSelection;
    }

    public void setReady(boolean ready){
        isReady=ready;
    }

    public boolean getReady(){
        return isReady;
    }

	public int getCurrentGame()
	{
		return currentGame;
	}

	public void setCurrentGame(int currentGame)
	{
		this.currentGame = currentGame;
	}

	public int getNetPlayerId()
	{
		return netPlayerId;
	}

	public void setNetPlayerId(int netPlayerId)
	{
		this.netPlayerId = netPlayerId;
	}
}
