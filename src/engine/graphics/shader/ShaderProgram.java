package engine.graphics.shader;

import static org.lwjgl.opengl.GL11.GL_FALSE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL20;

/**
 * A representation of shader programs. Used to handle both vertex and fragment programs
 * @author nawro
 *
 */
public class ShaderProgram
{
	private int m_ID;
	private ShaderUniformLayout m_UniformLayout;
	
	/**
	 * Creates a shader program and initialises an empty uniform layout
	 */
	public ShaderProgram() 
	{
		m_UniformLayout = new ShaderUniformLayout();
	}
	
	/**
	 * Gets the OpenGL id for this shader program
	 * @return the id of this shader program
	 */
	public int getID()
	{
		return m_ID;
	}
	
	/**
	 * Sets this shader program as the currently used shader
	 */
	public void bind()
	{
		GL20.glUseProgram(m_ID);
	}
	
	/**
	 * Unbinds this shader program
	 */
	public void unbind()
	{
		GL20.glUseProgram(0);
	}
	
	/**
	 * Gets the layout of uniforms for this shader program
	 * @return the uniform layout of this shader program
	 */
	public ShaderUniformLayout getUniformLayout()
	{
		return m_UniformLayout;
	}
	
	/**
	 * Sets the locations of all uniforms specified in the uniform layout for this shader program
	 */
	public void locateUniforms()
	{
		m_UniformLayout.locateUniforms(m_ID);
	}
	
	/**
	 * Loads the vertex and fragment shaders into this shader program. This function parses, compiles and verifies the correctness of the shaders.
	 * @param filepath - path to the shader file
	 */
	public void loadShader(final String filepath)
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
		
		m_ID = CreateShader(vertexShaderSource, fragmentShaderSource);
	}
	
	/**
	 * Creates the shader program from the parsed source code
	 * @param vertexShader - source code of the vertex shader
	 * @param fragmentShader - source code of the fragment shader
	 * @return the OpenGL id of the created shader program
	 */
	private int CreateShader(final String vertexShader, final String fragmentShader)
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
	
	/**
	 * Compiles the shader from its source code
	 * @param source - source code of the shder
	 * @param type - type of the shader
	 * @return the id of the generated shader
	 */
	private int CompileShader(final String source, int type)
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

	/**
	 * Deletes the shader
	 */
	public void close()
	{
		GL20.glDeleteProgram(m_ID);
	}
}
