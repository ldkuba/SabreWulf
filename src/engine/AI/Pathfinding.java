package engine.AI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;


public class Pathfinding {
	private float g;
	private ArrayList<Triangle> triangles;
	private ArrayList<Triangle> path;
	
	public Pathfinding(ArrayList<Triangle> triangles){
		this.triangles = triangles;
		path = new ArrayList<>();
	}
	
	public ArrayList<Triangle> AStar(Triangle start, Triangle goal){
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
			current.setLast(last);
			last.setG(last.findEdge(current).getWeight());
			g += last.findEdge(current).getWeight();
		}
		return path;
	}
	
	private void reconstructList(Triangle goal) {
		Triangle next = goal.getLast();
		path.add(goal);
		while (next != null){
			path.add(next);
			next = next.getLast();
		}
	}
}
