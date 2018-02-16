package engine.AI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ParseTriangles {
	
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
			}
		} catch (IOException e) {
			System.out.println("Error no more verticies found.");
		}
	}
	
	public float[] convertArray(String[] strings){
		float[] floats = new float[9];
		for(int i = 0; i < floats.length; i++){
			floats[i] = Float.parseFloat(strings[i]);			
		}
		return floats;
	}
}
