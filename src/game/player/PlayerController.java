package game.player;

import engine.entity.component.TransformComponent;
import engine.maths.Vec3;

//For handling actual instruction sets to be passed to transformation and interaction components of the players
public class PlayerController {
	//movePlayer
	//playerInteract
	//action
	private TransformComponent transformer;
	
	
	public void update()
	{
		//input
	}
	
	public void movePlayer(Vec3 position){
		transformer.setPosition(position);
	}
	
	public void playerInteract(){
		//TODO interaction methods
	}
	
	public void action(){
		//TODO action methods
	}
}
