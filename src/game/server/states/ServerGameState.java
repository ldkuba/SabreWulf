package game.server.states;

import engine.entity.component.NetTransformComponent;
import engine.maths.Vec3;
import engine.scene.Scene;
import engine.state.AbstractState;
import game.common.actors.Player;
import game.common.map.Map;
import game.common.player.PlayerManager;
import game.server.ingame.ServerMain;


public class ServerGameState extends AbstractState
{
	private ServerMain app;
	
	private Map map;
	
	private PlayerManager playerManager;
	
	private int frame = 0;
	private float second = System.currentTimeMillis();
	
	public ServerGameState(ServerMain app)
	{
		super(app);
		this.app = app;
		map = new Map(scene, "res/textures/map");
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
		super.init();

		//Add players
		for(int i = 0; i < app.getNetworkManager().getNetPlayers().size(); i++)
		{
			Player player = new Player(i, app.getNetworkManager().getNetPlayers().get(i).getName(), app);
			// here we would set up more stuff related to the player like class, items, starting position, etc.
			NetTransformComponent netTransform = (NetTransformComponent) player.getEntity().getComponent(NetTransformComponent.class);
			netTransform.setPosition(new Vec3(15.0f, -10.0f, 0.0f));
			playerManager.addPlayer(player);
		}
	}

	@Override
	public void update()
	{
		super.update();
		
		System.out.println("ALIVE");
		
		playerManager.update();
	}
	
	public PlayerManager getPlayerManager()
	{
		return this.playerManager;
	}
	
	public Map getMap()
	{
		return map;
	}

	@Override
	public void deactivate()
	{
		
	}
}
