package engine.font;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImageWrite;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTruetype;

import engine.graphics.texture.Texture;
import engine.maths.Vec2;

public class Font
{
	private Texture m_Texture;
	private STBTTBakedChar.Buffer m_CharData;
	private String m_Name;
	
	public Font(String name)
	{
		m_Name = name;

		Path p = FileSystems.getDefault().getPath("res/fonts", name);
		
		byte[] fontBytes = new byte[0];
		
		try
		{
			fontBytes = Files.readAllBytes(p);
		}catch (IOException e)
		{
			e.printStackTrace();
		}

		ByteBuffer fontBuffer = BufferUtils.createByteBuffer(fontBytes.length);
		fontBuffer.put(fontBytes, 0, fontBytes.length);
		
		m_CharData = STBTTBakedChar.malloc(64);
		ByteBuffer bitmap = BufferUtils.createByteBuffer(256*256);
		
		System.out.println(STBTruetype.stbtt_BakeFontBitmap(fontBuffer, 16.0f, bitmap, 256, 256, 0, m_CharData));// no guarantee this fits!
		
		CharSequence cs = "res/fontmap.png";
		
		STBImageWrite.stbi_write_png(cs, 256, 256, 1, bitmap, 1);
		
		m_Texture = new Texture(bitmap, 512, 512, name);	
	}
	
	public Vec2[] getUVs(char c)
	{
		Vec2[] resultUV = new Vec2[4];
		
		STBTTAlignedQuad quad = STBTTAlignedQuad.create();
		
		STBTruetype.stbtt_GetBakedQuad(m_CharData, 512, 512, c-32, BufferUtils.createFloatBuffer(1), BufferUtils.createFloatBuffer(1), quad, false);
		
		resultUV[0].setX(quad.s0());
		resultUV[0].setY(quad.t1());
		
		resultUV[1].setX(quad.s1());
		resultUV[1].setY(quad.t1());
		
		resultUV[2].setX(quad.s1());
		resultUV[2].setY(quad.t0());
		
		resultUV[3].setX(quad.s0());
		resultUV[3].setY(quad.t0());
		
		return resultUV;
	}
	
	public Texture getTexture()
	{
		return m_Texture;
	}
	
	public String getName()
	{
		return m_Name;
	}

	public void destroy()
	{
		m_CharData.free();
	}
}
