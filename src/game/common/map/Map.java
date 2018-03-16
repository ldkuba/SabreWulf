package game.common.map;

import engine.AI.Navmesh;
import engine.assets.AssetManager;
import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.scene.Scene;

public class Map {

	private Entity[] background;
	private final float TILE_WIDTH = 12.0f;
	private final float TILE_HEIGHT = 12.0f;
	private final int MAP_SIZE = 16;
	private Scene scene;
	private Navmesh navmesh;
	
	private String basePath;
	
	public Map(Scene scene, String basePath) {
		this.scene = scene;
		background = new Entity[MAP_SIZE * MAP_SIZE];
		this.basePath = basePath;
		navmesh = new Navmesh(basePath + "/navmesh.txt");
	}

	//only run on the client
	public void init(AssetManager assetManager) {
		// initialise entities
		Vec4 white = new Vec4(1.0f, 1.0f, 1.0f, 1.0f);
		
		// bottom right
		for (int i = 0; i < background.length; i++) {
			Entity newEntity = new Entity("mapBackground" + i, true);
			SpriteComponent comp1 = new SpriteComponent(white,
					assetManager.getTexture(basePath + "/map (" + (i + 1) + ").png"), TILE_WIDTH, TILE_HEIGHT);
			newEntity.addComponent(comp1);
			newEntity.addComponent(new TransformComponent());
			newEntity.getTransform()
					.setPosition(new Vec3(0 + TILE_WIDTH * (i % MAP_SIZE), 0 - TILE_HEIGHT * (i / MAP_SIZE), 1.0f));
			background[i] = newEntity;
		}

		for (int i = 0; i < background.length; i++) {
			scene.addEntity(background[i]);
		}
	}
	
	public Navmesh getNavmesh(){
		return navmesh;
	}
}