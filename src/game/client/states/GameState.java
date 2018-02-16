package game.client.states;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.openal.AL11;

import engine.application.Application;
import engine.gui.components.ToggleButton;
import engine.maths.MathUtil;
import engine.maths.Vec3;
import engine.scene.Scene;
import engine.sound.Sound;
import engine.sound.SoundManager;
import engine.state.AbstractState;
import game.client.Main;
import game.client.player.PlayerController;
import game.common.map.Map;

public class GameState extends AbstractState
{
	private Main app;
	private Scene scene;
	
	//private PlayerManager manager;
	private PlayerController playerController;

	private Map map;

	private int frame = 0;
	private float second = 0;

	private ToggleButton button;

	public GameState(Main app)
	{
		this.app = app;
		scene = new Scene(0);
		//manager = new PlayerManager(scene);
		playerController = new PlayerController(app, this);
		map = new Map(scene);
	}

	@Override
	public void keyAction(int key, int action)
	{
		playerController.onKeyPress(key, action);
	}

	@Override
	public void mouseAction(int button, int action)
	{
	}

	@Override
	public void init()
	{		
		scene.initRenderer();
		app.getGui().init(scene);
		
		button = new ToggleButton(20.0f, 20.0f, 10.0f, 10.0f, app.getAssetManager().getTexture("res/textures/testNoxus.png"), app.getAssetManager().getTexture("res/textures/background.png"))
		{
			@Override
			public void onClick(boolean toggled)
			{
				//app.getStateManager().setCurrentState(Main.menuState);
			}
		};
		app.getGui().add(button);

		float aspectRatio = Application.s_WindowSize.getX()/Application.s_WindowSize.getY();
		scene.getCamera().setProjectionMatrix(MathUtil.orthoProjMat(-10.0f, 10.0f, 10.0f * aspectRatio, -10.0f * aspectRatio, 0.1f, 100.0f));
		scene.getCamera().setPosition(new Vec3(0.0f, 0.0f, -5.0f));
		
		// set up background sound
		app.getSoundManager().invokeSound("game");
	}

	@Override
	public void render()
	{
		scene.render();
		//manager.render(); not used anymore
	}

	@Override
	public void update()
	{
		//FPS Counter
		if(GLFW.glfwGetTime() - second >= 1.0f)
		{
			second += 1.0f;
			System.out.println("FPS: " + frame);
			frame = 0;
		}
		frame++;

		scene.update();
		//manager.getStatuses();
		playerController.update();
		map.update();
	}

	@Override
	public void deactivate()
	{

	}
}
