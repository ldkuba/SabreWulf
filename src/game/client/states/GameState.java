package game.client.states;


import engine.gui.components.ProgressBar;
import org.lwjgl.glfw.GLFW;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.ColliderComponent;
import engine.entity.component.MeshComponent;
import engine.entity.component.NetSpriteAnimationComponent;
import engine.entity.component.SpriteAnimationComponent;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TextComponent;
import engine.entity.component.TransformComponent;
import engine.graphics.VertexArray;
import engine.graphics.VertexBuffer;
import engine.graphics.renderer.Renderable3D;
import engine.gui.components.Label;
import engine.gui.components.Sprite;
import engine.maths.MathUtil;
import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.net.common_net.networking_messages.DesiredLocationMessage;
import engine.scene.Scene;
import engine.state.AbstractState;
import game.client.Main;
import game.client.player.PlayerController;
import game.common.actors.Player;
import game.common.map.Map;
import game.common.player.PlayerManager;

public class GameState extends AbstractState {

	private Main app;
	private PlayerController playerController;

	private PlayerManager playerManager;
	private ProgressBar heathBar;
	private ProgressBar energyBar;

	private Map map;

	private Sprite spellBar;
	
	private float dirX = 0.0f;
	private float dirY = 0.0f;

	private float zoom = 10.0f;
	float aspectRatio = Application.s_WindowSize.getX() / Application.s_WindowSize.getY();

	public GameState(Main app) {
		super(app);
		
		this.app = app;
		
		playerManager = new PlayerManager(scene);
		playerController = new PlayerController(app, this, scene);
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
		super.init();

		// Add players
		for (int i = 0; i < app.getNetworkManager().getNetPlayers().size(); i++) {
			Player player = new Player(i, app.getNetworkManager().getNetPlayers().get(i).getName(), app);
			// here we would set up more stuff related to the player like class,
			// items, starting position, etc.

			playerManager.addPlayer(player);
		}
		
		map.init(app.getAssetManager());

		// set up background sound
		app.getSoundManager().invokeSound("background/game", true, true);

//		ProgressBar healthBar = new ProgressBar(20.0f,20.0f, 20.0f, 20.0f, app.getAssetManager().getTexture("res/textures/bars/potion.png"), app.getAssetManager().getTexture("res/textures/bars/progress.png"), app.getAssetManager().getFont("fontSprite.png"), app.getGui());
//		healthBar.setMaxProgress(240.0f);
//		healthBar.setBar(120.0f);

		spellBar = new Sprite(25.0f, 85.0f, 50.0f, 15.0f,
				app.getAssetManager().getTexture("res/textures/spellbar.png"));
		app.getGui().add(spellBar);

		heathBar = new ProgressBar(25.0f, 85.0f, 3.0f, 1.0f, app.getAssetManager().getTexture("res/textures/gui/bars/health_bar_background"),app.getAssetManager().getTexture("res/textures/gui/bars/health_bar"),app.getAssetManager().getFont("res/fonts/fontSprite.png"),app.getGui());

		energyBar = new ProgressBar(25.0f, 80.0f, 3.0f, 1.0f, app.getAssetManager().getTexture("res/textures/gui/bars/energy_bar_background"),app.getAssetManager().getTexture("res/textures/gui/bars/health_bar"),app.getAssetManager().getFont("res/fonts/fontSprite.png"),app.getGui());

//		Entity entity3D = new Entity("3D test");
//		entity3D.addComponent(new TransformComponent());
//		entity3D.addComponent(new MeshComponent(app.getAssetManager().getModel("res/models/testModel.obj", "res/shaders/simpleshader3D.txt", null, false)));
//		scene.addEntity(entity3D);
		
		Entity entityCollider = new Entity("ColliderTest");
		entityCollider.addComponent(new TransformComponent());
		entityCollider.addComponent(new SpriteComponent(new Vec4(1.0f, 1.0f, 1.0f, 1.0f), app.getAssetManager().getTexture("res/textures/characters/placeholder.png"), 3.0f, 3.0f));
		entityCollider.addComponent(new ColliderComponent(1.6f, false));
		entityCollider.addTag("Targetable");
		scene.addEntity(entityCollider);
		
		scene.getCamera().setProjectionMatrix(MathUtil.orthoProjMat(-zoom, zoom, zoom * aspectRatio, -zoom * aspectRatio, 1.0f, 100.0f));
		scene.getCamera().setPosition(new Vec3(0.0f, 0.0f, -5.0f));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void update() {
		super.update();

		float cameraSpeed = 0.3f;

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
		
		if (app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_A)) {
			dirX -= 0.04f;
		}
		
		if (app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_F)) {
			dirX += 0.04f;
		}
		
		if (app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_W)) {
			dirY -= 0.04f;
		}
		
		if (app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_S)) {
			dirY += 0.04f;
		}
		
		//scene.getCamera().setDirection(new Vec3(dirX, dirY, 5.0f));
		
		playerController.update();
	}

	public PlayerManager getPlayerManager() {
		return this.playerManager;
	}
	
	public Map getMap()
	{
		return this.map;
	}

	@Override
	public void deactivate() {

	}
}
