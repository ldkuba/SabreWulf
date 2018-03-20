package game.client.player;

import java.util.ArrayList;

import engine.net.common_net.networking_messages.RequestAbilityMessage;
import org.lwjgl.glfw.GLFW;

import engine.entity.Entity;
import engine.entity.component.SpriteAnimationComponent;
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
	private ArrayList<Entity> selectedEntities;
	
	public PlayerController(Main main, GameState gs, Scene scene) {
		gamestate = gs;
		this.main = main;
		inputManager = main.getInputManager();
		this.scene = scene;
		//this.main.getSoundManager().invokeSound("movement/footstep", true, false);
	}
	
	public void update()
	{
		//input
		//Hover selection
		selectedEntities = scene.pickEntities((float)inputManager.getMouseX(), (float)inputManager.getMouseY());
		
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
					
					if(e.hasComponent(SpriteAnimationComponent.class))
					{
						SpriteAnimationComponent spriteAnim = (SpriteAnimationComponent) e.getComponent(SpriteAnimationComponent.class);
						spriteAnim.setOverlayColor(new Vec4(1.0f, 0.0f, 0.0f, 0.3f));
					}
					
				}else
				{
					if(e.hasComponent(SpriteComponent.class))
					{
						e.getSprite().setOverlayColor(new Vec4(0.0f, 0.0f, 0.0f, 0.0f));
					}
					
					if(e.hasComponent(SpriteAnimationComponent.class))
					{
						SpriteAnimationComponent spriteAnim = (SpriteAnimationComponent) e.getComponent(SpriteAnimationComponent.class);
						spriteAnim.setOverlayColor(new Vec4(0.0f, 0.0f, 0.0f, 0.0f));
					}
					
				}
			}
		}
		System.out.println("Selected: " + selectedEntities.size());
	}
	
	public void onKeyPress(int key, int action)
	{

		if(key == GLFW.GLFW_KEY_Q && action == GLFW.GLFW_PRESS) {

			Vec3 worldPos = scene.getCamera().getWorldCoordinates((float)main.getInputManager().getMouseX(), (float)main.getInputManager().getMouseY());

			RequestAbilityMessage reqMsg = new RequestAbilityMessage();
			reqMsg.setAbility(gamestate.getActorManager().getLocalPlayer().getRole().getAbilities().get(0));
			reqMsg.setTargetLocation(worldPos);

			main.getClient().sendTCP(reqMsg);

		}
	}

	public void mouseAction(int button, int action)
	{

		boolean clickedEntity = false;

		if(button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_PRESS)
		{
			DesiredLocationMessage msg = new DesiredLocationMessage();
			Vec3 worldPos = scene.getCamera().getWorldCoordinates((float)main.getInputManager().getMouseX(), (float)main.getInputManager().getMouseY());
			msg.setPos(worldPos);
			main.getClient().sendTCP(msg);
			
			if(!main.getSoundManager().getIsMuted()){
				main.getSoundManager().getSoundSource("background/game").setGain(0.5f);				
			}

		} else if (button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS){
			if (!main.getSoundManager().getIsMuted()){
				main.getSoundManager().invokeSound(gamestate.getAttackSoundPath(), false, true);
			}
		}
	}
}
