package engine.AI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Pathfinding {
	private ArrayList<Triangle> triangles;
	private ArrayList<Triangle> path;
	
	private boolean shouldQuit = false;

	public Pathfinding(ArrayList<Triangle> triangles) {
		this.triangles = triangles;
		path = new ArrayList<Triangle>();
	}

	public Triangle lookupTriangle(Triangle triangle) {
		for (Triangle t : triangles) {
			if (triangle.equals(t)) {
				return t;
			}
		}

		return null;
	}

	private void resetTriangles() {
		for (Triangle t : triangles) {
			t.setG(Float.MAX_VALUE);
			t.setF(Float.MAX_VALUE);
			t.setLast(t);
			
			if(shouldQuit)
			{
				return;
			}
		}
	}

	public void MAKEITSTOP()
	{
		shouldQuit = true;
	}
	
	public ArrayList<Triangle> AStar(Triangle start, Triangle goal) {

		resetTriangles();
		
		path.clear();
		//System.out.println("Finding new path...");
		Triangle current = start;

		Comparator<Triangle> hcomp = new DistanceComparator();
		ArrayList<Triangle> visitedList = new ArrayList<>();
		PriorityQueue<Triangle> toSearch = new PriorityQueue<Triangle>(triangles.size(), hcomp);
		toSearch.add(start);

		start.setG(0.0f);
		start.setH(goal);
		start.setF(start.getHeuristic());

		while (!toSearch.isEmpty()) {

			if(shouldQuit)
			{
				return null;
			}
			
			current = toSearch.poll();

			if ((current.getMidpoint().getX() == goal.getMidpoint().getX())
					&& (current.getMidpoint().getY() == goal.getMidpoint().getY())) {
				reconstructList(current);
				break;
			}

			visitedList.add(current);

			for (int i = 0; i < current.getEdges().size(); i++) {
				Edge edge = current.getEdges().get(i);
				Triangle temp = lookupTriangle(edge.getGoal());

				if (visitedList.contains(temp)) {
					continue;
				}

				if (!toSearch.contains(temp)) {
					toSearch.add(temp);
				}

				float tempG = current.getG() + edge.getWeight();

				if (tempG >= temp.getG()) {
					continue;
				}

				temp.setLast(current);
				temp.setG(tempG);
				temp.setH(goal);
				temp.setF(temp.getG() + temp.getHeuristic());
			}

			//System.out.println("Checking Next.");
		}
		return path;
	}

	private void reconstructList(Triangle goal) {
		//System.out.println("Reconstructing path.");
		Triangle next = goal.getLast();
		ArrayList<Triangle> tempPath = new ArrayList<Triangle>();
		tempPath.add(goal);
		while (next != null) {
			if (tempPath.contains(next)) {
				break;
			}
			tempPath.add(next);
			next = next.getLast();
			//System.out.println("Triangle F value: " + next.getF());
			//System.out.println("Next Triangle");
		}
		//System.out.println("Reversing Path.");
		for (int i = 0; i < tempPath.size(); i++) {
			path.add(tempPath.get(tempPath.size() - (i + 1)));
		}
	}
}
