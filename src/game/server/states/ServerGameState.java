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
	private Scene scene;
	
	private Map map;
	
	private PlayerManager playerManager;
	
	private int frame = 0;
	private float second = System.currentTimeMillis();
	
	public ServerGameState(ServerMain app)
	{
		this.app = app;
		scene = new Scene(0, app);
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
		scene.init();

		//Add players
		for(int i = 0; i < app.getNetworkManager().getNetPlayers().size(); i++)
		{
			Player player = new Player(i, app.getNetworkManager().getNetPlayers().get(i).getName(), app);
			// here we would set up more stuff related to the player like class, items, starting position, etc.
			NetTransformComponent netTransform = (NetTransformComponent) player.getEntity().getComponent(NetTransformComponent.class);
			netTransform.setPosition(new Vec3(-10.0f, 0.0f, 0.0f));
			playerManager.addPlayer(player);
		}

		//Add Dummy Player
		Player dummyPlayer = new Player(1, "dummy",app);
		playerManager.addPlayer(dummyPlayer);

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
		
		System.out.println("ALIVE");

		/*
		Testing Zone
		 */

		NetTransformComponent playerTrans = (NetTransformComponent) playerManager.getPlayer(0).getEntity().getComponent(NetTransformComponent.class);
		NetTransformComponent dummyTrans = (NetTransformComponent) playerManager.getPlayer(1).getEntity().getComponent(NetTransformComponent.class);
		System.out.println(playerTrans.getPosition().getX());
		System.out.println(dummyTrans.getPosition().getX());

		playerManager.update();
		scene.update();
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
