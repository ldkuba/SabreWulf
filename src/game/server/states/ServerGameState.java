package game.server.states;

import engine.scene.Scene;
import engine.state.AbstractState;


public class ServerGameState extends AbstractState
{
	private ServerMain app;
	private Scene scene;
	
	public ServerGameState(ServerMain app)
	{
		this.app = app;
	}
		
	@Override
	public void keyAction(int key, int action)
	{
		
	}

	@Override
	public void mouseAction(int button, int action)
	{
		
	}

	@Override
	public void init()
	{
			
	}

	@Override
	public void render()
	{
		
	}

	@Override
	public void update()
	{
		System.out.println("running game");
	}

	@Override
	public void deactivate()
	{
		
	}
}
