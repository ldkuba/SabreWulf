package engine.AI;

import java.util.ArrayList;

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
		
		for(int i = 0; i < triangles.size(); i++){
			
			A = triangles.get(i);
			
			for(int j = 0; j < triangles.size(); j++){
				
				B = triangles.get(j);
				
				boolean[] boollist = new boolean[9]; 
				
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
					edge = new Edge(A.getMidpoint(), B.getMidpoint());
					edges.add(edge);
				}
			}
		}
	}
}
