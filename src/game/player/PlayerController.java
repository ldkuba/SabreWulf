package game.player;

import engine.entity.component.TransformComponent;
import engine.maths.Vec3;

//For handling actual instruction sets to be passed to transformation and interaction components of the players
public class PlayerController {
	
	private TransformComponent transformer;
	
	public void update()
	{
		//input 
		
	}
	
	public void onKeyPress(int button, int action)
	{
		
	}
	
	public void onMousePress(int button, int action)
	{
		
	}
	
	public void movePlayer(Vec3 position){
		transformer.setPosition(position);
	}
	
	public void rotatePlayer(Vec3 angle){
		transformer.setRotationAngles(angle);
	}
	
	public void scalePlayer(Vec3 scale){
		transformer.setScale(scale);
	}
}
