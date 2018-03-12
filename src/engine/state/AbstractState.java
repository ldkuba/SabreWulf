package engine.state;

import org.lwjgl.glfw.GLFW;

import engine.application.Application;
import engine.input.KeyboardListener;
import engine.input.MouseListener;
import engine.scene.Scene;

/*
 * AbstractState is a class that is extended by all different
 * game states
 */

public abstract class AbstractState implements KeyboardListener, MouseListener {

	protected Scene scene;
	private Application application;
	
	//FPS counter
	private int frame = 0;
	private float second = 0;
	
	public AbstractState(Application application)
	{
		this.application = application;
		scene = new Scene(0, application);
	}
	
	//initialise state
	public void init()
	{
		scene.init();
		application.getGui().init(scene);
	}
	
	//draw state
	public void render()
	{
		scene.render();
	}
	
	//update state
	public void update()
	{
		// FPS Counter
		if (GLFW.glfwGetTime() - second >= 1.0f) {
			second += 1.0f;
			System.out.println("FPS: " + frame);
			frame = 0;
		}
		frame++;
		
		scene.update();
	}
	
	public abstract void deactivate(); //opposite of initialise
	
	public Scene getScene()
	{
		return scene;
	}
}
