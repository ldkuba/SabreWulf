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
	
	public float[] getElements()
	{
		return m_Elements;
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

	public Mat4 mult(Mat4 other)
	{
		Mat4 result = new Mat4();
		
		float[] otherElements = other.getElements();
		
		result.setElement(0, m_Elements[0]*otherElements[0] + m_Elements[1]*otherElements[4] + m_Elements[2]*otherElements[8] + m_Elements[3]*otherElements[12]);
		result.setElement(1, m_Elements[4]*otherElements[0] + m_Elements[5]*otherElements[4] + m_Elements[6]*otherElements[8] + m_Elements[7]*otherElements[12]);
		result.setElement(2, m_Elements[8]*otherElements[0] + m_Elements[9]*otherElements[4] + m_Elements[10]*otherElements[8] + m_Elements[11]*otherElements[12]);
		result.setElement(3, m_Elements[12]*otherElements[0] + m_Elements[13]*otherElements[4] + m_Elements[14]*otherElements[8] + m_Elements[15]*otherElements[12]);
		
		result.setElement(4, m_Elements[0]*otherElements[1] + m_Elements[1]*otherElements[5] + m_Elements[2]*otherElements[9] + m_Elements[3]*otherElements[13]);
		result.setElement(5, m_Elements[4]*otherElements[1] + m_Elements[5]*otherElements[5] + m_Elements[6]*otherElements[9] + m_Elements[7]*otherElements[13]);
		result.setElement(6, m_Elements[8]*otherElements[1] + m_Elements[9]*otherElements[5] + m_Elements[10]*otherElements[9] + m_Elements[11]*otherElements[13]);
		result.setElement(7, m_Elements[12]*otherElements[1] + m_Elements[13]*otherElements[5] + m_Elements[14]*otherElements[9] + m_Elements[15]*otherElements[13]);
		
		result.setElement(8, m_Elements[0]*otherElements[2] + m_Elements[1]*otherElements[6] + m_Elements[2]*otherElements[10] + m_Elements[3]*otherElements[14]);
		result.setElement(9, m_Elements[4]*otherElements[2] + m_Elements[5]*otherElements[6] + m_Elements[6]*otherElements[10] + m_Elements[7]*otherElements[14]);
		result.setElement(10, m_Elements[8]*otherElements[2] + m_Elements[9]*otherElements[6] + m_Elements[10]*otherElements[10] + m_Elements[11]*otherElements[14]);
		result.setElement(11, m_Elements[12]*otherElements[2] + m_Elements[13]*otherElements[6] + m_Elements[14]*otherElements[10] + m_Elements[15]*otherElements[14]);
		
		result.setElement(12, m_Elements[0]*otherElements[3] + m_Elements[1]*otherElements[7] + m_Elements[2]*otherElements[11] + m_Elements[3]*otherElements[15]);
		result.setElement(13, m_Elements[4]*otherElements[3] + m_Elements[5]*otherElements[7] + m_Elements[6]*otherElements[11] + m_Elements[7]*otherElements[15]);
		result.setElement(14, m_Elements[8]*otherElements[3] + m_Elements[9]*otherElements[7] + m_Elements[10]*otherElements[11] + m_Elements[11]*otherElements[15]);
		result.setElement(15, m_Elements[12]*otherElements[3] + m_Elements[13]*otherElements[7] + m_Elements[14]*otherElements[11] + m_Elements[15]*otherElements[15]);
		
		return result;
	}
}