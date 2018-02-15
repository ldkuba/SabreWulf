package game.logic;

import engine.logic.AbstractAction;
import engine.maths.Vec2;
import game.player.Player;

public class Attack extends AbstractAction{
	
	private Vec2 coordPlayer;
	private Vec2 coorEnemy;
	
	private Vec2 noMovement = new Vec2(0,0);
	
	private Player myPlayer;
	
	public Attack (Player player) {
		myPlayer = player;
	}
	
	public Vec2 attack(Vec2 playCoord, Vec2 enemyCoord) {
		
		if(inRange(playCoord, enemyCoord)) {
			doDamage();
			return noMovement;
		} else {
			return moveTo();
		}
		
	}

	public boolean inRange(Vec2 playerCoord, Vec2 enemyCoord) {
		
		/*
		 * Do some calculations
		 */
		if(calculateDistance(playerCoord,enemyCoord) <= myPlayer.getCharClass().getAttackRange()) {
			return true;
		} else {
			return false;
		}
		
	}
	
	private float calculateDistance(Vec2 playerCoord, Vec2 enemyCoord) {
		return 0.0f;
	}
	
	private void doDamage() {
		
	}
	
	private Vec2 moveTo() {
		
	}
	
}
