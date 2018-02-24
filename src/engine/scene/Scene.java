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

	public Scene(int id, Application app)
	{
		this.app = app;
		m_ID = id;
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
		int count = 0; //for checking isInView method
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
				if(isInView(e)){
					sprite.submit(m_Renderer2D, transformation);
					count++; //for checking isInView method
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
				//if(isInView(e)){
					text.submit(m_Renderer2D, transform);
				//}
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
				//if (isInView(e)){
					animation.submit(m_Renderer2D, transformation);
				//}
			}
		}
		System.out.println("Displayed tiles " + Integer.toString(count));
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
		 *       _p2_ 
		 *      |    |
		 *      |____| 
		 *    p1      p3     
		 */
		Vec3 cam = m_Camera.getPosition();
		SpriteComponent sprite = entity.getSprite();
		TransformComponent transform = entity.getTransform();
		if(sprite != null && transform != null) {
			float entWidth = sprite.getWidth();
			float entHeight = sprite.getHeight();
			Vec3 p1 = transform.getPosition(); 
			Vec2 p2 = new Vec2(p1.getX()+(entWidth/2), p1.getY()+(entHeight/2));
			Vec2 p3 = new Vec2(p1.getX()+entWidth, p1.getY());
			Vec2 u = new Vec2(p2.getX()-p1.getX(), p2.getY()-p1.getY());
			Vec2 v = new Vec2(p3.getX()-p1.getX(), p3.getY()-p1.getY());
			float nx = u.getX() - v.getX();
			float ny = u.getY() - v.getY();
			float dot = (nx * (cam.getX() - p1.getX())) + (ny * (cam.getY() - p1.getY()));
			/*System.out.println("**********************");
			System.out.println(dot);
			System.out.println(entity.getId());*/
			if(dot < 0) {
				return true;
			}
		}
		return false;
	}
}
