package temp;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import engine.graphics.IndexBuffer;
import engine.graphics.VertexArray;
import engine.graphics.VertexAttribute;
import engine.graphics.VertexBuffer;
import engine.graphics.VertexBuffer.VertexBufferUsage;
import engine.graphics.VertexLayout;
import engine.graphics.VertexLayout.AttributeTypes;
import engine.graphics.camera.Camera;
import engine.graphics.shader.ShaderProgram;
import engine.maths.Mat4;
import engine.maths.MathUtil;
import engine.maths.Vec3;
import engine.maths.Vec4;
import engine.state.AbstractState;

public class TestState extends AbstractState
{
	ShaderProgram shader;
	VertexArray vao;
	IndexBuffer ibo;
	
	int modelMatrixLocation;
	int viewMatrixLocation;
	int projectionMatrixLocation;
	
	Camera camera;
	float angle = 0.0f;
	
	private Main app;
	
	public TestState(Main app)
	{
		this.app = app;
	}
	
	@Override
	public void init()
	{		
		vao = new VertexArray();
		vao.bind();
		
		VertexLayout layout = new VertexLayout();
		layout.addAttribute(new VertexAttribute(AttributeTypes.Float, 3, false), 1);
		layout.addAttribute(new VertexAttribute(AttributeTypes.Float, 4, false), 1);
		
		float[] vertices = new float[] {
				-1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f,
				1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f,
				-1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,
				1.0f, -1.0f, 1.0f, 0.7f, 0.7f, 0.7f, 1.0f,
				-1.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
				1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f,
				-1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f,
				1.0f, -1.0f, 0.0f, 0.7f, 0.7f, 0.7f, 1.0f
		};
		
		ByteBuffer vertexBuffer = BufferUtils.createByteBuffer(layout.getVertexSizeInBytes() * 8);
		for(int i = 0; i < 8 * 7; i++)
		{
			vertexBuffer.putFloat(vertices[i]);
		}
		vertexBuffer.flip();
		
		VertexBuffer vbo = new VertexBuffer(layout, vertexBuffer, 8, VertexBufferUsage.STATIC);
		
		vao.addVertexBuffer(vbo);
		
		int[] indices = new int[] {
			0, 2, 1,
			2, 3, 1,
			
			0, 6, 2,
			6, 0, 4,
			
			5, 4, 6,
			5, 6, 7,
			
			1, 3, 7,
			5, 1, 7
		};
		
		ibo = new IndexBuffer(indices, 24);
		
		vbo.unbind();
		ibo.unbind();
		vao.unbind();
		
		shader = new ShaderProgram();
		shader.loadShader("res/shaders/shader.txt");
		shader.bind();
		
		modelMatrixLocation = GL20.glGetUniformLocation(shader.getID(), "modelMatrix");
		viewMatrixLocation = GL20.glGetUniformLocation(shader.getID(), "viewMatrix");
		projectionMatrixLocation = GL20.glGetUniformLocation(shader.getID(), "projectionMatrix");
		
		camera = new Camera();
		camera.setPosition(new Vec3(0, 0, -5.0f));
		camera.setDirection(new Vec3(0, 0, 1.0f));
		
		float aspectRatio = 4.0f/3.0f;
		camera.setProjectionMatrix(MathUtil.perspProjMat(aspectRatio, 75, 0.1f, 100.0f));
		//camera.setProjectionMatrix(MathUtil.orthoProjMat(5.0f, -5.0f, 5.0f*aspectRatio, -5.0f*aspectRatio, 0.1f, 100.0f));
	}

	@Override
	public void update()
	{
		if(app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_UP))
		{
			Vec3 newPos = Vec3.add(camera.getPosition(), new Vec3(0.04f*(float)Math.sin(angle), 0, 0.04f*(float)Math.cos(angle)));
			
			camera.setPosition(newPos);
		}
		
		if(app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_DOWN))
		{
			Vec3 newPos = Vec3.add(camera.getPosition(), new Vec3(-0.04f*(float)Math.sin(angle), 0, -0.04f*(float)Math.cos(angle)));
			
			camera.setPosition(newPos);
		}
		
		if(app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_Z))
		{
			Vec3 newPos = Vec3.add(camera.getPosition(), new Vec3(0.04f*(float)Math.cos(angle), 0, -0.04f*(float)Math.sin(angle)));
			
			camera.setPosition(newPos);
		}
		
		if(app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_X))
		{
			Vec3 newPos = Vec3.add(camera.getPosition(), new Vec3(-0.04f*(float)Math.cos(angle), 0, 0.04f*(float)Math.sin(angle)));
			
			camera.setPosition(newPos);
		}
		
		if(app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_LEFT))
		{
			angle += 0.02f;
			
			Vec3 dir = new Vec3((float) Math.sin(angle), 0.0f, (float) Math.cos(angle));
			camera.setDirection(dir);
		}
		
		if(app.getInputManager().isKeyPressed(GLFW.GLFW_KEY_RIGHT))
		{
			angle -= 0.02f;
			
			Vec3 dir = new Vec3((float) Math.sin(angle), 0.0f, (float) Math.cos(angle));
			camera.setDirection(dir);
		}
		
		System.out.println("Camera X: " + camera.getPosition().getX() + " Camera Y: " + camera.getPosition().getY() + " Camera Z: " + camera.getPosition().getZ());
	}

	@Override
	public void render()
	{
		vao.bind();
		ibo.bind();
		
		Mat4 modelMatrix = Mat4.identity();
		Mat4 viewMatrix = camera.getViewMatrix();
		Mat4 projectionMatrix = camera.getProjectionMatrix();

		GL20.glUniformMatrix4fv(modelMatrixLocation, true, modelMatrix.getElements());
		GL20.glUniformMatrix4fv(viewMatrixLocation, true, viewMatrix.getElements());
		GL20.glUniformMatrix4fv(projectionMatrixLocation, true, projectionMatrix.getElements());
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, 24, GL11.GL_UNSIGNED_INT, 0);
		
		ibo.unbind();
		vao.unbind();
	}
	
	@Override
	public void keyAction(int key, int action)
	{
		if(key == GLFW.GLFW_KEY_SPACE && action == GLFW.GLFW_PRESS)
		{
			System.out.println("Key pressed in state 1");
		}else if(key == GLFW.GLFW_KEY_A && action == GLFW.GLFW_PRESS)
		{
			app.getStateManager().setState(Main.testState2);
		}
	}
	
	@Override 
	public void mouseAction(int key, int action)
	{
		
	}

	@Override
	public void deactivate()
	{
		shader.unbind();
		shader.close();
	}
}
