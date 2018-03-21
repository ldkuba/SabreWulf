package game.client.states;

import game.common.actors.Actor;
import game.common.actors.NPC;
import game.common.classes.classes.CaravanClass;
import org.lwjgl.glfw.GLFW;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.ColliderComponent;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.gui.components.Button;
import engine.gui.components.ProgressBar;
import engine.gui.components.Sprite;
import engine.maths.MathUtil;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.state.AbstractState;
import game.client.Main;
import game.client.player.PlayerController;
import game.common.actors.Player;
import game.common.classes.classes.GhostClass;
import game.common.classes.classes.LinkClass;
import game.common.classes.classes.WolfClass;
import game.common.map.Map;
import game.common.player.ActorManager;
public class GameState extends AbstractState {

	private final int SCROLL_MARGIN = 10;
	private final float SCROLL_SPEED = 0.00f;
	
	private Main app;
	private PlayerController playerController;

	private ActorManager actorManager;
	private ProgressBar healthBar;
	private ProgressBar energyBar;

	private Map map;
	private Sprite spellBar;

	private float dirX = 0.0f;
	private float dirY = 0.0f;

	private float zoom = 10.0f;
	private float aspectRatio = Application.s_WindowSize.getX() / Application.s_WindowSize.getY();

	private String attackAudio;
	
	public GameState(Main app) {
		super(app);

		this.app = app;

		actorManager = new ActorManager(scene);
		actorManager.setMap(map);
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

		if (key == GLFW.GLFW_KEY_D && action == GLFW.GLFW_PRESS) {
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
			
			Player player = null;
			
			if(app.getNetworkManager().getNetPlayers().get(i).getName().equals(Main.clientName))
			{
				player = new Player(i, app.getNetworkManager().getNetPlayers().get(i).getName(), app, true);
			}else
			{
				player = new Player(i, app.getNetworkManager().getNetPlayers().get(i).getName(), app, false);
			}
			// here we would set up more stuff related to the player like class,
			// items, starting position, etc.

			int characterSelection = app.getNetworkManager().getNetPlayers().get(i).getChar();
			System.out.println("Character Selected: " + characterSelection);
			switch (characterSelection) {
				case 0:
					player.setRole(new LinkClass());
					attackAudio = "attack/a2";
					break;
				case 1:
					player.setRole(new WolfClass());
					attackAudio = "attack/scratch";
					break;
				case 2:
					player.setRole(new GhostClass());
					attackAudio = "attack/m2";
					break;
			}

			if (i >= 0 && i < 3) {
				player.setTeam(1);
			} else {
				player.setTeam(2);
			}

			actorManager.addActor(player);

		}

		//Add Caravan
		NPC caravan = new NPC("Caravan", actorManager.getNextID(), app);
		caravan.setRole(new CaravanClass());
		caravan.setTeam(3);
		caravan.getEntity().getNetTransform().setPosition(new Vec3(30.0f,-30.0f,0.0f));
		caravan.getEntity().removeTag("Targetable");
		caravan.getEntity().addTag("Untargetable");

		actorManager.addActor(caravan);

		map.init(app.getAssetManager());

		// set up background sound
		if (!app.getSoundManager().getIsMuted()) {
			app.getSoundManager().invokeSound("background/game", true, true);
		}

		// ProgressBar healthBar = new ProgressBar(20.0f,20.0f, 20.0f, 20.0f,
		// app.getAssetManager().getTexture("res/textures/bars/potion.png"),
		// app.getAssetManager().getTexture("res/textures/bars/progress.png"),
		// app.getAssetManager().getFont("fontSprite.png"), app.getGui());
		// healthBar.setMaxProgress(240.0f);
		// healthBar.setBar(120.0f);

		spellBar = new Sprite(25.0f, 85.0f, 50.0f, 15.0f,
				app.getAssetManager().getTexture("res/textures/gui/placeholders/spellbar.png"));
		app.getGui().add(spellBar);

		healthBar = new ProgressBar(43.6f, 93.4f, 12.25f, 2.5f,
				app.getAssetManager().getTexture("res/textures/gui/bars/health_bar_background.png"),
				app.getAssetManager().getTexture("res/textures/gui/bars/health_bar.png"),
				app.getAssetManager().getFont("fontSprite.png"), app.getGui());

		energyBar = new ProgressBar(43.6f, 96.4f, 12.25f, 2.5f,
				app.getAssetManager().getTexture("res/textures/gui/bars/energy_bar_background.png"),
				app.getAssetManager().getTexture("res/textures/gui/bars/energy_bar.png"),
				app.getAssetManager().getFont("fontSprite.png"), app.getGui());

		// Entity entity3D = new Entity("3D test");
		// entity3D.addComponent(new TransformComponent());
		// entity3D.addComponent(new
		// MeshComponent(app.getAssetManager().getModel("res/models/testModel.obj",
		// "res/shaders/simpleshader3D.txt", null, false)));
		// scene.addEntity(entity3D);

		Entity entityCollider = new Entity("ColliderTest");
		entityCollider.addComponent(new TransformComponent());
		entityCollider.addComponent(new SpriteComponent(new Vec4(1.0f, 1.0f, 1.0f, 1.0f),
				app.getAssetManager().getTexture("res/textures/characters/placeholder.png"), 3.0f, 3.0f));
		entityCollider.addComponent(new ColliderComponent(1.6f, false));
		entityCollider.addTag("Targetable");
		scene.addEntity(entityCollider);

		scene.getCamera().setProjectionMatrix(
				MathUtil.orthoProjMat(-zoom, zoom, zoom * aspectRatio, -zoom * aspectRatio, 1.0f, 100.0f));
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
		
		//Mouse Scroll
		if(app.getInputManager().getMouseX() < SCROLL_MARGIN)
		{
			scene.getCamera().move(new Vec3(-SCROLL_SPEED * Application.s_Viewport.getX(), 0.0f, 0.0f));
		}
		
		if(app.getInputManager().getMouseX() > Application.s_WindowSize.getX() - SCROLL_MARGIN)
		{
			scene.getCamera().move(new Vec3(SCROLL_SPEED * Application.s_Viewport.getX(), 0.0f, 0.0f));
		}
		
		if(app.getInputManager().getMouseY() < SCROLL_MARGIN)
		{
			scene.getCamera().move(new Vec3(0.0f, SCROLL_SPEED * Application.s_Viewport.getY(), 0.0f));
		}
		
		if(app.getInputManager().getMouseY() > Application.s_WindowSize.getY() - SCROLL_MARGIN)
		{
			scene.getCamera().move(new Vec3(0.0f, -SCROLL_SPEED * Application.s_Viewport.getX(), 0.0f));
		}
		
		//STATS UPDATE:
		healthBar.setBar(actorManager.getLocalPlayer().getHealth());
		healthBar.setMaxProgress(actorManager.getLocalPlayer().getHealthLimit());
		energyBar.setBar(actorManager.getLocalPlayer().getEnergy());
		energyBar.setMaxProgress(actorManager.getLocalPlayer().getEnergyLimit());
		
		actorManager.update();
		playerController.update();
	}

	public ActorManager getActorManager() {
		return this.actorManager;
	}

	public Map getMap() {
		return this.map;
	}
	
	public String getAttackSoundPath(){
		return this.attackAudio;
	}
	
	@Override
	public void deactivate() {

	}
}

