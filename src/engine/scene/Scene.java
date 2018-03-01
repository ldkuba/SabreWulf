package engine.scene;

import java.util.ArrayList;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.NetIdentityComponent;
import engine.entity.component.NetTransformComponent;
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

public class Scene
{
	private int m_ID;
	private ArrayList<Entity> m_Entities;
	
	private Renderer2D m_Renderer2D;
	private Renderer3D m_Renderer3D; // Currently not used
	private Camera m_Camera;

	private Application app;
	private boolean isGameState;

	public Scene(int id, Application app, boolean gameState)
	{
		this.app = app;
		m_ID = id;
		isGameState = gameState;
	}

	public void init()
	{
		m_Entities = new ArrayList<Entity>();

		if(!app.isHeadless())
		{
			float aspectRatio = 4.0f / 3.0f;

			m_Camera = new Camera();
			m_Camera.setPosition(new Vec3(0.0f, 0.0f, -10.0f));
			m_Camera.setProjectionMatrix(
					MathUtil.orthoProjMat(-10.0f, 10.0f, 10.0f * aspectRatio, -10.0f * aspectRatio, 0.01f, 100.0f));

			m_Renderer2D = new Renderer2D();
			m_Renderer3D = new Renderer3D();
		}
	}

	public void addEntity(Entity e)
	{
		if(!m_Entities.contains(e))
		{
			m_Entities.add(e);

			int id = 0;
			while (!isIdFree(id))
				id++;

			e.setId(id);
		}
		
		sortEntitiesZ();
	}

	private void sortEntitiesZ()
	{
		//Collections.so
	}
	
	private boolean isIdFree(int id)
	{
		for (Entity e : m_Entities)
		{
			if(e.getId() == id)
			{
				return false;
			}
		}

		return true;
	}

	public void removeEntity(Entity e)
	{
		m_Entities.remove(e);
	}

	public void removeAllEnties()
	{
		m_Entities.clear();
	}

	public void update()
	{
		for (Entity e : m_Entities)
		{
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

	public void render()
	{
		/*for some reason when checking is entity is visible for text/animation it doesnt work
		* i'm guessing it's something to do with where the respective are positioned
		*/
		m_Renderer2D.init(m_Camera);

		for (Entity e : m_Entities)
		{
			if(e.hasComponent(SpriteComponent.class))
			{
				SpriteComponent sprite = (SpriteComponent) e.getComponent(SpriteComponent.class);

				Mat4 transformation = Mat4.identity();

				if(e.hasComponent(TransformComponent.class))
				{
					TransformComponent transform = (TransformComponent) e.getComponent(TransformComponent.class);
					transformation = transform.getTransformationMatrix();
				}else if(e.hasComponent(NetTransformComponent.class))
				{
					NetTransformComponent transform = (NetTransformComponent) e
							.getComponent(NetTransformComponent.class);
					transformation = transform.getTransformationMatrix();
				}

				// check if visible
				if(!isGameState){
					sprite.submit(m_Renderer2D, transformation);
				} else {
					if(isInView(e)){
						sprite.submit(m_Renderer2D, transformation);
					}
				}
			}

			if(e.hasComponent(TextComponent.class))
			{
				TextComponent text = (TextComponent) e.getComponent(TextComponent.class);

				TransformComponent transform = new TransformComponent();

				if(e.hasComponent(TransformComponent.class))
				{
					transform = e.getTransform();
				}else if(e.hasComponent(NetTransformComponent.class))
				{
					NetTransformComponent netTransform = (NetTransformComponent) e.getComponent(NetTransformComponent.class);
					transform.setPosition(netTransform.getPosition());
					transform.setRotationAngles(netTransform.getRotationAngles());
					transform.setScale(netTransform.getScale());
				}

				// check if visible
				text.submit(m_Renderer2D, transform);
			}

			if(e.hasComponent(SpriteAnimationComponent.class))
			{
				SpriteAnimationComponent animation = (SpriteAnimationComponent) e.getComponent(SpriteAnimationComponent.class);

				Mat4 transformation = Mat4.identity();

				if(e.hasComponent(TransformComponent.class))
				{
					TransformComponent transform = (TransformComponent) e.getComponent(TransformComponent.class);
					transformation = transform.getTransformationMatrix();
				}else if(e.hasComponent(NetTransformComponent.class))
				{
					NetTransformComponent transform = (NetTransformComponent) e
							.getComponent(NetTransformComponent.class);
					transformation = transform.getTransformationMatrix();
				}
				// check is visible
				animation.submit(m_Renderer2D, transformation);
			}
		}
		m_Renderer2D.drawAll();
	}

	public Camera getCamera()
	{
		return m_Camera;
	}

	public Renderer2D getRenderer2D()
	{
		return m_Renderer2D;
	}

	public ArrayList<Entity> getEntities()
	{
		return m_Entities;
	}
		
	public boolean isInView(Entity entity){
		/*
		 *     p4____p2 
		 *      |    |
		 *      |____| 
		 *    p1      p3     
		 */
		Vec3 cam = m_Camera.getPosition();
		float view = Application.s_Viewport.getLength();
		SpriteComponent sprite = entity.getSprite();
		TransformComponent transform = entity.getTransform();
		if(sprite != null && transform != null) {
			float entWidth = sprite.getWidth();
			float entHeight = sprite.getHeight();
			Vec3 p1 = transform.getPosition(); 
			Vec2 p2 = new Vec2(p1.getX()+(entWidth), p1.getY()+(entHeight));
			Vec2 p3 = new Vec2(p1.getX()+entWidth, p1.getY());
			Vec2 p4 = new Vec2(p1.getX(), p1.getY()+entHeight);
			float xMinSpan = cam.getX() - (view/2);
			float xMaxSpan = cam.getX() + (view/2);
			float yMinSpan = cam.getY() - (view/3);
			float yMaxSpan = cam.getY() + (view/3);
			if (p4.getX() < xMaxSpan && p4.getX() > xMinSpan && p4.getY() < yMaxSpan && p4.getY() > yMinSpan){
				return true;
			} else if (p3.getX() < xMaxSpan && p3.getX() > xMinSpan && p3.getY() < yMaxSpan && p3.getY() > yMinSpan){
				return true;
			} else if (p2.getX() < xMaxSpan && p2.getX() > xMinSpan && p2.getY() < yMaxSpan && p2.getY() > yMinSpan){
				return true;
			} else if (p1.getX() < xMaxSpan && p1.getX() > xMinSpan && p1.getY() < yMaxSpan && p1.getY() > yMinSpan){
				return true;
			} 
		}
		return false;
	}
}
