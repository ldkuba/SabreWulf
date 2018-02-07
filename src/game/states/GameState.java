package game.states;

import engine.scene.Scene;
import engine.state.AbstractState;
import game.Main;

public class GameState extends AbstractState
{
	private Main app;
	private Scene scene;
	
	public GameState(Main app)
	{
		this.app = app;
	}
	
	@Override
	public void keyAction(int key, int action) {
		
	}

	@Override
	public void mouseAction(int button, int action) {
		
	}

	@Override
	public void init() {
		
	}

	@Override
	public void render() {
		scene.render();
	}

	@Override
	public void update() {
		scene.update();
	}

	@Override
	public void deactivate() {
		
	}
	//TODO: Fill in state methods to make them functional
}
