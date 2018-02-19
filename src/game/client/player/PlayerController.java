package game.client.player;

import org.lwjgl.glfw.GLFW;

import engine.AI.Path;
import engine.entity.component.TransformComponent;
import engine.input.InputManager;
import engine.maths.MathUtil;
import engine.maths.Vec2;
import engine.maths.Vec3;
import game.client.Main;
import game.client.states.GameState;

//For handling actual instruction sets to be passed to transformation and interaction components of the players
public class PlayerController {
	
	private InputManager inputManager;
	private Main main;
	private GameState gamestate;
	
	public PlayerController(Main main, GameState gs) {
		gamestate = gs;
		this.main = main;
		inputManager = main.getInputManager();
	}
	
	public void update()
	{
		//input
				
	}
	
	public void onKeyPress(int key, int action)
	{
		
	}
	
	public void mouseAction(int button, int action)
	{
		if(button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_PRESS)
		{
			
		}
	}
}
