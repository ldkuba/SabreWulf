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
		Vec3 pos = new Vec3(5.0f, 84.0f, 20.005f);
		//test get and setters
		assertEquals(5.0f, pos.getX(), 0);
		assertEquals(84.0f, pos.getY(), 0);
		assertEquals(20.005f, pos.getZ(), 0);
		pos.setX(87.0f);
		assertEquals(87.0f,pos.getX(),0);
		pos.setY(-54.0f); //check works for negatives
		assertEquals(-54.0f,pos.getY(),0);
		
		//max/min float values
		pos.setX(Float.MAX_VALUE);
		assertEquals(Float.MAX_VALUE, pos.getX(), 0);
		pos.setY(-Float.MAX_VALUE);
		assertEquals(-Float.MAX_VALUE, pos.getY(), 0);
		pos.setZ(Float.NEGATIVE_INFINITY);
		assertEquals(Float.NEGATIVE_INFINITY, pos.getZ(), 0);
		
		Vec3 pos2 = new Vec3(2.0f, 2.0f, 8.0f);
		//length
		assertEquals(8.485280990600586,pos2.getLength(),0);
		
		//adding to a bound
		pos.add(pos2);
		assertEquals(Float.MAX_VALUE, pos.getX(), 0);
		assertEquals(-Float.MAX_VALUE, pos.getY(), 0);
		assertEquals(Float.NEGATIVE_INFINITY, pos.getZ(), 0);
		
		//adding normally
		Vec3 pos3 = new Vec3(-4.0f, 4.0f, Float.POSITIVE_INFINITY);
		pos2.add(pos3);
		assertEquals(-2.0f, pos2.getX(), 0);
		assertEquals(6.0f, pos2.getY(), 0);
		assertEquals(Float.POSITIVE_INFINITY, pos2.getZ(), 0);
		
		//adding very small numbers
		Vec3 pos4 = new Vec3(0.00000000000001f, 0.000000000000008f, 0.00000000005f);
		Vec3 pos5 = new Vec3(0.0000000000000014f, 0.00000000000004f, 0.000000003f);
		pos4.add(pos5);
		assertEquals(0.0000000000000114f, pos4.getX(), 0);
		assertEquals(0.000000000000048f, pos4.getY(), 0);
		
		pos2.setZ(5.0f);
		//dot product
		float dot = Vec3.dotProduct(pos2, pos4);
		assertEquals(0.00000001525026505078131, dot, 0);
		pos.setX(54.258f);
		pos.setY(-888.0f);
		pos.setZ(0.01f);
		dot = Vec3.dotProduct(pos, pos2);
		assertEquals(-5436.46630859375, dot, 0);
		
		Vec3 xdot = Vec3.crossProduct(pos4, pos2);
		assertEquals(-0.00000001829975992961863, xdot.getX(), 0);
		assertEquals(-0.000000006100056904045914, xdot.getY(), 0);
		assertEquals(0.00000000000016439998776381082, xdot.getZ(), 0);
		
		//normalizing
		Vec3 newVec = Vec3.normalize(pos3);
		assertEquals(-0.0f, newVec.getX(), 0);
		assertEquals(0.0f, newVec.getY(), 0);
		
		newVec = Vec3.normalize(pos2);
		assertEquals(-0.24806946516036987f, newVec.getX(), 0);
		assertEquals(0.7442083954811096, newVec.getY(), 0);
		assertEquals(0.6201737f, newVec.getZ(), 0);
	}

}
