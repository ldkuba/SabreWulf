package engine.AI;

import engine.maths.Vec2;

public class Edge {
	
	private Triangle source;
	private Triangle goal;
	private Vec2 x;
	private Vec2 y;
	private float weight;
	
	public Edge(Triangle source, Triangle goal, Vec2 x, Vec2 y, float weight){
		this.x = x;
		this.y = y;
		this.weight = weight;
	}
	
	public Triangle getSource(){
		return source;
	}
	
	public Triangle getGoal(){
		return goal;
	}
	
	public Vec2 getXNode(){
		return x;
	}
	
	public Vec2 getYNode(){
		return y;
	}
	
	public float getWeight(){
		return weight;
	}
}
