package graphics;

import graphics.VertexLayout.AttributeTypes;

public class VertexAttribute
{
	private AttributeTypes m_Type;
	private int m_Count;
	private boolean m_Normalized;
	
	private static final int FLOAT_SIZE = 4;
	private static final int INT_SIZE = 4;
	private static final int VEC3_SIZE = 3 * FLOAT_SIZE;
	private static final int VEC4_SIZE = 4 * FLOAT_SIZE;
	private static final int MAT4_SIZE = 16 * FLOAT_SIZE;
	
	public VertexAttribute(AttributeTypes type, int count, boolean normalized)
	{
		m_Type = type;
		m_Count = count;
		m_Normalized = normalized;
	}
	
	public AttributeTypes getType() {return m_Type;}
	public int getCount() {return m_Count;}
	
	public int getSizeInBytes()
	{
		int size = 0;
		for(int i = 0; i < m_Count; i++)
		{
			if(m_Type.compareTo(AttributeTypes.Int) == 0)
			{
				size += VertexAttribute.INT_SIZE;
			}else if(m_Type.compareTo(AttributeTypes.Float) == 0)
			{
				size += VertexAttribute.FLOAT_SIZE;
			}else if(m_Type.compareTo(AttributeTypes.Vec3) == 0)
			{
				size += VertexAttribute.VEC3_SIZE;
			}else if(m_Type.compareTo(AttributeTypes.Vec4) == 0)
			{
				size += VertexAttribute.VEC4_SIZE;
			}else if(m_Type.compareTo(AttributeTypes.Mat4) == 0)
			{
				size += VertexAttribute.MAT4_SIZE;
			}
		}
		return size;
	}
	
	public boolean isNormalized() { return false; }
	
	public int getPrimitiveCount()
	{
		if(m_Type.compareTo(AttributeTypes.Int) == 0)
		{
			return m_Count;
		}else if(m_Type.compareTo(AttributeTypes.Float) == 0)
		{
			return m_Count;
		}else if(m_Type.compareTo(AttributeTypes.Vec3) == 0)
		{
			return m_Count * 3;
		}else if(m_Type.compareTo(AttributeTypes.Vec4) == 0)
		{
			return m_Count * 4;
		}else if(m_Type.compareTo(AttributeTypes.Mat4) == 0)
		{
			return m_Count * 16;
		}
		
		return -1;
	}

	public static int getFloatSize()
	{
		return FLOAT_SIZE;
	}

	public static int getIntSize()
	{
		return INT_SIZE;
	}

	public static int getVec3Size()
	{
		return VEC3_SIZE;
	}

	public static int getVec4Size()
	{
		return VEC4_SIZE;
	}

	public static int getMat4Size()
	{
		return MAT4_SIZE;
	}
}