package game.common.logic.actions;

import engine.entity.Entity;
import engine.entity.component.AbstractComponent;
import engine.logic.AbstractAction;
import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.net.server.core.NetPlayer;
import game.common.actors.Player;

public class Attack extends AbstractAction {


	// Player control: If inRange attack <damage>, else get closer.
	public boolean attack(Vec3 playCoord, Player myActor, Entity myEntity, Vec3 enemyCoord, Player enemy) {
		if(inRange(playCoord, enemyCoord, myActor.getAttackRange())) {
			
			//Class NetDataComponent;
			//AbstractComponent dmg = myEntity.getComponent(NetDataComponent);
			/**
			 * I Have no idea how to change the damage of the other player.
			 * Will ask how to do this...
			 */
			//enemy.getLogic().receiveDamage(dmg);	//Replaced by sending it to the server.
			//Display damage.
			
			return true;
		} else {
			return false;
		}
	}

	public boolean inRange(Vec3 playerCoord, Vec3 enemyCoord, float playerRange) {
		
		float rangeX = enemyCoord.getX() - playerCoord.getX();
		float rangeY = enemyCoord.getY() - playerCoord.getY();
		
		//Make values positive.
		rangeX = toPositive(rangeX);
		rangeY = toPositive(rangeY);
		
		if( rangeX <= playerRange && rangeY <= playerRange) {
			return true;
		} else {
			return false;
		}
		
	}
	
	//Change coordinates to positive to properly calculate the radius of the player.
	private float toPositive(float coordinate) {
		return (coordinate * -1);
		
	}
}
