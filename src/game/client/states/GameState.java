package game.client.states;

import org.lwjgl.glfw.GLFW;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.SpriteAnimationComponent;
import engine.entity.component.TextComponent;
import engine.entity.component.TransformComponent;
import engine.gui.components.Label;
import engine.gui.components.Sprite;
import engine.maths.MathUtil;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.scene.Scene;
import engine.state.AbstractState;
import game.client.Main;
import game.client.player.PlayerController;
import game.common.actors.Player;
import game.common.classes.classes.Elf;
import game.common.classes.classes.Knight;
import game.common.classes.classes.Wizard;
import game.common.map.Map;
import game.common.player.PlayerManager;

public class GameState extends AbstractState {

	/**
	 * Dummy Player creator
	 */

	private boolean dummy = true;
	private Player dummyPlayer;

	/*--------------------------*/

	private Main app;
	private Scene scene;
	private PlayerController playerController;

	private static PlayerManager playerManager;

	private Map map;

	private int frame = 0;
	private float second = 0;

	private Sprite spellBar;

	private float zoom = 10.0f;
	float aspectRatio = Application.s_WindowSize.getX() / Application.s_WindowSize.getY();

	public GameState(Main app) {
		this.app = app;
		scene = new Scene(0, app);
		playerManager = new PlayerManager(scene);
		playerController = new PlayerController(app, this, scene, playerManager);
		map = new Map(scene, "res/textures/map");
	}

	@Override
	public void keyAction(int key, int action) {
		playerController.onKeyPress(key, action);

		if (key == GLFW.GLFW_KEY_Z && action == GLFW.GLFW_PRESS) {
			zoom += 5.0f;
			scene.getCamera().setProjectionMatrix(
					MathUtil.orthoProjMat(-zoom, zoom, zoom * aspectRatio, -zoom * aspectRatio, 1.0f, 100.0f));
			app.setViewport(zoom * aspectRatio, zoom);
		}
		
		if(key == GLFW.GLFW_KEY_D && action == GLFW.GLFW_PRESS)
		{
			System.out.println("D FOR DEBUG");
		}

		if (key == GLFW.GLFW_KEY_X && action == GLFW.GLFW_PRESS) {
			zoom -= 5.0f;
			scene.getCamera().setProjectionMatrix(
					MathUtil.orthoProjMat(-zoom, zoom, zoom * aspectRatio, -zoom * aspectRatio, 1.0f, 100.0f));
			app.setViewport(zoom * aspectRatio, zoom);
		}
	}

	@Override
	public void mouseAction(int button, int action) {
		playerController.mouseAction(button, action);
	}

	@Override
	public void init() {
		scene.init();
		app.getGui().init(scene);

		// Add players
		for (int i = 0; i < app.getNetworkManager().getNetPlayers().size(); i++) {
			Player player = new Player(i, app.getNetworkManager().getNetPlayers().get(i).getName(), app);
			// here we would set up more stuff related to the player like class (done),
			// items, starting position(done), team(done) etc.
			int characterSelection = app.getNetworkManager().getNetPlayers().get(i).getChar();
			characterSelection = 1;
			System.out.println("Character Selected: " +characterSelection);
			switch (characterSelection) {
			case 1:
				player.setRole(new Wizard());

				break;
			case 2:
				player.setRole(new Knight());
				break;
			case 3:
				player.setRole(new Elf());
				break;
			}

			if (i >= 0 && i < 3) {
				player.setTeam(1);
				
			}

			else {
				player.setTeam(2);
			}
			playerManager.addPlayer(player);

		}

		if (dummy) {
			dummyPlayer = new Player(1, "Dummy", app);
			dummyPlayer.setTeam(1);
			dummyPlayer.setRole(new Wizard());
			playerManager.addPlayer(dummyPlayer);
		}
		map.init(app.getAssetManager());


		// set up background sound
		app.getSoundManager().invokeSound("background/game", true);

		Label label = new Label(40.0f, 10.0f, app.getAssetManager().getFont("fontSprite.png"), 5.0f, 0.7f,
				new Vec4(1.0f, 0.0f, 0.0f, 1.0f));
		label.setText("hello");
		app.getGui().add(label);

		spellBar = new Sprite(25.0f, 85.0f, 50.0f, 15.0f,
				app.getAssetManager().getTexture("res/textures/spellbar.png"));
		app.getGui().add(spellBar);

		Entity textTest = new Entity("textTest");
		textTest.addComponent(new TransformComponent());
		textTest.addComponent(new TextComponent(app.getAssetManager().getFont("fontSprite.png"), 0.5f, 0.7f,
				new Vec4(1.0f, 1.0f, 1.0f, 1.0f)));
		((TextComponent) (textTest.getComponent(TextComponent.class))).setText(
				" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~");
		textTest.getTransform().move(new Vec3(-16.0f, 0.0f, 0.0f));

		scene.addEntity(textTest);

		Entity animTest = new Entity("animTest");
		animTest.addComponent(new TransformComponent());
		animTest.addComponent(new SpriteAnimationComponent(
				app.getAssetManager().getTexture("res/textures/Cursor/cursorMovementAnimated.png"), 4, 0, 11, 3.0f,
				3.0f, 2));
		animTest.getTransform().setPosition(new Vec3(6.0f, -6.0f, 0.0f));

		scene.addEntity(animTest);

		scene.getCamera().setProjectionMatrix(
				MathUtil.orthoProjMat(-zoom, zoom, zoom * aspectRatio, -zoom * aspectRatio, 1.0f, 100.0f));
		scene.getCamera().setPosition(new Vec3(0.0f, 0.0f, -5.0f));
	}

	@Override
	public void render() {
		scene.render();
		// manager.render(); not used anymore
	}

	@Override
	public void update() {
		// FPS Counter
		if (GLFW.glfwGetTime() - second >= 1.0f) {
			second += 1.0f;
			System.out.println("FPS: " + frame);
			frame = 0;
		}
		frame++;

		float cameraSpeed = 0.08f;

		if (app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_LEFT)) {
			scene.getCamera().move(new Vec3(-cameraSpeed, 0.0f, 0.0f));
		}

		if (app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_RIGHT)) {
			scene.getCamera().move(new Vec3(cameraSpeed, 0.0f, 0.0f));
		}

		if (app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_UP)) {
			scene.getCamera().move(new Vec3(0.0f, cameraSpeed, 0.0f));
		}

		if (app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
			scene.getCamera().move(new Vec3(0.0f, -cameraSpeed, 0.0f));
		}

		scene.update();
		// manager.getStatuses();
		playerController.update();
	}

	public PlayerManager getPlayerManager() {
		return this.playerManager;
	}
	
	public Map getMap()
	{
		return this.map;
	}
	
	public void respawn() {
		// add filter here?
	}

	@Override
	public void deactivate() {

	}
}
