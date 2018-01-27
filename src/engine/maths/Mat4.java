package engine.maths;

public class Mat4
{
	private float[] m_Elements;
	
	public Mat4()
	{
		this(0.0f);
	}
	
	public Mat4(float initVal)
	{
		m_Elements = new float[16];
		
		for(int i = 0; i < 16; i++)
		{
			m_Elements[i] = initVal;
		}
	}
	
	public void setElement(int index, float val)
	{
		m_Elements[index] = val;
	}
	
	public static Mat4 identity()
	{
		Mat4 identityMat = new Mat4(0.0f);
		identityMat.setElement(0, 1.0f);
		identityMat.setElement(5, 1.0f);
		identityMat.setElement(10, 1.0f);
		identityMat.setElement(15, 1.0f);
		
		return identityMat;
		
	}
}