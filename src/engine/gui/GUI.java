package engine.gui;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import engine.gui.components.GuiComponent;
import engine.input.MouseListener;
import engine.scene.Scene;
import game.Main;

public class GUI implements MouseListener
{
	private Scene scene;
	private ArrayList<GuiComponent> components;
	private Main app;
	
	public GUI(Scene scene, Main app)
	{
		this.app = app;
		this.scene = scene;
		components = new ArrayList<>();
	}
	
	public void update()
	{
		
	}
	
	public void render()
	{
		
	}

	@Override
	public void mouseAction(int button, int action)
	{
		for(GuiComponent component : components)
		{
			if(action == GLFW.GLFW_PRESS)
			{
				
			}else
			{
				
			}
		}
	}
}
