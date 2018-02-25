package game.server.states;

import engine.entity.Entity;
import engine.entity.component.NetIdentityComponent;
import engine.entity.component.NetTransformComponent;
import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.scene.Scene;
import engine.state.AbstractState;


public class ServerGameState extends AbstractState
{
	private ServerMain app;
	private Scene scene;
	
	private Entity testNetEntity;
	private static Vec2 currentTargetPos;
	private float speed;
	
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
		
		currentTargetPos = new Vec2(0, 0);
		
		speed = 0.05f;
		
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

		Vec2 currentPos = new Vec2(transform.getPosition().getX(), transform.getPosition().getY());
		
		currentPos.scale(-1.0f);
		
		Vec2 moveDir = Vec2.add(currentTargetPos, currentPos);
		moveDir = Vec2.normalize(moveDir);
		moveDir.scale(speed);
		
		transform.move(new Vec3(moveDir.getX(), moveDir.getY(), 0));
		
		scene.update();
	}
	
	public static void setBallTarget(Vec2 newPos)
	{
		currentTargetPos = newPos;
	}

	@Override
	public void deactivate()
	{
		
	}
}
