package engine.AI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import engine.maths.Vec2;

public class Pathfinding {
	private float g;
	private Navmesh navmesh;
	private ArrayList<Edge> edges;
	private ArrayList<Triangle> triangles;
	
	public Pathfinding(Navmesh navmesh, ArrayList<Edge> edges, ArrayList<Triangle> triangles){
		this.navmesh = navmesh;
		this.edges = edges;
		this.triangles = triangles;
	}
	
	public void AStar(Navmesh navmesh, Triangle start, Triangle goal){
		Triangle current = start;
		Triangle last;
		Triangle temp;
		g = 0;
		ArrayList<Triangle> opened = new ArrayList<Triangle>();
		opened.add(start);
		Comparator<Triangle> hcomp = new DistanceComparator();
		PriorityQueue<Triangle> toSearch = new PriorityQueue<Triangle>(triangles.size(), hcomp);
		toSearch.add(start);
		while(!toSearch.isEmpty()){
			if(current == goal){
				reconstructList(current);
				break;
			}
			if(!opened.contains(current)){
				opened.add(current);
			}
			
			for(int i = 0; i < current.getEdges().size(); i++){
				temp = current.getEdges().get(i).getGoal();
				temp.setH(goal);
				temp.setF(g + temp.getHeuristic());
				if(!toSearch.contains(temp)){
					toSearch.add(temp);
				}
			}
			last = current;
			current = toSearch.poll();
			last.setG(last.findEdge(current).getWeight());
			g += last.findEdge(current).getWeight();
		}
	}
	
	private void reconstructList(Triangle goal) {
		
	}
}
