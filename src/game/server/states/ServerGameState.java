package game.server.states;

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
		testNetEntity.addComponent(new NetIdentityComponent(0, app.getNetworkManager(), testNetEntity));
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
		NetTransformComponent transform = (NetTransformComponent) testNetEntity.getComponent(NetTransformComponent.class);
		
		transform.move(new Vec3(0.2f, 0, 0));
		
		scene.update();
		System.out.println("running game");
	}

	@Override
	public void deactivate()
	{
		
	}
}
