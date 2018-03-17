package engine.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.AbstractComponent;
import engine.entity.component.ColliderComponent;
import engine.entity.component.CustomComponent;
import engine.entity.component.MeshComponent;
import engine.entity.component.NetIdentityComponent;
import engine.entity.component.NetSpriteAnimationComponent;
import engine.entity.component.NetTransformComponent;
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
			m_Camera.setPosition(new Vec3(0.0f, 0.0f, -20.0f));
			m_Camera.setProjectionMatrix(MathUtil.perspProjMat(aspectRatio, 70.0f, 0.1f, 100.0f));

			m_Renderer2D = new Renderer2D();
			m_Renderer3D = new Renderer3D(m_Camera);
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
		class CompareZ implements Comparator<Entity>{

			@Override
			public int compare(Entity e1, Entity e2)
			{
				float z1 = 0.0f;
				float z2 = 0.0f;
				
				if(e1.hasComponent(TransformComponent.class))
				{
					z1 = e1.getTransform().getPosition().getZ();
				}else if(e1.hasComponent(NetTransformComponent.class))
				{
					z1 = ((NetTransformComponent) e1.getComponent(NetTransformComponent.class)).getPosition().getZ();
				}
				
				if(e2.hasComponent(TransformComponent.class))
				{
					z2 = e2.getTransform().getPosition().getZ();
				}else if(e2.hasComponent(NetTransformComponent.class))
				{
					z2 = ((NetTransformComponent) e2.getComponent(NetTransformComponent.class)).getPosition().getZ();
				}
				
				if(z1 > z2)
				{
					return -1;
				}else if(z1 == z2)
				{
					return 0;
				}else
				{
					return 1;
				}
			}
			
		}
		
		Collections.sort(m_Entities, new CompareZ());
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
			if(e.hasComponent(NetSpriteAnimationComponent.class))
			{
				NetSpriteAnimationComponent animation = (NetSpriteAnimationComponent) e.getComponent(NetSpriteAnimationComponent.class);
				
				if(e.hasComponent(SpriteAnimationComponent.class))
				{
					SpriteAnimationComponent localAnim = (SpriteAnimationComponent) e.getComponent(SpriteAnimationComponent.class);
					localAnim.update(animation);
				}else
				{
					animation.update();
					System.out.println("Frame: " + animation.getCurrentFrame());
				}
				
			}else if(e.hasComponent(SpriteAnimationComponent.class))
			{
				SpriteAnimationComponent animation = (SpriteAnimationComponent) e.getComponent(SpriteAnimationComponent.class);
				animation.update();
			}
			
			if(e.hasComponent(NetIdentityComponent.class))
			{
				NetIdentityComponent netIdentity = (NetIdentityComponent) e.getComponent(NetIdentityComponent.class);
				app.getNetworkManager().updateEntityInNetworkManager(e, netIdentity.getNetworkId());
			}
			
			//Update custom components
			for(AbstractComponent c : e.getComponents())
			{
				if(c instanceof CustomComponent)
				{
					c.update();
				}
			}
		}

		sortEntitiesZ();
		
		
		app.getNetworkManager().synchronize(this);
	}

	public void render()
	{		
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
				//check if visible
				if (e.shouldBeCulled()){
					if(mapIsInView(e)) { 
						sprite.submit(m_Renderer2D, transformation);
					}
				} else {
					sprite.submit(m_Renderer2D, transformation);
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
				if (e.shouldBeCulled()){
					if(mapIsInView(e)) { 
						text.submit(m_Renderer2D, transform);
					}
				} else {
					text.submit(m_Renderer2D, transform);
				}
			}

			if(e.hasComponent(SpriteAnimationComponent.class)) {
				SpriteAnimationComponent animation = (SpriteAnimationComponent) e.getComponent(SpriteAnimationComponent.class);

				Mat4 transformation = Mat4.identity();

				if (e.hasComponent(TransformComponent.class)) {
					TransformComponent transform = (TransformComponent) e.getComponent(TransformComponent.class);
					transformation = transform.getTransformationMatrix();
				} else if (e.hasComponent(NetTransformComponent.class)) {
					NetTransformComponent transform = (NetTransformComponent) e
							.getComponent(NetTransformComponent.class);
					transformation = transform.getTransformationMatrix();
				}
				// check is visible
				if (e.shouldBeCulled()){
					if(mapIsInView(e)) { 
						animation.submit(m_Renderer2D, transformation);
					}
				} else {
					animation.submit(m_Renderer2D, transformation);
				}
			}
			
			if(e.hasComponent(MeshComponent.class))
			{
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
				
				MeshComponent model = ((MeshComponent)e.getComponent(MeshComponent.class));
				
				model.draw(m_Renderer3D, transformation);
				
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
	
	public boolean mapIsInView(Entity entity){
		/*
		 *     p3____p4 
		 *      |    |
		 *      |____| 
		 *    p1      p2     
		 */
		Vec3 cam = m_Camera.getPosition();
		SpriteComponent sprite = entity.getSprite();
		TransformComponent transform = entity.getTransform();
		float view = Application.s_Viewport.getLength();
		float winX = Application.s_WindowSize.getX(); 
		float winY= Application.s_WindowSize.getY(); 
		if(sprite != null && transform != null) {
			float entWidth = sprite.getWidth();
			float entHeight = sprite.getHeight();
			Vec3 p1 = transform.getPosition();
			Vec2 p2 = new Vec2(p1.getX()+entWidth, p1.getY());
			Vec2 p3 = new Vec2(p1.getX(), p1.getY()+entHeight);
			Vec2 p4 = new Vec2(p1.getX()+(entWidth), p1.getY()+(entHeight));
			float xMaxSpan = cam.getX() + (view*1.34f);
			float xMinSpan = cam.getX() - (view*1.34f);
			float yMaxSpan = cam.getY() + (view*(winX/winY));
			float yMinSpan = cam.getY() - (view*(winX/winY));
			
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
	
	//2D version
	public ArrayList<Entity> pickEntities(float mouseX, float mouseY)
	{
		Vec3 worldPos = m_Camera.getWorldCoordinates(mouseX, mouseY);
		
		ArrayList<Entity> result = new ArrayList<>();
		
		for(Entity e : m_Entities)
		{
			if(e.hasComponent(ColliderComponent.class))
			{
				Vec3 position = null;
				if(e.hasComponent(TransformComponent.class))
				{
					TransformComponent transform = e.getTransform();
					position = transform.getPosition();
				}else if(e.hasComponent(NetTransformComponent.class))
				{
					NetTransformComponent transform = (NetTransformComponent) e.getComponent(NetTransformComponent.class);
					position = transform.getPosition();
				}else
				{
					continue;
				}
			
				ColliderComponent collider = (ColliderComponent) e.getComponent(ColliderComponent.class);
				
				if(collider.isInBounds(worldPos, position))
				{
					result.add(e);
				}
			}
		}
		
		return result;
	}
}
