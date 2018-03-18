package engine.gui.components;

import engine.application.Application;
import engine.entity.component.CustomComponent;
import engine.entity.component.NetTransformComponent;
import engine.gui.GUI;
import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.scene.Scene;
import game.client.Main;
import game.common.actors.Actor;

public class ActorStatus extends CustomComponent {

	private GUI gui;
	private Scene scene;
	private Actor actor;
	private ProgressBar health;
	private ProgressBar energy;
	
	private float paddingHealth = 80.0f;
	private float paddingEnergy = 40.0f;
	
	public ActorStatus(Actor actor, Application app) {
		this.actor = actor;
		this.gui = app.getGui();
		scene = Main.gameState.getScene();
		Vec3 temp = new Vec3(0.0f, 0.0f, 0.0f); //set this to player starting position (currently a null pointer ex)
		Vec2 screen = scene.getCamera().getScreenCoordinates(temp);
		health = new ProgressBar(screen.getX(), screen.getY()+paddingHealth, 200.0f, 20.0f, app.getAssetManager().getTexture("res/textures/gui/bars/health_bar_background.png"),app.getAssetManager().getTexture("res/textures/gui/bars/health_bar.png"),app.getAssetManager().getFont("fontSprite.png"), this.gui);
		energy = new ProgressBar(screen.getX(), screen.getY(), 200.0f, 20.0f, app.getAssetManager().getTexture("res/textures/gui/bars/energy_bar_background.png"),app.getAssetManager().getTexture("res/textures/gui/bars/energy_bar.png"),app.getAssetManager().getFont("fontSprite.png"), this.gui);		

	}
	
	public void update(){
		Vec2 newPos = calculatePos();
		//energy bar
		float newEnergy = actor.getEnergy();
		energy.setPosition(newPos.getX(), newPos.getY()+paddingHealth/Application.s_Viewport.getX());
		energy.setBar(newEnergy);
		//health bar
		float newHealth = actor.getHealth();
		health.setPosition(newPos.getX(), newPos.getY()+paddingEnergy/Application.s_Viewport.getX());
		health.setBar(newHealth);
	}
	
	private Vec2 calculatePos() {
		NetTransformComponent transform = actor.getEntity().getNetTransform();
		if(transform != null)
		{
			Vec2 screenPos = scene.getCamera().getScreenCoordinates(Vec3.add(transform.getPosition(), new Vec3(-2.0f, 3.25f, 0.0f)));
			Vec2 result = new Vec2(screenPos.getX(), screenPos.getY());
			return result;
		}
		
		return new Vec2(0.0f, 0.0f);
	}
}
