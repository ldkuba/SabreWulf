
package engine.maths;

public class Mat3
{
	private float[] m_Elements;

	public Mat3()
	{
		this(0.0f);
	}

	public Mat3(float initVal)
	{
		m_Elements = new float[9];

		for (int i = 0; i < 9; i++)
		{
			m_Elements[i] = initVal;
		}
	}

	public void setElement(int index, float val)
	{
		m_Elements[index] = val;
	}

	public float[] getElements()
	{
		return m_Elements;
	}

	public static Mat3 identity()
	{
		Mat3 identityMat = new Mat3(0.0f);
		identityMat.setElement(0, 1.0f);
		identityMat.setElement(4, 1.0f);
		identityMat.setElement(8, 1.0f);

		return identityMat;

	}

	public Vec3 mult(Vec3 vector)
	{
		Vec3 result = new Vec3(0.0f, 0.0f, 0.0f);
		
		result.setX(vector.getX()*m_Elements[0] + vector.getY()*m_Elements[1] + vector.getZ()*m_Elements[2]);
		result.setY(vector.getX()*m_Elements[3] + vector.getY()*m_Elements[4] + vector.getZ()*m_Elements[5]);
		result.setZ(vector.getX()*m_Elements[6] + vector.getY()*m_Elements[7] + vector.getZ()*m_Elements[8]);
		
		return result;
	}
	
	public Mat3 mult(Mat3 other)
	{
		Mat3 result = new Mat3();

		float[] otherElements = other.getElements();

		//TODO

		return result;
	}
}