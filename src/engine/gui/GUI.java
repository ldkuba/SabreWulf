package engine.gui;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import engine.application.Application;
import engine.entity.component.SpriteComponent;
import engine.entity.component.TransformComponent;
import engine.gui.components.GuiComponent;
import engine.input.MouseListener;
import engine.maths.Vec3;
import engine.scene.Scene;

public class GUI implements MouseListener
{
	private Scene scene;
	private ArrayList<GuiComponent> components;
	private Application app;
	
	public GUI(Application app)
	{
		this.app = app;
		this.scene = scene;
		components = new ArrayList<>();
		
		app.getInputManager().addMouseListener(this);
	}
	
	public void init(Scene newScene)
	{
		if(this.scene != null)
		{
			for(GuiComponent component : components)
			{
				this.scene.removeEntity(component.getEntity());
			}
			
			components.clear();
		}
		
		this.scene = newScene;
	}
	
	public void update()
	{
		
	}
	
	public void render()
	{
		for(GuiComponent component : components)
		{
			TransformComponent transform = component.getEntity().getTransform();
			SpriteComponent sprite = component.getEntity().getSprite();
			
			Vec3 result = new Vec3();
			
			//scene.getCamera().getViewport()
			//app.getWindowSize()
			
			transform.setPosition(result);
		}
	}
	
	public void add(GuiComponent component)
	{
		components.add(component);
		scene.addEntity(component.getEntity());
	}
	
	public void remove(GuiComponent component)
	{
		components.remove(component);
		scene.removeEntity(component.getEntity());
	}

	@Override
	public void mouseAction(int button, int action)
	{
		for(GuiComponent component : components)
		{
			if(app.getInputManager().getMouseX() >= component.getX() && app.getInputManager().getMouseX() <= component.getX() + component.getWidth() &&
					app.getInputManager().getMouseY() >= component.getY() && app.getInputManager().getMouseY() <= component.getY() + component.getHeight())
			{
				if(action == GLFW.GLFW_PRESS)
				{
					component.onPress(button);
				}else if(action == GLFW.GLFW_RELEASE)
				{
					component.onRelease(button);
				}else if(action == GLFW.GLFW_REPEAT)
				{
					component.onRepeat(button);
				}
			}
		}
	}
}
