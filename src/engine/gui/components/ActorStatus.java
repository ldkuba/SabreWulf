package engine.gui.components;

import engine.gui.GUI;
import game.client.Main;
import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.scene.Scene;
import game.common.actors.Actor;
import engine.application.Application;
import engine.entity.component.TransformComponent;

public class ActorStatus {

	private GUI gui;
	private Scene scene;
	private Actor player;
	private ProgressBar health;
	private ProgressBar energy;
	private TransformComponent transform;
	
	private float playerX;
	private float playerY;
	private float paddingHealth = 4.0f;
	private float paddingEnergy = 2.0f;
	
	public ActorStatus(Actor actor, Application app) {
		this.player = actor;
		this.gui = app.getGui();
		this.transform = player.getEntity().getTransform();
		this.playerX = transform.getPosition().getX();
		this.playerY = transform.getPosition().getY();
		health = new ProgressBar(playerX, playerY+paddingHealth, 12.25f, 2.0f, app.getAssetManager().getTexture("res/textures/gui/bars/health_bar_background.png"),app.getAssetManager().getTexture("res/textures/gui/bars/health_bar.png"),app.getAssetManager().getFont("fontSprite.png"), this.gui);
		energy = new ProgressBar(playerX, playerY+paddingEnergy, 12.25f, 2.0f, app.getAssetManager().getTexture("res/textures/gui/bars/energy_bar_background.png"),app.getAssetManager().getTexture("res/textures/gui/bars/energy_bar.png"),app.getAssetManager().getFont("fontSprite.png"), this.gui);		
		scene = Main.gameState.getScene();
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
		Vec3 screenPos = scene.getCamera().getScreenCoordinates(playerX, playerY);
		Vec2 result = new Vec2(screenPos.getX(), screenPos.getY());
		return result;
	}
}
