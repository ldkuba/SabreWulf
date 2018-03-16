package engine.gui.components;

import engine.application.Application;
import engine.entity.component.TransformComponent;
import engine.gui.GUI;
import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.scene.Scene;
import game.client.Main;
import game.common.actors.Actor;

public class ActorStatus {

	private GUI gui;
	private Actor player;
	private ProgressBar health;
	private ProgressBar energy;
	private Scene scene;

	public ActorStatus(Actor actor, Application app) {
		this.player = actor;
		this.gui = app.getGui();
		health = new ProgressBar(0, 0, 0, 0, null, null, null, this.gui);
		energy = new ProgressBar(0, 0, 0, 0, null, null, null, this.gui);
		scene = Main.gameState.getScene();
	}

	private Vec2 calculatePos() {
		TransformComponent transform = player.getEntity().getTransform();
		float playerX = transform.getPosition().getX();
		float playerY = transform.getPosition().getY();
		Vec3 screenPos = scene.getCamera().getScreenCoordinates(playerX, playerY);
		Vec2 result = new Vec2(screenPos.getX(), screenPos.getY());
		return result;
	}
	
	private void energy(){
		player.getEnergy();
	}
	
	private void health(){
		player.getHealth();
	}
	
	

}
