package engine.AI;

import java.util.ArrayList;

import engine.maths.Vec2;
import engine.maths.Vec3;

public class Navmesh {
	
	private ArrayList<Edge> edges;
	private ArrayList<Triangle> triangles;
	private Pathfinding pathfinding;
	private ParseTriangles parsing;
	
	public Navmesh(String filename){
		parsing = new ParseTriangles();
		parsing.readFile(filename);
		this.triangles = parsing.getTriangles();
		this.pathfinding = new Pathfinding(triangles);
		edges = new ArrayList<>();
		generateEdges();
	}
	
	public void generateEdges(){
		
		Triangle A = null;
		Triangle B = null;
		Edge edge;
		boolean[] boollist = new boolean[9]; 
		Vec2 midA = null;
		Vec2 midB = null;
		Vec2 weightVec = null;
		float weight = 0;
		
		for(int i = 0; i < triangles.size(); i++){
			
			A = triangles.get(i);
			
			for(int j = 0; j < triangles.size(); j++){
				
				B = triangles.get(j);
				
				boollist[0] = A.getX() == B.getX();
				boollist[1] = A.getX() == B.getY();
				boollist[2] = A.getX() == B.getZ();
				boollist[3] = A.getY() == B.getX();
				boollist[4] = A.getY() == B.getY();
				boollist[5] = A.getY() == B.getZ();
				boollist[6] = A.getZ() == B.getX();
				boollist[7] = A.getZ() == B.getY();
				boollist[8] = A.getZ() == B.getZ();
				
				int count = 0;
				
				for(int k = 0; k < boollist.length; k++){
					if(boollist[k]){
						count++;
					}
				}
				
				if(count >= 2){
					midA = A.getMidpoint();
					midB = B.getMidpoint();
					weightVec = new Vec2(midB.getX() - midA.getX(), midB.getY() - midA.getY());
					weight = weightVec.getLength();
					edge = new Edge(A, B, midA, midB, weight);
					edges.add(edge);
					A.addEdge(edge);
					B.addEdge(edge);
				}
			}
		}
	}
	
	public ArrayList<Edge> getEdges(){
		return edges;
	}
	
	public ArrayList<Triangle> getTriangles(){
		return triangles;
	}

	public Pathfinding getPathfinding() {
		return pathfinding;
	}
	
	private boolean isSameSide(Vec3 point, Vec3 ref, Vec3 a, Vec3 b){
		Vec3 nega = new Vec3(a);
		nega.scale(-1);
		Vec3 p1 = Vec3.crossProduct(Vec3.add(nega, b), Vec3.add(nega, point));
		Vec3 p2 = Vec3.crossProduct(Vec3.add(nega, b), Vec3.add(nega, ref));
		
		if(Vec3.dotProduct(p1, p2) >= 0){
			return true;
		} else {
			return false;
		}
	}
	
	private boolean pointInTriangle(Vec3 point, Triangle t){
		Vec3 pointA = new Vec3(t.getX().getX(), t.getX().getY(), 0.0f);
		Vec3 pointB = new Vec3(t.getY().getX(), t.getY().getY(), 0.0f);
		Vec3 pointC = new Vec3(t.getZ().getX(), t.getZ().getY(), 0.0f);
		
		if(isSameSide(point, pointA, pointB, pointC) && isSameSide(point, pointB, pointA, pointC) && isSameSide(point, pointC, pointA, pointB)){
			return true;
		}else
		{
			return false;
		}
	}
	
	public ArrayList<Vec3> findPath(Vec3 start, Vec3 end)
	{
		ArrayList<Vec3> path = new ArrayList<Vec3>();
		
		Triangle startTrig = null;
		Triangle endTrig = null;
		
		for(Triangle t : triangles)
		{
			if(pointInTriangle(start, t))
			{
				startTrig = t;
			}
			
			if(pointInTriangle(end, t))
			{
				endTrig = t;
			}
		}
		
		if(startTrig == null || endTrig == null)
		{
			return null;
		}
		
		ArrayList<Triangle> pathTrigs = pathfinding.AStar(startTrig, endTrig);
		
		System.out.println(pathTrigs.size());
		
		for(int i = 0; i < pathTrigs.size() - 1; i++)
		{
			path.add(new Vec3(pathTrigs.get(i).getMidpoint().getX(), pathTrigs.get(i).getMidpoint().getY(), 0.0f));
		}
		
		path.add(end);
		
		return path;	
	}
}
