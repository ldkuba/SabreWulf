package game.server.states;

import org.lwjgl.glfw.GLFW;

import engine.entity.Entity;
import engine.entity.component.NetIdentityComponent;
import engine.entity.component.NetTransformComponent;
import engine.maths.Vec3;
import engine.scene.Scene;
import engine.state.AbstractState;


public class ServerGameState extends AbstractState
{
	private ServerMain app;
	private Scene scene;
	
	private Entity testNetEntity;
	
	private int frame = 0;
	private float second = System.currentTimeMillis();
	
	public ServerGameState(ServerMain app)
	{
		this.app = app;
		scene = new Scene(0, app);
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
		
		testNetEntity = new Entity("TestNetEntity");
		testNetEntity.addComponent(new NetIdentityComponent(0, app.getNetworkManager()));
		testNetEntity.addComponent(new NetTransformComponent());
		
		scene.addEntity(testNetEntity);
	}

	@Override
	public void render()
	{
		
	}

	@Override
	public void update()
	{
		if (System.currentTimeMillis() - second >= 1000.0f) {
			second += 1000.0f;
			System.out.println("FPS: " + frame);
			frame = 0;
		}
		frame++;
		
		NetTransformComponent transform = (NetTransformComponent) testNetEntity.getComponent(NetTransformComponent.class);
		
		transform.move(new Vec3(0.2f, 0, 0));
		
		
		
		scene.update();
	}

	@Override
	public void deactivate()
	{
		
	}
}
