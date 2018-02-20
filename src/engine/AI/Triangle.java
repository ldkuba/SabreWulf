package engine.AI;

import java.util.ArrayList;

import engine.maths.Vec2;

public class Triangle {
	
	private Vec2 x;
	private Vec2 y;
	private Vec2 z;
	private ArrayList<Edge> edges;
	private float f;
	private float g;
	private float h;
	
	public Triangle(Vec2 x, Vec2 y, Vec2 z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void addEdge(Edge edge){
		if(!edges.contains(edge)){
			edges.add(edge);			
		}
	}
	
	public Vec2 getMidpoint(){
		Vec2 mid = new Vec2();
		mid.setX((x.getX() + y.getX() + z.getX())/3);
		mid.setY((x.getY() + y.getY() + z.getY())/3);
		return mid;
	}
	
	public Vec2 getX(){
		return x;
	}
	
	public Vec2 getY(){
		return y;
	}
	
	public Vec2 getZ(){
		return z;
	}

	public float getF() {
		return f;
	}

	public float getG() {
		return g;
	}

	public float getH() {
		return h;
	}
}
