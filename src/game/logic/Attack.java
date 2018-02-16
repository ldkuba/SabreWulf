package game.logic;

import engine.logic.AbstractAction;
import engine.maths.Vec2;
import game.player.Player;

public class Attack extends AbstractAction{
	
	private Vec2 coordPlayer;
	private Vec2 coordEnemy;
	
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
		
		float rangeX = enemyCoord.getX() - playerCoord.getX();
		float rangeY = enemyCoord.getY() - playerCoord.getY();
		
		//Always positive.
		if (rangeX < 0) {
			rangeX = rangeX * -1;
		}
		if(rangeY < 0) {
			rangeY = rangeY * -1;
		}
		
		if( rangeX <= myPlayer.getCharClass().getAttackRange() || rangeY <= myPlayer.getCharClass().getAttackRange()) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public int doDamage() {
		int damage = myPlayer.getCharClass().getAttackDmg();	//Base damage.
		return damage;
	}
	
	private Vec2 moveTo() {
		float toX = coordPlayer.getX() - coordEnemy.getX();
		float toY = coordPlayer.getY() - coordEnemy.getY();
		Vec2 toPos = new Vec2(toX,toY);
		
		return toPos;
	}
	
}
