package engine.AI;

import engine.maths.Vec2;

public class Path {
	private Vec2 start;
	private Vec2 goal;
	
	public void setStart(Vec2 pos){
		start = pos;
	}
	
	public void setGoal(Vec2 pos){
		goal = pos;
	}
	
	public Vec2 getStart(){
		return start;
	}
	
	public Vec2 getGoal(){
		return goal;
	}
}
