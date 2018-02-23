package game.common.logic.actions;

import engine.maths.Vec2;
import game.common.player.Player;

public class Attack extends AbstractAction{
	
	private Vec2 coordPlayer;
	private Vec2 coordEnemy;
	
	private Vec2 noMovement = new Vec2(0,0);
	
	private Player myPlayer;
	
	public Attack () {
		
	}
	
	//Player control: If inRange attack <damage>, else get closer.
	public int attack(Vec2 playCoord, Vec2 enemyCoord, float playerRange) {
		
		if(inRange(playCoord, enemyCoord, playerRange)) {
			doDamage();
			return doDamage();
		} else {
			return 0;
		}
	}

	public boolean inRange(Vec2 playerCoord, Vec2 enemyCoord, float playerRange) {
		
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
	
	//Change coordinates to contain positive values
	private float toPositive(float coordinate) {
		return (coordinate * -1);
		
	}
}