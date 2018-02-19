package engine.AI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import engine.maths.Vec2;

public class ParseTriangles {
	
	private ArrayList<Triangle> verticies;
	
	public void readFile(){
		FileReader input = null;
		String[] array1 = null;
		try {
			//TODO: Figure out best way to generalise file path.
			input = new FileReader("demomapmash.raw");
		} catch (FileNotFoundException e) {
			System.out.println("Error file not found.");
		}
		BufferedReader bufRead = new BufferedReader(input);
		String myLine;
		try {
			myLine = bufRead.readLine();
		} catch (IOException e) {
			System.out.println("Error no verticies found.");
		}
		
		try {
			while ( (myLine = bufRead.readLine()) != null)
			{    
				array1 = myLine.split(" ");
				convertArray(array1);
			}
		} catch (IOException e) {
			System.out.println("No more verticies found.");
		}
	}
	
	public void convertArray(String[] strings){
		Triangle tri;
		Vec2 x = null;
		Vec2 y = null;
		Vec2 z = null;
		float[] floats = new float[6];
		for(int i = 0; i < floats.length; i++){
			if(i != 2 || i != 5 || i != 8){
				//add verticies to array except z coodinates because we have no height.
				floats[i] = Float.parseFloat(strings[i]);							
			}
		}
		x.setX(floats[0]);
		x.setY(floats[1]);
		y.setX(floats[2]);
		y.setY(floats[3]);
		z.setX(floats[4]);
		z.setY(floats[5]);
		tri = new Triangle(x, y, z);
		verticies.add(tri);
	}
}