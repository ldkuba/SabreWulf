package game.states;

import org.lwjgl.glfw.GLFW;

import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.graphics.texture.Texture;
import engine.maths.MathUtil;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.scene.Scene;
import engine.state.AbstractState;
import game.Main;
import game.player.PlayerManager;

public class GameState extends AbstractState
{
	private Main app;
	private Scene scene;
	private PlayerManager manager;
	
	private int frame = 0;
	
	private float second = 0;
	
	public GameState(Main app)
	{
		this.app = app;
		scene = new Scene(0);
		manager = new PlayerManager();
	}

	@Override
	public void keyAction(int key, int action)
	{
		
	}

	@Override
	public void mouseAction(int button, int action)
	{

	}

	
	@Override
	public void init()
	{
		scene.init();

		for(int i = 0; i < 10000; i++)
		{
			Entity testEntity = new Entity(0, "Test Entity");
			testEntity.addComponent(new TransformComponent());
			Texture tex = app.getAssetManager().getTexture("res/textures/testNoxus.png");
			testEntity.addComponent(new SpriteComponent(new Vec4(1.0f, 1.0f, 1.0f, 1.0f), tex, 10.0f, 10.0f));
			TransformComponent transform = testEntity.getTransform();
			transform.setPosition(new Vec3(i%100 * 10.0f, i/100 * 10.0f, 0.0f));
	
			scene.addEntity(testEntity);
		}
			
//		float aspectRatio = 4.0f/3.0f;
		scene.getCamera().setProjectionMatrix(MathUtil.orthoProjMat(-600.0f, 600.0f, -800.0f, 800.0f, 0.1f, 100.0f));
		scene.getCamera().setPosition(new Vec3(0.0f, 0.0f, -5.0f));
	}

	@Override
	public void render()
	{
		scene.render();
	}

	@Override
	public void update()
	{
		if(GLFW.glfwGetTime() - second >= 1.0f)
		{
			second += 1.0f;
			System.out.println("FPS: " + frame);
			frame = 0;
		}
		
		frame++;
		
		scene.update();
		manager.getStatuses();
		
		if(app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_A))
		{
			scene.getCamera().move(new Vec3(-1.0f, 0.0f, 0.0f));
		}
		
		if(app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_D))
		{
			scene.getCamera().move(new Vec3(1.0f, 0.0f, 0.0f));
		}
		
		if(app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_W))
		{
			scene.getCamera().move(new Vec3(0.0f, 1.0f, 0.0f));
		}
		
		if(app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_S))
		{
			scene.getCamera().move(new Vec3(0.0f, -1.0f, 0.0f));
		}		
		
		//System.out.println("Cam Pos: " + scene.getCamera().getPosition().getX() + "  " + scene.getCamera().getPosition().getY() + "  " + scene.getCamera().getPosition().getZ());
	}

	@Override
	public void deactivate()
	{

	}
	// TODO: Fill in state methods to make them functional
}
