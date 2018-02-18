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
				
				boolean xx = A.getX() == B.getX();
				boolean xy = A.getX() == B.getY();
				boolean xz = A.getX() == B.getZ();
				boolean yx = A.getY() == B.getX();
				boolean yy = A.getY() == B.getY();
				boolean yz = A.getY() == B.getZ();
				boolean zx = A.getZ() == B.getX();
				boolean zy = A.getZ() == B.getY();
				boolean zz = A.getZ() == B.getZ();
				
				if((xx && xy)||(xx && xz)||(xx && yx)||(xx && yy)||(xx && yz)||(xx && zx)||(xx && zy)||(xx && zz)||
				   (xy && xz)||(xy && yx)||(xy && yy)||(xy && yz)||(xy && zx)||(xy && zy)||(xy && zz)||
				   (xz && yx)||(xz && yy)||(xz && yz)||(xz && zx)||(xz && zy)||(xz && zz)||
			       (yx && yy)||(yx && yz)||(yx && zx)||(yx && zy)||(yx && zz)||
				   (yy && yz)||(yy && zx)||(yy && zy)||(yy && zz)||
				   (yz && zx)||(yz && zy)||(yz && zz)||
				   (zx && zy)||(zx && zz)){
					
					edge = new Edge(A.getMidpoint(), B.getMidpoint());
					edges.add(edge);
				}
			}
		}
	}
}
