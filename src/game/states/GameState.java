package game.states;

import org.lwjgl.glfw.GLFW;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.gui.components.Button;
import engine.maths.MathUtil;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.scene.Scene;
import engine.state.AbstractState;
import game.Main;
import game.player.PlayerController;
import game.player.PlayerManager;

public class GameState extends AbstractState
{
	private Main app;
	private Scene scene;
	
	private PlayerManager manager;
	private PlayerController playerController;

	private int frame = 0;
	private float second = 0;

	private Button button;


	public GameState(Main app)
	{
		this.app = app;
		scene = new Scene(0);
		manager = new PlayerManager(scene);
		playerController = new PlayerController(app, this);
	}

	@Override
	public void keyAction(int key, int action)
	{
		playerController.onKeyPress(key, action);
	}

	@Override
	public void mouseAction(int button, int action)
	{
		playerController.onMousePress(button, action);
	}

	@Override
	public void init()
	{
		scene.init();
		app.getGui().init(scene);
		
		button = new Button(20.0f, 20.0f, 10.0f, 10.0f, app.getAssetManager().getTexture("res/textures/testNoxus.png"), app.getAssetManager().getTexture("res/textures/background.png"))
		{
			@Override
			public void onClick()
			{
				//app.getStateManager().setCurrentState(Main.menuState);
			}
		};
		app.getGui().add(button);

		float aspectRatio = Application.s_WindowSize.getX()/Application.s_WindowSize.getY();
		scene.getCamera().setProjectionMatrix(MathUtil.orthoProjMat(-10.0f, 10.0f, -10.0f * aspectRatio, 10.0f * aspectRatio, 0.1f, 100.0f));
		scene.getCamera().setPosition(new Vec3(0.0f, 0.0f, -5.0f));
	}

	@Override
	public void render()
	{
		scene.render();
		manager.render();
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
		manager.getStatuses();
		playerController.update();

		
	}

	@Override
	public void deactivate()
	{

	}
}
