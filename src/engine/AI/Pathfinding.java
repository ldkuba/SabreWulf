package engine.AI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;


public class Pathfinding {
	private ArrayList<Triangle> triangles;
	private ArrayList<Triangle> path;
	
	public Pathfinding(ArrayList<Triangle> triangles){
		this.triangles = triangles;
		path = new ArrayList<Triangle>();
	}
	
	public ArrayList<Triangle> AStar(Triangle start, Triangle goal){
		System.out.println("Finding new path...");
		Triangle current = start;
		Triangle last;
		Triangle temp = current;
		Comparator<Triangle> hcomp = new DistanceComparator();
		PriorityQueue<Triangle> toSearch = new PriorityQueue<Triangle>(triangles.size(), hcomp);
		toSearch.add(start);
		while(!toSearch.isEmpty()){
			if((current.getMidpoint().getX() == goal.getMidpoint().getX()) 
					&& (current.getMidpoint().getY() == goal.getMidpoint().getY())){
				reconstructList(current);
				break;
			}
			
			for(int i = 0; (i < current.getEdges().size()) && (i < temp.getLast().getEdges().size()); i++){
				
				temp = current.getEdges().get(i).getGoal();
				temp.setInt(i);
				temp.setH(goal);
				
				if((temp.getMidpoint().getX() == start.getMidpoint().getX()) 
						&& (temp.getMidpoint().getY() == start.getMidpoint().getY())){
					temp.setG(0);
					temp.setLast(temp); 
					temp.getLast().setInt(i);
				} else {
					if(temp.getLast().getEdges().size() > temp.getLast().getInt()){
						temp.setG(temp.getLast().getG()
								+ temp.getLast().getEdges().get(temp.getLast().getInt()).getWeight());

						temp.setF(temp.getG() + temp.getHeuristic());	
					}
				}
				if(!toSearch.contains(temp)){
					toSearch.add(temp);
				}
			}
			last = current;
			current = toSearch.poll();
			current.setLast(last);
			System.out.println("Checking Next.");
		}
		return path;
	}
	
	private void reconstructList(Triangle goal) {
		System.out.println("Reconstructing path.");
		Triangle next = goal.getLast();
		ArrayList<Triangle> tempPath = new ArrayList<Triangle>();
		tempPath.add(goal);
		while (next != null){
			if(tempPath.contains(next)){
				break;
			}
			tempPath.add(next);
			next = next.getLast();
			System.out.println("Next Triangle");
		}
		System.out.println("Reversing Path.");
		for(int i = 0; i < tempPath.size(); i++){
			path.add(tempPath.get(tempPath.size() - (i+1)));
		}
	}
}
