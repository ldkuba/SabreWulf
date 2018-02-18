package engine.AI;

import engine.maths.Vec2;

public class Edge {
	
	private Vec2 x;
	private Vec2 y;
	
	public Edge(Vec2 x, Vec2 y){
		this.x = x;
		this.y = y;
	}
	
	public Vec2 getXNode(){
		return x;
	}
	
	public Vec2 getYNode(){
		return y;
	}
}
