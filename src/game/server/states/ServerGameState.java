package game.server.states;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import engine.entity.component.NetTransformComponent;
import engine.maths.Vec3;
import engine.state.AbstractState;
import game.common.actors.NPC;
import game.common.actors.Player;
import game.common.classes.classes.CaravanClass;
import game.common.classes.classes.GhostClass;
import game.common.classes.classes.GoblinClass;
import game.common.classes.classes.LinkClass;
import game.common.classes.classes.SlimeClass;
import game.common.classes.classes.WolfClass;
import game.common.map.Map;
import game.common.objects.Tower;
import game.common.player.ActorManager;
import game.server.ingame.ServerMain;

/**
 * Game state class for the server
 * 
 * @author User
 *
 */
public class ServerGameState extends AbstractState {

	private boolean dummy = false;
	private ServerMain app;
	private Map map;
	private ActorManager actorManager;
	private int frame = 0;
	private float second = System.currentTimeMillis();
	private ArrayList<String> computerPlayers = new ArrayList<String>();

	public ServerGameState(ServerMain app) {
		super(app);
		this.app = app;
		map = new Map(scene, "res/textures/map");
		actorManager = new ActorManager(scene);
		actorManager.setMap(map);
	}

	@Override
	public void keyAction(int key, int action) {

	}

	@Override
	public void mouseAction(int button, int action) {

	}

	/**
	 * Initialise the game state on the server side - pretty much mirrors the
	 * client just without GUI setup
	 */
	@Override
	public void init() {
		super.init();
		/* Add players */
		for (int i = 0; i < app.getNetworkManager().getNetPlayers().size(); i++) {
			Player player = new Player(app.getNetworkManager().getNetPlayers().get(i).getPayload().getNetPlayerId(),
					app.getNetworkManager().getNetPlayers().get(i).getName(), app, false);
			// here we would set up more stuff related to the player like class,
			// items, starting position, etc.
			NetTransformComponent netTransform = (NetTransformComponent) player.getEntity()
					.getComponent(NetTransformComponent.class);
			netTransform.setPosition(new Vec3(15.0f, -10.0f, 0.0f));

			int characterSelection = app.getNetworkManager().getNetPlayers().get(i).getChar();
			// characterSelection = 1;
			System.out.println("Character Selected: " + characterSelection);
			switch (characterSelection) {
			case 0:
				player.setRole(new LinkClass());
				System.out.println("LINK");
				break;
			case 1:
				player.setRole(new WolfClass());
				System.out.println("GHOST");
				break;
			case 2:
				player.setRole(new GhostClass());
				System.out.println("WOLF");
				break;
			}

			player.setTeam((i % 2) + 1);

			actorManager.addActor(player);
		}
		/* Add Caravan */
		NPC caravan = new NPC("Caravan", actorManager.getNextID(), app);
		caravan.setRole(new CaravanClass());
		caravan.setTeam(3);
		caravan.getEntity().getNetTransform().setPosition(new Vec3(10.0f, -7.0f, 0.0f));
		caravan.getEntity().removeTag("Targetable");
		caravan.getEntity().addTag("Untargetable");

		// Destinations
		caravan.addDesiredLocation(new Vec3(15.0f, -16.0f, 0.0f));
		caravan.addDesiredLocation(new Vec3(21.5f, -26.5f, 0.0f));
		caravan.addDesiredLocation(new Vec3(39.0f, -44.5f, 0.0f));
		// caravan.addDesiredLocation(new Vec3(50.0f,-62.0f,0.0f));
		caravan.addDesiredLocation(new Vec3(34.9f, -45.8f, 0.0f));
		caravan.addDesiredLocation(new Vec3(100.0f, -71.0f, 0.0f));
		caravan.addDesiredLocation(new Vec3(113.0f, -64.0f, 0.0f));
		caravan.addDesiredLocation(new Vec3(97.0f, -109.0f, 0.0f));
		caravan.addDesiredLocation(new Vec3(96.0f, -125.5f, 0.0f));
		caravan.addDesiredLocation(new Vec3(164.0f, -176.0f, 0.0f));

		actorManager.addActor(caravan);

		/* Add towers */
		Tower tower = new Tower(actorManager, new Vec3(30.0f, -20.0f, 0.0f), app.getNetworkManager().getFreeId(),
				app.getNetworkManager(), 20.0f, 40.0f);
		scene.addEntity(tower.getEntity());
		tower = new Tower(actorManager, new Vec3(164.0f, -159.0f, 0.0f), app.getNetworkManager().getFreeId(),
				app.getNetworkManager(), 20.0f, 40.0f);
		scene.addEntity(tower.getEntity());
		tower = new Tower(actorManager, new Vec3(98.0f, -122.5f, 0.0f), app.getNetworkManager().getFreeId(),
				app.getNetworkManager(), 20.0f, 40.0f);
		scene.addEntity(tower.getEntity());
		tower = new Tower(actorManager, new Vec3(97.0f, -51.5f, 0.0f), app.getNetworkManager().getFreeId(),
				app.getNetworkManager(), 20.0f, 40.0f);
		scene.addEntity(tower.getEntity());

		/* NPCs - Sadly the AI is not working */
		/* for (int i = 0; i < 6; i++) {
			NPC slime = null;
			String name = "Slime" + Integer.toString(i);
			slime = new NPC(name, actorManager.getNextID(), app);
			slime.setRole(new SlimeClass());
			slime.setTeam(3);
			slime.getEntity().getNetTransform().setPosition(randomStart());
			slime.addDesiredLocation(randomDest(slime.getEntity().getNetTransform().getPosition()));
			slime.addDesiredLocation(randomDest(slime.getEntity().getNetTransform().getPosition()));
			actorManager.addActor(slime);
			NPC goblin = null;
			name = "Goblin" + Integer.toString(i);
			goblin = new NPC(name, actorManager.getNextID(), app);
			goblin.setRole(new GoblinClass());
			goblin.setTeam(3);
			goblin.getEntity().getNetTransform().setPosition(randomStart());
			goblin.addDesiredLocation(randomDest(goblin.getEntity().getNetTransform().getPosition()));
			goblin.addDesiredLocation(randomDest(goblin.getEntity().getNetTransform().getPosition()));
			goblin.addDesiredLocation(randomDest(goblin.getEntity().getNetTransform().getPosition()));
			actorManager.addActor(goblin);
		} */
	}

	/**
	 * Update the server game state
	 */
	@Override
	public void update() {
		super.update();
		actorManager.update();
	}

	/**
	 * Get the actor manager
	 * 
	 * @return
	 */
	public ActorManager getActorManager() {
		return this.actorManager;
	}

	/**
	 * Get the map
	 * 
	 * @return
	 */
	public Map getMap() {
		return map;
	}

	@Override
	public void deactivate() {

	}

	/**
	 * Get a random coordinate to initialise the NPCs
	 * 
	 * @return
	 */
	private Vec3 randomStart() {
		ArrayList<Vec3> points = new ArrayList<Vec3>(Arrays.asList(new Vec3(15.0f, -10.0f, 0.0f),
				new Vec3(15.0f, -20.0f, 0.0f), new Vec3(15.0f, -30.0f, 0.0f), new Vec3(15.0f, -40.0f, 0.0f),
				new Vec3(15.0f, -50.0f, 0.0f), new Vec3(35.0f, -50.0f, 0.0f), new Vec3(20.0f, -16.0f, 0.0f),
				new Vec3(39.0f, -44.5f, 0.0f), new Vec3(113.0f, -64.0f, 0.0f), new Vec3(164.0f, -176.0f, 0.0f),
				new Vec3(160.0f, -180.0f, 0.0f), new Vec3(160.0f, -150.0f, 0.0f), new Vec3(160.0f, -140.0f, 0.0f),
				new Vec3(160.0f, -130.0f, 0.0f), new Vec3(150.0f, -60.0f, 0.0f), new Vec3(120.0f, -60.0f, 0.0f),
				new Vec3(120.0f, -45.0f, 0.0f), new Vec3(50.0f, -105.0f, 0.0f), new Vec3(45.0f, -135.0f, 0.0f)));
		Random random = new Random();
		int choice = random.nextInt(points.size());
		Vec3 newPos = points.get(choice);
		return newPos;
	}

	/**
	 * Get a random coordinate to set as the NPCs destination
	 * 
	 * @return
	 */
	private Vec3 randomDest(Vec3 current) {
		ArrayList<Vec3> points = new ArrayList<Vec3>(Arrays.asList(new Vec3(15.0f, -10.0f, 0.0f),
				new Vec3(15.0f, -20.0f, 0.0f), new Vec3(15.0f, -30.0f, 0.0f), new Vec3(15.0f, -40.0f, 0.0f),
				new Vec3(15.0f, -50.0f, 0.0f), new Vec3(35.0f, -50.0f, 0.0f), new Vec3(20.0f, -16.0f, 0.0f),
				new Vec3(39.0f, -44.5f, 0.0f), new Vec3(113.0f, -64.0f, 0.0f), new Vec3(164.0f, -176.0f, 0.0f),
				new Vec3(160.0f, -180.0f, 0.0f), new Vec3(160.0f, -150.0f, 0.0f), new Vec3(160.0f, -140.0f, 0.0f),
				new Vec3(160.0f, -130.0f, 0.0f), new Vec3(150.0f, -60.0f, 0.0f), new Vec3(120.0f, -60.0f, 0.0f),
				new Vec3(120.0f, -45.0f, 0.0f), new Vec3(50.0f, -105.0f, 0.0f), new Vec3(45.0f, -135.0f, 0.0f)));
		points.remove(current);
		Random random = new Random();
		int choice = random.nextInt(points.size());
		Vec3 newPos = points.get(choice);
		points.add(current);
		return newPos;
	}
}
