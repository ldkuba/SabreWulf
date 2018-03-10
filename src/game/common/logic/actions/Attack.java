package game.common.logic.actions;

import engine.entity.Entity;
import engine.entity.component.AbstractComponent;
import engine.entity.component.NetDataComponent;
import engine.maths.Vec2;
import engine.net.server.core.NetPlayer;
import game.common.actors.Player;

public class Attack {


	// Player control: If inRange attack <damage>, else get closer.
	public boolean attack(Vec2 playCoord, Player myActor, Entity myEntity,Vec2 enemyCoord, Player enemy) {
		if(inRange(playCoord, enemyCoord, myActor.getAttackRange())) {
			

			NetDataComponent dmgComp = (NetDataComponent) myEntity.getComponent(NetDataComponent.class);

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

	private boolean inRange(Vec2 playerCoord, Vec2 enemyCoord, float playerRange) {
		
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
