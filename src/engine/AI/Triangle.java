package engine.AI;

import java.util.ArrayList;

import engine.maths.Vec2;

public class Triangle {
	
	private Vec2 x;
	private Vec2 y;
	private Vec2 z;
	private ArrayList<Edge> edges;
	private float g;
	private float h;
	private float f;
	private Triangle last;
	private Vec2 midpoint;
	
	public Triangle(Vec2 x, Vec2 y, Vec2 z){
		this.x = x;
		this.y = y;
		this.z = z;
		edges = new ArrayList<>();
		last = this;
		g = Float.MAX_VALUE;
		h = 0;
		f = g + h;
		midpoint = this.getMidpoint();
	}
	
	public void addEdge(Edge edge){
		if(!edges.contains(edge)){
			edges.add(edge);			
		}
	}
	
	public ArrayList<Edge> getEdges(){
		return edges;
	}
	
	public Edge findEdge(Triangle goal){
		Edge edge = null;
		for(int i = 0; i < edges.size(); i++){
			if(edges.get(i).getGoal() == goal){
				edge = edges.get(i);
				break;
			}
		}
		return edge;
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

	public void setF(float f) {
		this.f = f;
	}

	public float getG() {
		return g;
	}

	public void setG(float g) {
		this.g = g;
	}

	public float getHeuristic(){
		return h;
	}

	public void setH(Triangle B) {
		Vec2 v = new Vec2(B.getMidpoint().getX() - midpoint.getX(), B.getMidpoint().getY() - midpoint.getY());
		h = v.getLength();
	}

	public void setLast(Triangle last) {
		this.last = last;
	}
	
	public Triangle getLast(){
		return last;
	}
	
	@Override
	public String toString(){
		return "Number of Edges: " + this.getEdges().size() + ". " + "Midpoint: " + this.getMidpoint().getX() + " " + this.getMidpoint().getY();
	
	}
}
