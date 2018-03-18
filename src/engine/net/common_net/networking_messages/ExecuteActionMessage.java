package engine.net.common_net.networking_messages;

import game.common.logic.actions.Action;

public class ExecuteActionMessage extends AbstractMessage
{
	private Action action;
	
	public ExecuteActionMessage() {}

	public Action getAction()
	{
		return action;
	}

	public void setAction(Action action)
	{
		this.action = action;
	}
}
