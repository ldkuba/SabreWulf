package game.client.states;

import org.lwjgl.glfw.GLFW;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.TextComponent;
import engine.entity.component.TransformComponent;
import engine.gui.components.Label;
import engine.gui.components.TextField;
import engine.gui.components.ToggleButton;
import engine.maths.MathUtil;
import engine.maths.Vec3;
import engine.scene.Scene;
import engine.state.AbstractState;
import game.client.Main;
import game.client.player.PlayerController;
import game.common.map.Map;

public class GameState extends AbstractState {
	private Main app;
	private Scene scene;

	// private PlayerManager manager;
	private PlayerController playerController;

	private Map map;

	private int frame = 0;
	private float second = 0;

	private TextField textField;
	
	private float zoom = 10.0f;
	float aspectRatio = Application.s_WindowSize.getX() / Application.s_WindowSize.getY();

	private ToggleButton button;

	public GameState(Main app) {
		this.app = app;
		scene = new Scene(0);
		// manager = new PlayerManager(scene);
		playerController = new PlayerController(app, this);
		map = new Map(scene);
	}

	@Override
	public void keyAction(int key, int action) {
		playerController.onKeyPress(key, action);
		if (key == GLFW.GLFW_KEY_Z && action == GLFW.GLFW_PRESS) {
			zoom += 5.0f;
			scene.getCamera().setProjectionMatrix(
					MathUtil.orthoProjMat(-zoom, zoom, zoom * aspectRatio, -zoom * aspectRatio, 0.1f, 100.0f));
		}

		if (key == GLFW.GLFW_KEY_X && action == GLFW.GLFW_PRESS) {
			zoom -= 5.0f;
			scene.getCamera().setProjectionMatrix(
					MathUtil.orthoProjMat(-zoom, zoom, zoom * aspectRatio, -zoom * aspectRatio, 0.1f, 100.0f));
		}
	}

	@Override
	public void mouseAction(int button, int action) {

	}

	@Override
	public void init() {
		scene.init();
		app.getGui().init(scene);
		//map.init();

		// set up background sound
		app.getSoundManager().invokeSound("background/game", true);
		button = new ToggleButton(20.0f, 20.0f, 10.0f, 10.0f,
				app.getAssetManager().getTexture("res/textures/testNoxus.png"),
				app.getAssetManager().getTexture("res/textures/background.png")) {
			@Override
			public void onClick(boolean toggled) {
				// app.getStateManager().setCurrentState(Main.menuState);
			}
		};
		app.getGui().add(button);
		
		Label label = new Label(40.0f, 10.0f, app.getAssetManager().getFont("fontSprite.png"), 5.0f, 0.7f);
		label.setText("hello");
		app.getGui().add(label);
		
		textField = new TextField(40.0f, 60.0f, app.getAssetManager().getFont("fontSprite.png"), 5.0f, 0.5f, 16);
		app.getGui().add(textField);

		Entity textTest = new Entity(0, "textTest");
		textTest.addComponent(new TransformComponent());
		textTest.addComponent(new TextComponent(app.getAssetManager().getFont("fontSprite.png"), 0.5f, 0.7f));
		((TextComponent) (textTest.getComponent(TextComponent.class))).setText(" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~");
		textTest.getTransform().move(new Vec3(-16.0f, 0.0f, 0.0f));
		
		scene.addEntity(textTest);

		scene.getCamera().setProjectionMatrix(MathUtil.orthoProjMat(-zoom, zoom, zoom * aspectRatio, -zoom * aspectRatio, 0.1f, 100.0f));
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

		float cameraSpeed = 0.04f;

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

		System.out.println(textField.isFocused());
		
		scene.update();
		// manager.getStatuses();
		playerController.update();
		//map.update();
	}

	@Override
	public void deactivate() {

	}
}
