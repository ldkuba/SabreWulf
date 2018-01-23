package temp;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import engine.AbstractState;
import engine.Main;

public class TestState extends AbstractState
{
	float xPos = 0.0f;
	double counter = 0.0f;
	
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
		xPos = (float) Math.sin(counter);
		
		GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glVertex2f(-0.5f, -0.5f);
			GL11.glVertex2f(0.5f, -0.5f);
			GL11.glVertex2f(xPos, 0.5f);
		GL11.glEnd();
	}

	@Override
	public void update()
	{
		counter += 0.05f;
		
		if(isPressed(GLFW.GLFW_KEY_SPACE))
		{
			
		}
	}

	@Override
	public void deactivate()
	{
		
	}
}
