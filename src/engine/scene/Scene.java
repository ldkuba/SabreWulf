package engine.scene;

import java.util.ArrayList;

import engine.application.Application;
import engine.entity.Entity;
//import engine.entity.component.NetIdentityComponent;
import engine.entity.component.SpriteAnimationComponent;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TextComponent;
import engine.entity.component.TransformComponent;
import engine.graphics.camera.Camera;
import engine.graphics.renderer.Renderer2D;
import engine.graphics.renderer.Renderer3D;
import engine.maths.Mat4;
import engine.maths.MathUtil;
import engine.maths.Vec2;
import engine.maths.Vec3;
import game.common.map.Map;

public class Scene {
	private int m_ID;
	private ArrayList<Entity> m_Entities;

	private Renderer2D m_Renderer2D;
	private Renderer3D m_Renderer3D; // Currently not used
	private Camera m_Camera;

	private Application app;
	
	public Scene(int id, Application app) {
		this.app = app;
		m_ID = id;
	}

	public void init() {

		m_Entities = new ArrayList<Entity>();

		float aspectRatio = 4.0f / 3.0f;

		m_Camera = new Camera();
		m_Camera.setPosition(new Vec3(0.0f, 0.0f, -10.0f));
		m_Camera.setProjectionMatrix(
				MathUtil.orthoProjMat(-10.0f, 10.0f, 10.0f * aspectRatio, -10.0f * aspectRatio, 0.01f, 100.0f));

		m_Renderer2D = new Renderer2D();
		m_Renderer3D = new Renderer3D();
	}

	public void addEntity(Entity e) {
		if (!m_Entities.contains(e)) {
			m_Entities.add(e);
			
			int id = 0;
			while(!isIdFree(id)) id++;
			
			e.setId(id);
		}
	}
	
	private boolean isIdFree(int id)
	{
		for(Entity e : m_Entities)
		{
			if(e.getId() == id)
			{
				return false;
			}
		}
		
		return true;
	}

	public void removeEntity(Entity e) {
		m_Entities.remove(e);
	}

	public void removeAllEnties() {
		m_Entities.clear();
	}

	public void update() {
		for (Entity e : m_Entities) {

			if (e.hasComponent(TransformComponent.class)) {
				TransformComponent transform = (TransformComponent) e.getComponent(TransformComponent.class);
				// transform.update(); just an example, the transform component
				// doesnt need automatic updating
			}
			
			if(e.hasComponent(SpriteAnimationComponent.class))
			{
				SpriteAnimationComponent animation = (SpriteAnimationComponent) e.getComponent(SpriteAnimationComponent.class);
				
				animation.update();
			}
			
			if(e.hasComponent(NetIdentityComponent.class))
			{
				NetIdentityComponent netIdentity = (NetIdentityComponent) e.getComponent(NetIdentityComponent.class);
				app.getNetworkManager().updateEntityInNetworkManager(e, netIdentity.getNetworkId());
				
			}
		}
		
		app.getNetworkManager().synchronize(this);
	}

	public void render() {
		m_Renderer2D.init(m_Camera);

		for (Entity e : m_Entities) {
			if (e.hasComponent(SpriteComponent.class)) {
				SpriteComponent sprite = (SpriteComponent) e.getComponent(SpriteComponent.class);

				Mat4 transformation = Mat4.identity();

				if (e.hasComponent(TransformComponent.class)) {
					TransformComponent transform = (TransformComponent) e.getComponent(TransformComponent.class);
					transformation = transform.getTransformationMatrix();
				}

				//check if visible
				
				sprite.submit(m_Renderer2D, transformation);
			}
			
			if(e.hasComponent(TextComponent.class))
			{
				TextComponent text = (TextComponent) e.getComponent(TextComponent.class);
				
				TransformComponent transform = new TransformComponent();
				
				if (e.hasComponent(TransformComponent.class)) {
					transform = (TransformComponent) e.getComponent(TransformComponent.class);
				}
				
				//check is visible
				
				text.submit(m_Renderer2D, transform);
			}
			
			if(e.hasComponent(SpriteAnimationComponent.class))
			{
				SpriteAnimationComponent animation = (SpriteAnimationComponent) e.getComponent(SpriteAnimationComponent.class);
				
				Mat4 transformation = Mat4.identity();

				if (e.hasComponent(TransformComponent.class)) {
					TransformComponent transform = (TransformComponent) e.getComponent(TransformComponent.class);
					transformation = transform.getTransformationMatrix();
				}

				//check is visible
				
				animation.submit(m_Renderer2D, transformation);
			}
		}

		m_Renderer2D.drawAll();
	}

	public Camera getCamera() {
		return m_Camera;
	}

	public Renderer2D getRenderer2D() {
		return m_Renderer2D;
	}
	
	public ArrayList<Entity> getEntities()
	{
		return m_Entities;
	}

	public void isInView(Entity entity) {
		//incomplete
		Vec3 camPos = m_Camera.getPosition();
		SpriteComponent sprite = entity.getSprite();
		TransformComponent transform = entity.getTransform();
		Vec3 entPos = transform.getPosition();
		float entWidth = sprite.getWidth();
		float entHeight = sprite.getHeight();
		if (entPos.getX() + entWidth <= camPos.getX()) {
			
		}
		if (entPos.getY() + entHeight <= camPos.getY()) {

		}

	}
}
