package game.method;

import engine.state.AbstractState;
import game.Main;

public class SetCurrentGameState extends Method
{	
	private Main app;
	private AbstractState state;
	
	public SetCurrentGameState(Main app, AbstractState state)
	{
		this.app = app;
		this.state = state;
	}
	
	public void execute()
	{
		app.getStateManager().setCurrentState(state);
	}
}
