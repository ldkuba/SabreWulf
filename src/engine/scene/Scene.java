package engine.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.*;
//import engine.entity.component.NetIdentityComponent;
import engine.graphics.camera.Camera;
import engine.graphics.renderer.Renderer2D;
import engine.graphics.renderer.Renderer3D;
import engine.gui.components.ProgressBar;
import engine.maths.Mat4;
import engine.maths.MathUtil;
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

				// check if visible

				sprite.submit(m_Renderer2D, transformation);
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
			if(e.hasComponent(ProgressBarComponent.class))
			{
				ProgressBarComponent progressBar = (ProgressBarComponent) e.getComponent(ProgressBarComponent.class);

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

				progressBar.submit(m_Renderer2D, transform);
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

	public void isInView(Entity entity)
	{
		// incomplete
		Vec3 camPos = m_Camera.getPosition();
		SpriteComponent sprite = entity.getSprite();
		TransformComponent transform = entity.getTransform();
		Vec3 entPos = transform.getPosition();
		float entWidth = sprite.getWidth();
		float entHeight = sprite.getHeight();
		if(entPos.getX() + entWidth <= camPos.getX())
		{

		}
		if(entPos.getY() + entHeight <= camPos.getY())
		{

		}

	}
}
