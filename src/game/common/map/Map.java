package game.common.map;

import engine.AI.Navmesh;
import engine.assets.AssetManager;
import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.scene.Scene;

/**
 * Set up the game state map
 * 
 * @author bhavi
 *
 */
public class Map {

	private final float TILE_WIDTH = 12.0f;
	private final float TILE_HEIGHT = 12.0f;
	private final int MAP_SIZE = 16;

	private Scene scene;
	private Navmesh navmesh;

	private Entity[] background;
	private Entity[] terrain;
	private String basePath;

	/**
	 * Create a new map
	 * 
	 * @param scene
	 * @param basePath
	 */
	public Map(Scene scene, String basePath) {
		this.scene = scene;
		background = new Entity[MAP_SIZE * MAP_SIZE];
		terrain = new Entity[MAP_SIZE * MAP_SIZE];
		this.basePath = basePath;
		navmesh = new Navmesh(basePath + "/navmesh.txt");

	}

	/**
	 * Initialise the map entities - only run on the client
	 * 
	 * @param assetManager
	 */
	public void init(AssetManager assetManager) {
		Vec4 white = new Vec4(1.0f, 1.0f, 1.0f, 1.0f);
		// bottom right
		for (int i = 0; i < background.length; i++) {
			Entity newEntity = new Entity("mapBackground" + i, true);
			SpriteComponent comp1 = new SpriteComponent(white,
					assetManager.getTexture(basePath + "/terrain/terrain (" + (i + 1) + ").png"), TILE_WIDTH,
					TILE_HEIGHT);
			newEntity.addComponent(comp1);
			newEntity.addComponent(new TransformComponent());
			newEntity.getTransform()
					.setPosition(new Vec3(0 + TILE_WIDTH * (i % MAP_SIZE), 0 - TILE_HEIGHT * (i / MAP_SIZE), 1.0f));
			background[i] = newEntity;

			Entity newEntityTerrain = new Entity("mapTerrain" + i, true);
			SpriteComponent sprite = new SpriteComponent(white,
					assetManager.getTexture(basePath + "/entities/entities (" + (i + 1) + ").png"), TILE_WIDTH,
					TILE_HEIGHT);
			newEntityTerrain.addComponent(sprite);
			newEntityTerrain.addComponent(new TransformComponent());
			newEntityTerrain.getTransform()
					.setPosition(new Vec3(0 + TILE_WIDTH * (i % MAP_SIZE), 0 - TILE_HEIGHT * (i / MAP_SIZE), -0.3f));
			terrain[i] = newEntityTerrain;
		}

		for (int i = 0; i < background.length; i++) {
			scene.addEntity(background[i]);
			scene.addEntity(terrain[i]);
		}
	}

	/**
	 * Get the navmesh used for the map
	 * @return
	 */
	public Navmesh getNavmesh() {
		return navmesh;
	}
}