package game;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import engine.state.AbstractState;

public class TestState extends AbstractState
{
	//stuff for checking if networking works
	float xPos = 0.0f;
	float speed = 0.03f;
	
	private Main app;
	
	public TestState(Main app)
	{
		this.app = app;
	}
	
	@Override
	public void init()
	{
		
	}

	@Override
	public void render()
	{
		GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glVertex2f(xPos - 0.5f, -0.5f);
			GL11.glVertex2f(xPos + 0.5f, -0.5f);
			GL11.glVertex2f(xPos, 0.5f);
		GL11.glEnd();
	}

	@Override
	public void update()
	{
		if(app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_LEFT))
		{
			if(xPos > -1.0f)
			{
				xPos -= speed;
			}
		}else if(app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_RIGHT))
		{
			if(xPos < 1.0f)
			{
				xPos += speed;
			}
		}
	}
	
	@Override
	public void keyAction(int key, int action)
	{
		if(key == GLFW.GLFW_KEY_SPACE && action == GLFW.GLFW_PRESS)
		{
			System.out.println("Key pressed in state 1");
		}else if(key == GLFW.GLFW_KEY_A && action == GLFW.GLFW_PRESS)
		{
			app.getStateManager().setCurrentState(Main.testState2);
		}
	}
	
	@Override 
	public void mouseAction(int key, int action)
	{
		
	}

	@Override
	public void deactivate()
	{
		
	}
}
