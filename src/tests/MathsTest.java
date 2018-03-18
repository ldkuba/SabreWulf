package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import engine.maths.*;

public class MathsTest {

	@Test
	public void Vec2Test() {
		Vec2 pos = new Vec2(5.0f, 84.0f);
		//test get and setters
		assertEquals(5.0f, pos.getX(), 0);
		assertEquals(84.0f, pos.getY(), 0);
		pos.setX(87.0f);
		assertEquals(87.0f,pos.getX(),0);
		pos.setY(-54.0f); //check works for negatives
		assertEquals(-54.0f,pos.getY(),0);
		
		//max/min float values
		pos.setX(Float.MAX_VALUE);
		assertEquals(Float.MAX_VALUE, pos.getX(), 0);
		pos.setY(-Float.MAX_VALUE);
		assertEquals(-Float.MAX_VALUE, pos.getY(), 0);
		
		Vec2 pos2 = new Vec2(2.0f, 2.0f);
		//length
		assertEquals(2.828427f,pos2.getLength(),0);
		
		//adding to a bound
		pos.add(pos2);
		assertEquals(Float.MAX_VALUE, pos.getX(), 0);
		assertEquals(-Float.MAX_VALUE, pos.getY(), 0);
		
		//adding normally
		Vec2 pos3 = new Vec2(-4.0f, 4.0f);
		pos2.add(pos3);
		assertEquals(-2.0f, pos2.getX(), 0);
		assertEquals(6.0f, pos2.getY(), 0);
		
		//adding very small numbers
		Vec2 pos4 = new Vec2(0.00000000000001f, 0.000000000000008f);
		Vec2 pos5 = new Vec2(0.0000000000000014f, 0.00000000000004f);
		pos4.add(pos5);
		assertEquals(0.0000000000000114f, pos4.getX(), 0);
		assertEquals(0.000000000000048f, pos4.getY(), 0);
		
		//dot product
		float dot = Vec2.dotProduct(pos2, pos3);
		assertEquals(32.0f, dot, 0);
		dot = Vec2.dotProduct(pos, pos2);
		assertEquals(Float.NEGATIVE_INFINITY, dot, 0);
		
		//normalizing
		Vec2 newVec = Vec2.normalize(pos3);
		assertEquals(-0.70710677f, newVec.getX(), 0);
		assertEquals(0.70710677f, newVec.getY(), 0);
		
		newVec = Vec2.normalize(pos2);
		assertEquals(-0.31622776f, newVec.getX(), 0);
		assertEquals(0.94868326f, newVec.getY(), 0);
		
	}
	
	@Test
	public void Vec3Test() {
		fail("Not yet implemented");
	}

}
