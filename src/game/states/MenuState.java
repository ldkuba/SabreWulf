package game.states;

import org.lwjgl.glfw.GLFW;

import engine.scene.Scene;
import engine.state.AbstractState;
import game.Main;

public class MenuState extends AbstractState
{
	private Main app;
	private Scene scene;
	
	
	
	public MenuState(Main app)
	{
		this.app = app;
		scene = new Scene(0);
	}
	
	@Override
	public void keyAction(int key, int action) {
		if(key == GLFW.GLFW_KEY_J && action == GLFW.GLFW_PRESS)
		{
//			app.client.
//			app.getStateManager().setCurrentState(Main.lobbyState);
		}
	}

	@Override
	public void mouseAction(int button, int action) {
		
	}

	@Override
	public void init() {
		scene.init();
	}

	@Override
	public void render() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void deactivate() {
		
	}
	//TODO: Fill in state methods to make them functional
}
