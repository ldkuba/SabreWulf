package engine.AI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import engine.maths.Vec2;

public class Pathfinding {
	
	private Navmesh navmesh;
	private ArrayList<Edge> edges;
	private ArrayList<Triangle> triangles;
	
	public Pathfinding(Navmesh navmesh, ArrayList<Edge> edges, ArrayList<Triangle> triangles){
		this.navmesh = navmesh;
		this.edges = edges;
		this.triangles = triangles;
	}
	
	public void AStar(Navmesh navmesh, Triangle start, Triangle goal){
		
		ArrayList<Triangle> unopened = triangles;
		ArrayList<Triangle> opened = new ArrayList<Triangle>();
		opened.add(start);
		Comparator<Triangle> hcomp = new DistanceComparator();
		PriorityQueue<Triangle> toSearch = new PriorityQueue<Triangle>(unopened.size(), hcomp);
	}
	
	public float getHeuristic(Triangle A, Triangle B){
		Vec2 v = new Vec2(B.getMidpoint().getX() - A.getMidpoint().getX(),
						  B.getMidpoint().getY() - A.getMidpoint().getY());
		return v.getLength();
	}
}
