package temp;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import engine.state.AbstractState;

public class TestState2 extends AbstractState
{
	float yPos = 0.0f;
	double counter = 0.0f;
	
	private Main app;
	
	public TestState2(Main app)
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
		yPos = (float) Math.sin(counter);
		
		GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glVertex2f(-0.5f, yPos);
			GL11.glVertex2f(0.5f, -0.5f);
			GL11.glVertex2f(0.0f, 0.5f);
		GL11.glEnd();
	}

	@Override
	public void update()
	{
		counter += 0.05f;
	}

	@Override
	public void keyAction(int key, int action)
	{
		if(key == GLFW.GLFW_KEY_SPACE && action == GLFW.GLFW_PRESS)
		{
			System.out.println("Key pressed in state 2");
		}else if(key == GLFW.GLFW_KEY_A && action == GLFW.GLFW_PRESS)
		{
			app.getStateManager().setState(Main.testState);
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
