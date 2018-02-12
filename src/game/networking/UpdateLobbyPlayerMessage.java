package game.networking;

import engine.common_net.AbstractMessage;

public class UpdateLobbyPlayerMessage extends AbstractMessage
{
	private int selection;
	private int slot;

	public UpdateLobbyPlayerMessage(){}

	public void setSelection(int selection)
    {
        this.selection = selection;
    }

    public int getSelection()
    {
        return selection;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}
