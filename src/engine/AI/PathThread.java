package engine.AI;

import java.util.ArrayList;

import engine.maths.Vec3;
import game.common.actors.Actor;

public class PathThread extends Thread
{
	private Navmesh navMesh;
	private Actor actor;
	private Pathfinding pathfinding;
	
	private ArrayList<Vec3> path;
	
	private Triangle startTrig;
	private Triangle endTrig;
	private Vec3 target;
	
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

		if(isSameSide(point, pointA, pointB, pointC) &&
				isSameSide(point, pointB, pointA, pointC) &&
				isSameSide(point, pointC, pointA, pointB)){
			return true;
		}else
		{
			return false;
		}
	}
	
	public ArrayList<Vec3> smoothPath(ArrayList<Vec3> path, ArrayList<Triangle> triangles){
		Vec3 A = new Vec3();
		Vec3 B = new Vec3(); 
		Vec3 C = new Vec3();
		boolean valid = false;
		ArrayList<Vec3> smoothedPath = new ArrayList<Vec3>();
		smoothedPath.add(path.get(0));
		path.remove(0);
		while(path.size() > 1){
			A = path.get(0);
			B = path.get(1);
			C = averagePoint(A, B);
			for(Triangle t : triangles){
				if(pointInTriangle(C, t)){
					valid = true;
					break;
				}
			}
			if(valid){
				smoothedPath.add(C);
				path.remove(0);
				path.remove(1);
			} else {
				smoothedPath.add(A);
				path.remove(0);
			}
			valid = false;
		}
		if(path.size() == 1){
			smoothedPath.add(path.get(0));
			path.remove(0);			
		} else {
			smoothedPath.add(target);
		}
		
		return smoothedPath;
	}

	private Vec3 averagePoint(Vec3 A, Vec3 B) {
		Vec3 C = new Vec3();
		C.setX((A.getX() + B.getX())/2);
		C.setY((A.getY() + B.getY())/2);
		C.setZ(0.0f);
		return C;
	}
	
	public void MAKEITSTOP()
	{
		this.pathfinding.MAKEITSTOP();
	}
	
	public PathThread(Actor actor, Navmesh navMesh, Vec3 start, Vec3 end)
	{
		this.setName("PATHTHTHTHTTHTH");
		this.navMesh = navMesh;
		this.actor = actor;
		this.target = end;
		
		path = new ArrayList<Vec3>();
		ArrayList<Triangle> triangles = (ArrayList<Triangle>) navMesh.getTriangles().clone();
		
		this.pathfinding = new Pathfinding(triangles);

		System.out.println("Triangle Array Size: " + triangles.size());

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
	}
	
	public void run()
	{
		if(startTrig == null || endTrig == null)
		{
			actor.callbackPath(new ArrayList());
			return;
		}
		
		if(startTrig.equals(endTrig)){
			path.add(target);
			actor.callbackPath(path);
			return;
		}	
		
		ArrayList<Triangle> pathTrigs = pathfinding.AStar(startTrig, endTrig);
		
		if(pathTrigs == null)
		{
			return;
		}
		
		System.out.println("Path Size:" + pathTrigs.size());

		for(int i = 0; i < pathTrigs.size() - 1; i++)
		{
			path.add(new Vec3(pathTrigs.get(i).getMidpoint().getX(), pathTrigs.get(i).getMidpoint().getY(), 0.0f));
		}
		path = smoothPath(path, navMesh.getTriangles());
		
		actor.callbackPath(path);
	}
}
