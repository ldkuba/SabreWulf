package temp;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import graphics.IndexBuffer;
import graphics.VertexArray;
import graphics.VertexAttribute;
import graphics.VertexBuffer;
import graphics.VertexBuffer.VertexBufferUsage;
import graphics.VertexLayout;
import graphics.VertexLayout.AttributeTypes;
import graphics.shader.ShaderProgram;

public class Main {
	
	static int CompileShader(final String source, int type)
	{
		int id = GL20.glCreateShader(type);
		CharSequence cs = source;
		GL20.glShaderSource(id, cs);
		GL20.glCompileShader(id);
		
		int[] result = new int[1];
		GL20.glGetShaderiv(id, GL20.GL_COMPILE_STATUS, result);
		
		if(result[0] == GL_FALSE)
		{
			String message;
			message = GL20.glGetShaderInfoLog(id);
			System.out.println("Failed to compile " + ((id == GL20.GL_VERTEX_SHADER) ? "vertex" : "fragment") + " shader: ");
			System.out.println(message);
			GL20.glDeleteShader(id);
			return 0;
		}
		
		return id;
	}
	
	static int CreateShader(final String vertexShader, final String fragmentShader)
	{
		int program = GL20.glCreateProgram();
		
		int vs = CompileShader(vertexShader, GL20.GL_VERTEX_SHADER);
		int fs = CompileShader(fragmentShader, GL20.GL_FRAGMENT_SHADER);
		
		GL20.glAttachShader(program, vs);
		GL20.glAttachShader(program, fs);
		GL20.glLinkProgram(program);
		GL20.glValidateProgram(program);
		
		GL20.glDeleteShader(vs);
		GL20.glDeleteShader(fs);
		
		return program;
	}
	
	static int loadShader(final String filepath)
	{
		String vertexShaderSource = "";
		String fragmentShaderSource = "";
		boolean readingVertexShader = true;
		
		try {
			
			File file = new File(filepath);
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String readLine = "";
			
			while ((readLine = br.readLine()) != null) 
			{
				if(readLine.equals("<SEPARATOR>"))
				{
					readingVertexShader = false;
				}else
				{
					if(readingVertexShader)
					{
						vertexShaderSource += (readLine + "\n");
					}else
					{
						fragmentShaderSource += (readLine + "\n");
					}
				}
	        }
			
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return CreateShader(vertexShaderSource, fragmentShaderSource);
	}
	
	// The window handle
	private long window;

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		
		
		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		window = glfwCreateWindow(640, 480, "Hello World!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
		
	}

	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		VertexArray vao = new VertexArray();
		vao.bind();
		
		VertexLayout layout = new VertexLayout();
		layout.addAttribute(new VertexAttribute(AttributeTypes.Float, 2, false), 1);
		layout.addAttribute(new VertexAttribute(AttributeTypes.Float, 4, false), 1);
		
		float[] vertices = {
				-0.5f, -0.6f, 1.0f, 1.0f, 0.0f, 1.0f,
				0.4f, -0.5f, 0.2f, 0.7f, 0.5f, 1.0f,
				0.6f, 0.7f, 0.0f, 1.0f, 1.0f, 1.0f,
				-0.3f, 0.4f, 1.0f, 0.0f, 0.0f, 1.0f
		};
		
		ByteBuffer vertexData = BufferUtils.createByteBuffer(layout.getVertexSizeInBytes() * 4);
		
		for(int i = 0; i < vertices.length; i++)
		{
			vertexData.putFloat(vertices[i]);
		}
		vertexData.flip();
		
		VertexBuffer vbo = new VertexBuffer(layout, vertexData, 4, VertexBufferUsage.STATIC);
		
		vao.addVertexBuffer(vbo);
		
		int[] indicies = new int[] {0, 1, 2, 2, 3, 0};
		
		IndexBuffer ibo = new IndexBuffer(indicies, 6);
		
		ibo.unbind();
		vbo.unbind();
		vao.unbind();
				
		ShaderProgram shader = new ShaderProgram();	
		shader.loadShader("res/shaders/shader.txt");
		
		shader.bind();
		
		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window) ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			
			vao.bind();
			ibo.bind();
			
			GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, 0);
			
			ibo.unbind();
			vao.unbind();
			
			glfwSwapBuffers(window); // swap the color buffers (called after drawing)

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
		
		shader.close();
	}

	public static void main(String[] args) {
		new Main().run();
	}

}