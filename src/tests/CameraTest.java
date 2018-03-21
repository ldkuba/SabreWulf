package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import engine.application.Application;
import engine.graphics.camera.Camera;
import engine.maths.Mat4;
import engine.maths.Vec2;
import engine.maths.Vec3;
import game.client.Main;

public class CameraTest {

	private Camera camera = new Camera();

	@Test
	public void positionAndDirectionTest() {
		Vec3 pos = camera.getPosition(); // exp (0,0,0)
		Vec3 dir = camera.getDirection(); // exp(0,0,1)
		
		assertEquals(0.0f, pos.getX() + pos.getY() + pos.getZ(), 0.0f);
		camera.setPosition(new Vec3(5.0f, -2.0f, 7.0f));
		pos = camera.getPosition();
		assertEquals(10.0f, pos.getX() + pos.getY() + pos.getZ(), 0.0f);

		assertEquals(1.0f, dir.getX() + dir.getY() + dir.getZ(), 0.0f);
		camera.setDirection(new Vec3(-4.0f, 8.0f, -2.0f));
		dir = camera.getDirection(); // exp(0,0,1)
		assertEquals(2.0f, dir.getX() + dir.getY() + dir.getZ(), 0.0f);

		Vec3 change = new Vec3(0.4f, 8.0f, -35.0f);
		camera.move(change);
		pos = camera.getPosition();
		assertEquals(5.4f, pos.getX(), 0.0f);
		assertEquals(6.0f, pos.getY(), 0.0f);
		assertEquals(-28.0f, pos.getZ(), 0.0f);
	}
	
	@Test
	public void coordinateConversionTest(){
		Main main = new Main();
		Main.s_Viewport.setX(40.0f);
		Main.s_Viewport.setY(60.0f);
		Vec3 world = new Vec3(4.0f, 8.0f, 17.0f);
		Vec2 calcScreen = camera.getScreenCoordinates(world);
		Vec3 calcWorld = camera.getWorldCoordinates(calcScreen.getX(), calcScreen.getY());
		assertEquals(world.getX(), calcScreen.getX(), 0.0f);
		assertEquals(world.getY(), calcScreen.getY(), 0.0f);
		assertEquals(world.getZ(), calcWorld.getZ(), 0.0f);
	}
}
