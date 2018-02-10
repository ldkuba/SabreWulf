package game.states;

import org.lwjgl.glfw.GLFW;

import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.scene.Scene;
import engine.state.AbstractState;
import game.Main;
import game.player.PlayerManager;
import javafx.scene.transform.Transform;

public class GameState extends AbstractState
{
	private Main app;
	private Scene scene;
	private PlayerManager manager;

	private Entity testEntity;
	
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

		testEntity = new Entity(0, "Test Entity");
		testEntity.addComponent(new TransformComponent());
		testEntity.addComponent(
				new SpriteComponent(new Vec4(1.0f, 0.0f, 0.0f, 1.0f), "res/textures/testNoxus.png", 5.0f, 5.0f));
		TransformComponent transform = (TransformComponent) testEntity.getComponent(TransformComponent.class);

		transform.setPosition(new Vec3(5.0f, 0.0f, 0.0f));

		scene.getCamera().setPosition(new Vec3(5.0f, 0.0f, 0.0f));

		scene.addEntity(testEntity);
	}

	@Override
	public void render()
	{
		scene.render();
	}

	@Override
	public void update()
	{
		scene.update();
		manager.getStatuses();
		
		if(app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_UP))
		{
			scene.getCamera().move(new Vec3(0.0f, 0.08f, 0.0f));
		}
		
		if(app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_DOWN))
		{
			scene.getCamera().move(new Vec3(0.0f, -0.08f, 0.0f));
		}
		
		if(app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_A))
		{
			testEntity.getTransform().rotate(new Vec3(0.0f, 0.0f, 1.0f));
		}
		
		if(app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_D))
		{
			testEntity.getTransform().rotate(new Vec3(0.0f, 0.0f, -1.0f));
		}
		
		if(app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_W))
		{
			testEntity.getTransform().rotate(new Vec3(0.0f, 0.0f, 1.0f));
		}
		
		if(app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_S))
		{
			testEntity.getTransform().rotate(new Vec3(0.0f, 0.0f, -1.0f));
		}		
		
		System.out.println("Cam Pos: " + scene.getCamera().getPosition().getX() + "  " + scene.getCamera().getPosition().getY() + "  " + scene.getCamera().getPosition().getZ());
	}

	@Override
	public void deactivate()
	{

	}
	// TODO: Fill in state methods to make them functional
}
