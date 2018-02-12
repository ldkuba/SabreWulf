package game.player;

import org.lwjgl.glfw.GLFW;

import engine.AI.Path;
import engine.entity.component.TransformComponent;
import engine.input.InputManager;
import engine.maths.Vec2;
import engine.maths.Vec3;
import game.Main;
import game.networking.UpdatePathMessage;
import game.states.GameState;

//For handling actual instruction sets to be passed to transformation and interaction components of the players
public class PlayerController {
	
	private TransformComponent transformer;
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
	
	public void onKeyPress(int button, int action)
	{
		
	}
	
	public void onMousePress(int button, int action)
	{
		if(action == GLFW.GLFW_PRESS && button == GLFW.GLFW_MOUSE_BUTTON_2){
			float x = (float) inputManager.getMouseX();
			float y = (float) inputManager.getMouseY();
			Path path = new Path();
			Vec3 pos = main.getPlayerManager().getLocalPlayer().getEntity().getTransform().getPosition();
			Vec2 start = new Vec2(pos.getX(), pos.getY());
			Vec2 goal = new Vec2(x, y);
			path.setStart(start);
			path.setGoal(goal);
			
			UpdatePathMessage msg = new UpdatePathMessage();
			msg.setPath(path);
			
			main.getClient().sendTCP(msg);
		}
	}
	
	public void movePlayer(Vec3 position){
		transformer.setPosition(position);
	}
	
	public void rotatePlayer(Vec3 angle){
		transformer.setRotationAngles(angle);
	}
	
	public void scalePlayer(Vec3 scale){
		transformer.setScale(scale);
	}
}
