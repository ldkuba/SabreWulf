package engine.font;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImageWrite;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTPackContext;
import org.lwjgl.stb.STBTTPackedchar;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;

import engine.graphics.texture.Texture;
import engine.maths.Vec2;

public class Font
{
	private Texture m_Texture;
	private STBTTPackedchar.Buffer m_CharData;
	private String m_Name;

	public Font(String name)
	{
		m_Name = name;

		try (MemoryStack stack = MemoryStack.stackPush()) {
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
	
			ByteBuffer bitmap = BufferUtils.createByteBuffer(1024 * 1024);
	
			m_CharData = STBTTPackedchar.malloc(3 * 128);
	
			STBTTPackContext pc = STBTTPackContext.malloc();
			STBTruetype.stbtt_PackBegin(pc, bitmap, 1024, 1024, 0, 1);
	
			m_CharData.position(0 * 128 + 32);
			STBTruetype.stbtt_PackSetOversampling(pc, 1, 1);
			STBTruetype.stbtt_PackFontRange(pc, fontBuffer, 0, 24.0f, 32, m_CharData);
	
			m_CharData.position(1 * 128 + 32);
			STBTruetype.stbtt_PackSetOversampling(pc, 2, 2);
			STBTruetype.stbtt_PackFontRange(pc, fontBuffer, 0, 24.0f, 32, m_CharData);
	
			m_CharData.position(2 * 128 + 32);
			STBTruetype.stbtt_PackSetOversampling(pc, 3, 1);
			STBTruetype.stbtt_PackFontRange(pc, fontBuffer, 0, 24.0f, 32, m_CharData);
	
			STBTruetype.stbtt_PackEnd(pc);
			pc.free();
	
			CharSequence cs = "res/fontmap.png";
	
			STBImageWrite.stbi_write_png(cs, 512, 512, 1, bitmap, 4);
	
			m_Texture = new Texture(bitmap, 512, 512, name);
		}
	}

	public Vec2[] getUVs(char c)
	{
		Vec2[] resultUV = new Vec2[4];

		STBTTAlignedQuad quad = STBTTAlignedQuad.malloc();

		STBTruetype.stbtt_GetPackedQuad(m_CharData, 512, 512, c, BufferUtils.createFloatBuffer(1),
				BufferUtils.createFloatBuffer(1), quad, false);

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
