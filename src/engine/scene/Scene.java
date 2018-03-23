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

/**
 * The scene is what is visible on the screen
 * 
 * @author SabreWulf
 *
 */
public class Scene {
	private ArrayList<Entity> m_Entities;
	private ArrayList<Entity> m_DeletedEntities;
	private Renderer2D m_Renderer2D;
	private Renderer3D m_Renderer3D;
	private Camera m_Camera;
	private Application app;
	private int m_ID;

	/**
	 * Create a new scene for a given application
	 * 
	 * @param id
	 * @param app
	 */
	public Scene(int id, Application app) {
		this.app = app;
		m_ID = id;
	}

	/**
	 * Initialise the scene
	 */
	public void init() {
		m_Entities = new ArrayList<Entity>();
		m_DeletedEntities = new ArrayList<>();

		if (!app.isHeadless()) {
			float aspectRatio = 4.0f / 3.0f;
			m_Camera = new Camera();
			m_Camera.setPosition(new Vec3(0.0f, 0.0f, -20.0f));
			m_Camera.setProjectionMatrix(MathUtil.perspProjMat(aspectRatio, 70.0f, 0.1f, 100.0f));
			m_Renderer2D = new Renderer2D();
			m_Renderer3D = new Renderer3D(m_Camera);
		}
	}

	/**
	 * Sort the entities by z coordinate value - for 3D
	 */
	private void sortEntitiesZ() {
		class CompareZ implements Comparator<Entity> {

			@Override
			public int compare(Entity e1, Entity e2) {
				float z1 = 0.0f;
				float z2 = 0.0f;
				if (e1.hasComponent(TransformComponent.class)) {
					z1 = e1.getTransform().getPosition().getZ();
				} else if (e1.hasComponent(NetTransformComponent.class)) {
					z1 = ((NetTransformComponent) e1.getComponent(NetTransformComponent.class)).getPosition().getZ();
				}
				if (e2.hasComponent(TransformComponent.class)) {
					z2 = e2.getTransform().getPosition().getZ();
				} else if (e2.hasComponent(NetTransformComponent.class)) {
					z2 = ((NetTransformComponent) e2.getComponent(NetTransformComponent.class)).getPosition().getZ();
				}
				if (z1 > z2) {
					return -1;
				} else if (z1 == z2) {
					return 0;
				} else {
					return 1;
				}
			}

		}
		Collections.sort(m_Entities, new CompareZ());
	}

	/**
	 * Check if an entity id is free (not used)
	 * 
	 * @param id
	 * @return
	 */
	private boolean isIdFree(int id) {
		for (Entity e : m_Entities) {
			if (e.getId() == id) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Add an entity to the scene
	 * 
	 * @param e
	 */
	public void addEntity(Entity e) {
		if (!m_Entities.contains(e)) {
			m_Entities.add(e);
			int id = 0;
			while (!isIdFree(id))
				id++;

			e.setId(id);
		}
		sortEntitiesZ();
	}

	/**
	 * Remove an entity from the scene
	 * 
	 * @param e
	 */
	public void removeEntity(Entity e) {
		m_DeletedEntities.add(e);
	}

	/**
	 * Remove all entities from the scene
	 */
	public void removeAllEnties() {
		m_Entities.clear();
	}

	/**
	 * Get all entities on the scene
	 * 
	 * @return
	 */
	public ArrayList<Entity> getEntities() {
		return m_Entities;
	}

	/**
	 * Update the scene
	 */
	public void update() {
		
		removeMarkedEntities();
		
		for (Entity e : m_Entities) {
			if (e.hasComponent(NetSpriteAnimationComponent.class)) {
				NetSpriteAnimationComponent animation = (NetSpriteAnimationComponent) e
						.getComponent(NetSpriteAnimationComponent.class);
				if (e.hasComponent(SpriteAnimationComponent.class)) {
					SpriteAnimationComponent localAnim = (SpriteAnimationComponent) e
							.getComponent(SpriteAnimationComponent.class);
					localAnim.update(animation);
				} else {
					animation.update();
				}
			} else if (e.hasComponent(SpriteAnimationComponent.class)) {
				SpriteAnimationComponent animation = (SpriteAnimationComponent) e
						.getComponent(SpriteAnimationComponent.class);
				animation.update();
			}
			if (e.hasComponent(NetIdentityComponent.class)) {
				NetIdentityComponent netIdentity = (NetIdentityComponent) e.getComponent(NetIdentityComponent.class);
				app.getNetworkManager().updateEntityInNetworkManager(e, netIdentity.getNetworkId());
			}

			/* Update custom components */
			for (AbstractComponent c : e.getComponents()) {
				if (c instanceof CustomComponent) {
					c.update();
				}
			}

			if(e.getLifeTime() != -1) {
				e.changeLifeTime(app.getTimer().getElapsedTime()/1000.0f);
				if(e.getLifeTime() < 0) {
					removeEntity(e);
				}
			}
		}
		
		sortEntitiesZ();
		app.getNetworkManager().synchronize(this);
	}
	
	public void removeMarkedEntities()
	{
		m_Entities.removeAll(m_DeletedEntities);
		m_DeletedEntities.clear();
	}

	/**
	 * Draw the entities in the scene onto the screen
	 */
	public void render() {
		m_Renderer2D.init(m_Camera);

		for (Entity e : m_Entities) {
			if (e.hasComponent(SpriteComponent.class)) {
				SpriteComponent sprite = (SpriteComponent) e.getComponent(SpriteComponent.class);
				Mat4 transformation = Mat4.identity();
				if (e.hasComponent(TransformComponent.class)) {
					TransformComponent transform = (TransformComponent) e.getComponent(TransformComponent.class);
					transformation = transform.getTransformationMatrix();
				} else if (e.hasComponent(NetTransformComponent.class)) {
					NetTransformComponent transform = (NetTransformComponent) e
							.getComponent(NetTransformComponent.class);
					transformation = transform.getTransformationMatrix();
				}
				/* check if visible */
				if (e.shouldBeCulled()) {
					if (mapIsInView(e)) {
						sprite.submit(m_Renderer2D, transformation);
					}
				} else {
					sprite.submit(m_Renderer2D, transformation);
				}
			}

			if (e.hasComponent(TextComponent.class)) {
				TextComponent text = (TextComponent) e.getComponent(TextComponent.class);
				TransformComponent transform = new TransformComponent();
				if (e.hasComponent(TransformComponent.class)) {
					transform = e.getTransform();
				} else if (e.hasComponent(NetTransformComponent.class)) {
					NetTransformComponent netTransform = (NetTransformComponent) e
							.getComponent(NetTransformComponent.class);
					transform.setPosition(netTransform.getPosition());
					transform.setRotationAngles(netTransform.getRotationAngles());
					transform.setScale(netTransform.getScale());
				}
				/* check if visible */
				if (e.shouldBeCulled()) {
					if (mapIsInView(e)) {
						text.submit(m_Renderer2D, transform);
					}
				} else {
					text.submit(m_Renderer2D, transform);
				}
			}

			if (e.hasComponent(SpriteAnimationComponent.class)) {
				SpriteAnimationComponent animation = (SpriteAnimationComponent) e
						.getComponent(SpriteAnimationComponent.class);
				Mat4 transformation = Mat4.identity();
				if (e.hasComponent(TransformComponent.class)) {
					TransformComponent transform = (TransformComponent) e.getComponent(TransformComponent.class);
					transformation = transform.getTransformationMatrix();
				} else if (e.hasComponent(NetTransformComponent.class)) {
					NetTransformComponent transform = (NetTransformComponent) e
							.getComponent(NetTransformComponent.class);
					transformation = transform.getTransformationMatrix();
				}
				/* check is visible */
				if (e.shouldBeCulled()) {
					if (mapIsInView(e)) {
						animation.submit(m_Renderer2D, transformation);
					}
				} else {
					animation.submit(m_Renderer2D, transformation);
				}
			}

			if (e.hasComponent(MeshComponent.class)) {
				Mat4 transformation = Mat4.identity();
				if (e.hasComponent(TransformComponent.class)) {
					TransformComponent transform = (TransformComponent) e.getComponent(TransformComponent.class);
					transformation = transform.getTransformationMatrix();
				} else if (e.hasComponent(NetTransformComponent.class)) {
					NetTransformComponent transform = (NetTransformComponent) e
							.getComponent(NetTransformComponent.class);
					transformation = transform.getTransformationMatrix();
				}
				MeshComponent model = ((MeshComponent) e.getComponent(MeshComponent.class));
				model.draw(m_Renderer3D, transformation);
			}
		}

		m_Renderer2D.drawAll();
	}

	/**
	 * Get the scenes camera
	 * 
	 * @return
	 */
	public Camera getCamera() {
		return m_Camera;
	}

	/**
	 * Get the 2D renderer
	 * 
	 * @return
	 */
	public Renderer2D getRenderer2D() {
		return m_Renderer2D;
	}

	/**
	 * See if an entity is in camera view - used only for game state
	 * 
	 * @param entity
	 * @return
	 */
	public boolean mapIsInView(Entity entity) {
		Vec3 cam = m_Camera.getPosition();
		SpriteComponent sprite = entity.getSprite();
		TransformComponent transform = entity.getTransform();
		float view = Application.s_Viewport.getLength();
		float winX = Application.s_WindowSize.getX();
		float winY = Application.s_WindowSize.getY();
		float xScalar = 1.34f;
		if (sprite != null && transform != null) {
			float entWidth = sprite.getWidth();
			float entHeight = sprite.getHeight();
			Vec3 p1 = transform.getPosition();
			Vec2 p2 = new Vec2(p1.getX() + entWidth, p1.getY());
			Vec2 p3 = new Vec2(p1.getX(), p1.getY() + entHeight);
			Vec2 p4 = new Vec2(p1.getX() + (entWidth), p1.getY() + (entHeight));
			float xMaxSpan = cam.getX() + (view * xScalar);
			float xMinSpan = cam.getX() - (view * xScalar);
			float yMaxSpan = cam.getY() + (view * (winX / winY));
			float yMinSpan = cam.getY() - (view * (winX / winY));

			if (p4.getX() < xMaxSpan && p4.getX() > xMinSpan && p4.getY() < yMaxSpan && p4.getY() > yMinSpan) {
				return true;
			} else if (p3.getX() < xMaxSpan && p3.getX() > xMinSpan && p3.getY() < yMaxSpan && p3.getY() > yMinSpan) {
				return true;
			} else if (p2.getX() < xMaxSpan && p2.getX() > xMinSpan && p2.getY() < yMaxSpan && p2.getY() > yMinSpan) {
				return true;
			} else if (p1.getX() < xMaxSpan && p1.getX() > xMinSpan && p1.getY() < yMaxSpan && p1.getY() > yMinSpan) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Pick entities - 2D version
	 * 
	 * @param mouseX
	 * @param mouseY
	 * @return
	 */
	public ArrayList<Entity> pickEntities(float mouseX, float mouseY) {
		Vec3 worldPos = m_Camera.getWorldCoordinates(mouseX, mouseY);
		ArrayList<Entity> result = new ArrayList<>();

		for (Entity e : m_Entities) {
			if (e.hasComponent(ColliderComponent.class)) {
				Vec3 position = null;
				if (e.hasComponent(TransformComponent.class)) {
					TransformComponent transform = e.getTransform();
					position = transform.getPosition();
				} else if (e.hasComponent(NetTransformComponent.class)) {
					NetTransformComponent transform = (NetTransformComponent) e
							.getComponent(NetTransformComponent.class);
					position = transform.getPosition();
				} else {
					continue;
				}
				ColliderComponent collider = (ColliderComponent) e.getComponent(ColliderComponent.class);
				if (collider.isInBounds(worldPos, position)) {
					result.add(e);
				}
			}
		}

		return result;
	}

	/**
	 * Pick entities - 3D version
	 * 
	 * @param worldPos
	 * @return
	 */
	public ArrayList<Entity> pickEntities(Vec3 worldPos) {
		ArrayList<Entity> result = new ArrayList<>();

		for (Entity e : m_Entities) {
			if (e.hasComponent(ColliderComponent.class)) {
				Vec3 position = null;
				if (e.hasComponent(TransformComponent.class)) {
					TransformComponent transform = e.getTransform();
					position = transform.getPosition();
				} else if (e.hasComponent(NetTransformComponent.class)) {
					NetTransformComponent transform = (NetTransformComponent) e
							.getComponent(NetTransformComponent.class);
					position = transform.getPosition();
				} else {
					continue;
				}

				ColliderComponent collider = (ColliderComponent) e.getComponent(ColliderComponent.class);
				if (collider.isInBounds(worldPos, position)) {
					result.add(e);
				}
			}
		}
		return result;
	}

	/**
	 * Instantiate the timer
	 * 
	 * @param e
	 * @param time
	 */
	public void instantiate(Entity e, float time) {
		e.setLifeTime(time);
		addEntity(e);
	}
}
