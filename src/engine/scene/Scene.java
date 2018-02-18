package engine.scene;

import java.util.ArrayList;

import engine.application.Application;
import engine.entity.Entity;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.graphics.camera.Camera;
import engine.graphics.renderer.Renderer2D;
import engine.graphics.renderer.Renderer3D;
import engine.maths.Mat4;
import engine.maths.MathUtil;
import engine.maths.Vec2;
import engine.maths.Vec3;

public class Scene {
	private int m_ID;
	private ArrayList<Entity> m_Entities;

	private Renderer2D m_Renderer2D;
	private Renderer3D m_Renderer3D; // Currently not used
	private Camera m_Camera;

	public Scene(int id) {
		m_ID = id;

		m_Entities = new ArrayList<Entity>();

		float aspectRatio = 4.0f / 3.0f;
		
		m_Camera = new Camera();
		m_Camera.setPosition(new Vec3(0.0f, 0.0f, -10.0f));
		m_Camera.setProjectionMatrix(MathUtil.orthoProjMat(-10.0f, 10.0f, 10.0f * aspectRatio, -10.0f * aspectRatio, 0.01f, 100.0f));

	}

	public void initRenderer() {
		m_Renderer2D = new Renderer2D();
		m_Renderer3D = new Renderer3D();
	}

	public void addEntity(Entity e) {
		if (!m_Entities.contains(e)) {
			m_Entities.add(e);
		}
	}

	public void removeEntity(Entity e) {
		m_Entities.remove(e);
	}

	public void update() {
		for (Entity e : m_Entities) {
			if (e.hasComponent(TransformComponent.class)) {
				TransformComponent transform = (TransformComponent) e.getComponent(TransformComponent.class);
				// transform.update(); just an example, the transform component
				// doesnt need automatic updating
			}
		}
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

				sprite.submit(m_Renderer2D, transformation);
			}
		}

		m_Renderer2D.drawAll();
	}

	public Camera getCamera() {
		return m_Camera;
	}
	
	public Renderer2D getRenderer2D(){
		return m_Renderer2D;
	}
}
