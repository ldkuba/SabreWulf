package game.server.states;

import engine.entity.Entity;
import engine.entity.component.NetIdentityComponent;
import engine.entity.component.NetTransformComponent;
import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.scene.Scene;
import engine.state.AbstractState;
import game.common.actors.Player;
import game.common.player.PlayerManager;


public class ServerGameState extends AbstractState
{
	private ServerMain app;
	private Scene scene;
	
	private PlayerManager playerManager;
	
	private Entity testNetEntity;
	private float speed;
	
	private int frame = 0;
	private float second = System.currentTimeMillis();
	
	public ServerGameState(ServerMain app)
	{
		this.app = app;
		scene = new Scene(0, app);
		
		playerManager = new PlayerManager(scene);
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

		//Add players
		for(int i = 0; i < app.getNetworkManager().getNetPlayers().size(); i++)
		{
			Player player = new Player(i, app.getNetworkManager().getNetPlayers().get(i).getName(), app);
			// here we would set up more stuff related to the player like class, items, starting position, etc.
			playerManager.addPlayer(player);
		}
		
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
		
		playerManager.update();
		scene.update();
	}
	
	public PlayerManager getPlayerManager()
	{
		return this.playerManager;
	}

	@Override
	public void deactivate()
	{
		
	}
}
