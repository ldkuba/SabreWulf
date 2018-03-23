package engine.gui.components;

import engine.application.Application;
import engine.entity.component.CustomComponent;
import engine.entity.component.NetTransformComponent;
import engine.gui.GUI;
import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.scene.Scene;
import game.client.Main;
import game.common.actors.Actor;
import game.common.actors.Player;

/**
 * Actor Status is responsible for creating and updating the status bars that
 * appear above the character
 * 
 * @author SabreWulf
 *
 */
public class ActorStatus {

	private GUI gui;
	private Scene scene;
	private Actor actor;
	private ProgressBar health;
	private ProgressBar energy;
	private Label playerName;
	private Application app;

	private float paddingHealth = 80.0f;
	private float paddingEnergy = 40.0f;
	private float paddingName = -20.0f;

	/**
	 * Create a health and energy bar for the given actor
	 * 
	 * @param actor
	 * @param application
	 */
	public ActorStatus(Actor actor, Application application) {
		this.app = application;
		this.actor = actor;
		this.gui = app.getGui();
		scene = Main.gameState.getScene();
		NetTransformComponent transform = actor.getEntity().getNetTransform();
		Vec3 temp = transform.getPosition();
		Vec2 screen = scene.getCamera().getScreenCoordinates(temp);
		health = new ProgressBar(screen.getX(), screen.getY() + paddingHealth, 200.0f, 20.0f,
				app.getAssetManager().getTexture("res/textures/gui/bars/health_bar_background.png"),
				app.getAssetManager().getTexture("res/textures/gui/bars/health_bar.png"),
				app.getAssetManager().getFont("fontSprite.png"), this.gui);
		energy = new ProgressBar(screen.getX(), screen.getY(), 200.0f, 20.0f,
				app.getAssetManager().getTexture("res/textures/gui/bars/energy_bar_background.png"),
				app.getAssetManager().getTexture("res/textures/gui/bars/energy_bar.png"),
				app.getAssetManager().getFont("fontSprite.png"), this.gui);
		playerName = new Label(screen.getX(), screen.getY() + paddingName,
				app.getAssetManager().getFont("fontSprite.png"), 4.0f, 6.0f, new Vec4(1.0f, 1.0f, 1.0f, 1.0f));

		if (actor instanceof Player) {
			Player player = (Player) actor;
			playerName.setText(player.getName());
		}
		gui.add(playerName);
	}

	/**
	 * Update the position of the actor status bars each frame
	 */
	public void update() {
		Vec2 newPos = calculatePos();
		/* energy bar */
		float newEnergy = actor.getEnergy();
		float maxEnergy = actor.getEnergyLimit();
		energy.setPosition(newPos.getX(), newPos.getY() + paddingHealth / Application.s_Viewport.getX(), newEnergy,
				maxEnergy);
		/* health bar */
		float newHealth = actor.getHealth();
		float maxHealth = actor.getHealthLimit();
		health.setPosition(newPos.getX(), newPos.getY() + paddingEnergy / Application.s_Viewport.getX(), newHealth,
				maxHealth);
		/* name label */
		playerName.setPosition(newPos.getX(), newPos.getY() + paddingName / Application.s_Viewport.getY());
	}

	/**
	 * Calculate the screen position of the actor
	 * 
	 * @return
	 */
	private Vec2 calculatePos() {
		NetTransformComponent transform = actor.getEntity().getNetTransform();
		if (transform != null) {
			Vec2 screenPos = scene.getCamera()
					.getScreenCoordinates(Vec3.add(transform.getPosition(), new Vec3(-2.0f, 3.25f, 0.0f)));
			Vec2 result = new Vec2(screenPos.getX(), screenPos.getY());
			return result;
		}

		return new Vec2(0.0f, 0.0f);
	}
}
