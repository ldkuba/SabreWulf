package temp;

import org.lwjgl.opengl.GL11;

import engine.AbstractState;
import engine.Main;

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
	public void deactivate()
	{
		
	}
}
