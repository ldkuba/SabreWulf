package engine.AI;

import java.util.ArrayList;

import engine.maths.Vec2;

public class Navmesh {

	private ArrayList<Edge> edges;
	private ArrayList<Triangle> triangles;
	private Pathfinding pathfinding;
	private ParseTriangles parsing;
	private PathThread pathThread;

	public Navmesh(String filename) {
		parsing = new ParseTriangles();
		parsing.readFile(filename);
		this.triangles = parsing.getTriangles();
		this.pathfinding = new Pathfinding(triangles);
		edges = new ArrayList<>();
		generateEdges();
	}

	public void generateEdges() {
		Triangle A = null;
		Triangle B = null;
		Edge edgeA;
		boolean[] boollist = new boolean[9];
		Vec2 midA = null;
		Vec2 midB = null;
		Vec2 weightVec = null;
		float weight = 0;
		int count;

		for (int i = 0; i < triangles.size(); i++) {
			A = triangles.get(i);
			for (int j = 0; j < triangles.size(); j++) {
				B = triangles.get(j);
				boollist[0] = (equals(A.getX(), B.getX()));
				boollist[1] = (equals(A.getX(), B.getY()));
				boollist[2] = (equals(A.getX(), B.getZ()));
				boollist[3] = (equals(A.getY(), B.getX()));
				boollist[4] = (equals(A.getY(), B.getY()));
				boollist[5] = (equals(A.getY(), B.getZ()));
				boollist[6] = (equals(A.getZ(), B.getX()));
				boollist[7] = (equals(A.getZ(), B.getY()));
				boollist[8] = (equals(A.getZ(), B.getZ()));
				count = 0;
				for (int k = 0; k < boollist.length; k++) {
					if (boollist[k]) {
						count++;
					}
				}
				if (count == 2) {
					midA = A.getMidpoint();
					midB = B.getMidpoint();
					weightVec = new Vec2(midB.getX() - midA.getX(), midB.getY() - midA.getY());
					weight = weightVec.getLength();
					edgeA = new Edge(A, B, midA, midB, weight);
					if (A != B) {
						if (!edges.contains(edgeA)) {
							edges.add(edgeA);
						}
						A.addEdge(edgeA);
					}
				}
			}
		}
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public ArrayList<Triangle> getTriangles() {
		return triangles;
	}

	public Pathfinding getPathfinding() {
		return pathfinding;
	}

	public boolean equals(Vec2 A, Vec2 B) {
		return (A.getX() == B.getX()) && (A.getY() == B.getY());
	}
}
