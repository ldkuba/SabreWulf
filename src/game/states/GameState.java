package game.states;

import org.lwjgl.glfw.GLFW;

import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.graphics.texture.Texture;
import engine.maths.MathUtil;
import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.scene.Scene;
import engine.state.AbstractState;
import game.Main;
import game.player.PlayerController;
import game.player.PlayerManager;

public class GameState extends AbstractState {
	private Main app;
	private Scene scene;
	private PlayerManager manager;

	private PlayerController playerController;

	private Entity player;

	private int frame = 0;

	private float second = 0;

	private float angle = 0.0f;

	public GameState(Main app) {
		this.app = app;
		scene = new Scene(0);
		manager = new PlayerManager(scene);
	}

	@Override
	public void keyAction(int key, int action) {
		playerController.onKeyPress(key, action);
	}

	@Override
	public void mouseAction(int button, int action) {
		playerController.onMousePress(button, action);
	}

	@Override
	public void mouseAction(int button, int action) {

	}

	@Override
	public void init() {
		scene.init();

		for (int i = 0; i < 100; i++) {
			Entity testEntity = new Entity(0, "Test Entity");
			testEntity.addComponent(new TransformComponent());
			Texture tex = app.getAssetManager().getTexture("res/textures/testNoxus.png");
			testEntity.addComponent(new SpriteComponent(new Vec4(1.0f, 1.0f, 1.0f, 1.0f), tex, 10.0f, 10.0f));
			TransformComponent transform = testEntity.getTransform();
			transform.setPosition(new Vec3(i % 10 * 10.0f, i / 10 * 10.0f, 0.0f));

			scene.addEntity(testEntity);
		}

		player = new Entity(0, "Player");
		player.addComponent(new TransformComponent());
		player.addComponent(new SpriteComponent(new Vec4(1.0f, 1.0f, 0.0f, 1.0f), 7.0f, 7.0f));

		scene.addEntity(player);

		float aspectRatio = 4.0f / 3.0f;
		scene.getCamera().setProjectionMatrix(
				MathUtil.orthoProjMat(-10.0f, 10.0f, -10.0f * aspectRatio, 10.0f * aspectRatio, 0.1f, 100.0f),
				new Vec2(10.0f, 10.0f * aspectRatio));
		scene.getCamera().setPosition(new Vec3(0.0f, 0.0f, -45.0f));
	}

	@Override
	public void render() {
		scene.render();
		manager.render();
	}

	@Override
	public void update() {
		if (GLFW.glfwGetTime() - second >= 1.0f) {
			second += 1.0f;
			System.out.println("FPS: " + frame);
			frame = 0;
		}

		System.out.println(
				"MouseX: " + app.getInputManager().getMouseX() + "  MouseY: " + app.getInputManager().getMouseY());

		frame++;

		scene.update();
		manager.getStatuses();
		playerController.update();

		if (app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_A)) {
			angle += 0.1f;
		}

		if (app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_D)) {
			angle -= 0.1f;
		}

		if (app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_W)) {
			scene.getCamera().move(new Vec3(0.0f, 1.0f, 0.0f));
		}

		if (app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_S)) {
			scene.getCamera().move(new Vec3(0.0f, -1.0f, 0.0f));
		}

		// scene.getCamera().setDirection(new Vec3((float) Math.cos(angle),
		// 0.0f, (float) Math.sin(angle)));

		if (app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_LEFT)) {
			player.getTransform().move(new Vec3(1.0f, 0.0f, 0.0f));
		}

		if (app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_RIGHT)) {
			player.getTransform().move(new Vec3(-1.0f, 0.0f, 0.0f));
		}

		if (app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_UP)) {
			player.getTransform().move(new Vec3(0.0f, 1.0f, 0.0f));
		}

		if (app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
			player.getTransform().move(new Vec3(0.0f, -1.0f, 0.0f));
		}

		// System.out.println("Cam Pos: " +
		// scene.getCamera().getPosition().getX() + " " +
		// scene.getCamera().getPosition().getY() + " " +
		// scene.getCamera().getPosition().getZ());
	}

	@Override
	public void deactivate() {

	}
	// TODO: Fill in state methods to make them functional
}
