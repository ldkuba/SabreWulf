package game.client.player;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.input.InputManager;
import engine.maths.Vec3;
import engine.maths.Vec4;
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
		//Hover selection
		ArrayList<Entity> selectedEntities = scene.pickEntities((float)inputManager.getMouseX(), (float)inputManager.getMouseY());
		
		for(Entity e : scene.getEntities())
		{
			if(e.hasTag("Targetable"))
			{
				if(selectedEntities.contains(e))
				{
					if(e.hasComponent(SpriteComponent.class))
					{
						e.getSprite().setOverlayColor(new Vec4(1.0f, 0.0f, 0.0f, 0.4f));
					}
				}else
				{
					if(e.hasComponent(SpriteComponent.class))
					{
						e.getSprite().setOverlayColor(new Vec4(0.0f, 0.0f, 0.0f, 0.0f));
					}
				}
			}
		}
		
		System.out.println("Selected: " + selectedEntities.size());
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
		}
	}
}
