package game.server.states;

import engine.entity.component.NetTransformComponent;
import engine.maths.Vec3;
import engine.state.AbstractState;
import game.common.actors.Player;
import game.common.classes.classes.GhostClass;
import game.common.classes.classes.LinkClass;
import game.common.classes.classes.WolfClass;
import game.common.map.Map;
import game.common.player.ActorManager;
import game.server.ingame.ServerMain;


public class ServerGameState extends AbstractState
{

	private boolean dummy = false;

	private ServerMain app;
	
	private Map map;
	
	private ActorManager actorManager;
	
	private int frame = 0;
	private float second = System.currentTimeMillis();
	
	public ServerGameState(ServerMain app)
	{
		super(app);
		this.app = app;
		map = new Map(scene, "res/textures/map");
		actorManager = new ActorManager(scene);
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


			int characterSelection = app.getNetworkManager().getNetPlayers().get(i).getChar();
			//characterSelection = 1;
			System.out.println("Character Selected: " +characterSelection);
			switch (characterSelection) {
				case 0:
					player.setRole(new LinkClass());
					System.out.println("WIZARD");
					player.setStatistics();
					break;
				case 1:
					player.setRole(new GhostClass());
					System.out.println("KNIGHT");
					break;
				case 2:
					player.setRole(new WolfClass());
					System.out.println("ELF");
					break;
			}

			if (i >= 0 && i < 3) {
				player.setTeam(1);

			}

			else {
				player.setTeam(2);
			}

			actorManager.addActor(player);
		}

	}

	@Override
	public void update()
	{
		super.update();
		
		System.out.println("ALIVE");
		
		actorManager.update();
	}
	
	public ActorManager getActorManager()
	{
		return this.actorManager;
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
