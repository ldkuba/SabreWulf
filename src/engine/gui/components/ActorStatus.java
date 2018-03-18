package engine.gui.components;

import engine.gui.GUI;
import game.client.Main;
import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.scene.Scene;
import game.common.actors.Actor;
import engine.application.Application;
import engine.entity.component.CustomComponent;
import engine.entity.component.TransformComponent;

public class ActorStatus extends CustomComponent {

	private GUI gui;
	private Scene scene;
	private Actor player;
	private ProgressBar health;
	private ProgressBar energy;
	
	private float paddingHealth = 4.0f;
	private float paddingEnergy = 2.0f;
	
	public ActorStatus(Actor actor, Application app) {
		this.player = actor;
		this.gui = app.getGui();
		scene = Main.gameState.getScene();
		Vec2 temp = new Vec2(0.0f,0.0f); //set this to player starting position (currently a null pointer ex)
		Vec2 screen = scene.getCamera().getScreenCoordinates(temp);
		health = new ProgressBar(screen.getX(), screen.getY()+paddingHealth, 12.25f, 2.0f, app.getAssetManager().getTexture("res/textures/gui/bars/health_bar_background.png"),app.getAssetManager().getTexture("res/textures/gui/bars/health_bar.png"),app.getAssetManager().getFont("fontSprite.png"), this.gui);
		energy = new ProgressBar(screen.getX(), screen.getY(), 12.25f, 2.0f, app.getAssetManager().getTexture("res/textures/gui/bars/energy_bar_background.png"),app.getAssetManager().getTexture("res/textures/gui/bars/energy_bar.png"),app.getAssetManager().getFont("fontSprite.png"), this.gui);		

	}
	
	public void update(){
		Vec2 newPos = calculatePos();
		//energy bar
		float newEnergy = player.getEnergy();
		energy.setBar(newEnergy);
		energy.setPosition(newPos.getX(), newPos.getY()+paddingHealth);
		//health bar
		float newHealth = player.getHealth();
		health.setBar(newHealth);
		health.setPosition(newPos.getX(), newPos.getY()+paddingEnergy);
	}
	
	private Vec2 calculatePos() {
		TransformComponent transform = player.getEntity().getTransform();
		float playerX = transform.getPosition().getX();
		float playerY = transform.getPosition().getY();
		Vec2 arr = new Vec2(playerX, playerY);
		Vec2 screenPos = scene.getCamera().getScreenCoordinates(arr);
		Vec2 result = new Vec2(screenPos.getX(), screenPos.getY());
		return result;
	}
}
