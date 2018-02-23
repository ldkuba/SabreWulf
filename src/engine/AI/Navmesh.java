package engine.AI;

import java.util.ArrayList;

import engine.maths.Vec2;

public class Navmesh {
	
	private ArrayList<Edge> edges;
	private ArrayList<Triangle> triangles;
	
	public Navmesh(ArrayList<Triangle> triangles){
		this.triangles = triangles;
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
}
