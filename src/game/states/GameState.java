package game.states;

import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.scene.Scene;
import engine.state.AbstractState;
import game.Main;
import game.player.PlayerManager;

public class GameState extends AbstractState
{
	private Main app;
	private Scene scene;
	private PlayerManager manager;
		
	public GameState(Main app)
	{
		this.app = app;
		scene = new Scene(0);
		manager = new PlayerManager();
	}
	
	@Override
	public void keyAction(int key, int action) {
		
	}

	@Override
	public void mouseAction(int button, int action) {
		
	}

	@Override
	public void init() {
		
		scene.init();
		
		Entity testEntity = new Entity(0, "Test Entity");
		testEntity.addComponent(new TransformComponent());
		testEntity.addComponent(new SpriteComponent(new Vec4(1.0f, 0.0f, 0.0f, 1.0f), 2, 2));
		TransformComponent transform = (TransformComponent) testEntity.getComponent(TransformComponent.class);
		
		transform.setPosition(new Vec3(0.0f, 0.0f, 0.0f));
		
		//scene.getCamera().setPosition(new Vec3(5.0f, 0.0f, 0.0f));
		
		scene.addEntity(testEntity);
	}

	@Override
	public void render() {
		scene.render();
	}

	@Override
	public void update() {
		scene.update();
		manager.getStatuses();
	}

	@Override
	public void deactivate() {
		
	}
	//TODO: Fill in state methods to make them functional
}
