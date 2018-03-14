package game.client.player;

import org.lwjgl.glfw.GLFW;

import engine.input.InputManager;
import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.net.common_net.networking_messages.DesiredLocationMessage;
import engine.scene.Scene;
import game.client.Main;
import game.client.states.GameState;

//For handling actual instruction sets to be passed to transformation and interaction components of the players
public class PlayerController {
	
	private InputManager inputManager;
	private Main main;
	private GameState gamestate;
	private Scene scene;
	
	public PlayerController(Main main, GameState gs, Scene scene) {
		gamestate = gs;
		this.main = main;
		inputManager = main.getInputManager();
		this.scene = scene;
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
			DesiredLocationMessage msg = new DesiredLocationMessage();
			Vec3 worldPos = scene.getCamera().getWorldCoordinates((float)main.getInputManager().getMouseX(), (float)main.getInputManager().getMouseY());
			msg.setPos(worldPos);
			
			main.getClient().sendTCP(msg);
			if(!main.getSoundManager().getIsMuted()){
				main.getSoundManager().getSoundSource("background/game").setGain(0.3f);
				main.getSoundManager().invokeSound("movement/footstep", false, false);
				/*System.out.println("----------");
				float v = playerManager.getPlayer(0).getMovementSpeed();
				Vec2 cur = playerManager.getPlayer(0).getPosition();
				System.out.println(cur.getX());
				System.out.println(cur.getY());
				System.out.println(worldPos.getX());
				System.out.println(worldPos.getY());
				System.out.println(v);
				float s = Math.abs(worldPos.getX() + pos.getY());
				int t = (int)(s/v);
				System.out.println(t);*/
			}
		} else if (button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS){
			if (!main.getSoundManager().getIsMuted()){
				main.getSoundManager().invokeSound("attack/a2", false, true);
			}
		}
	}
}
