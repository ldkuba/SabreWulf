package game.logic;

import engine.maths.Vec2;
import game.logic.Attack;
import game.logic.Movement;
import game.player.Player;

public class GameLogic {
	
	private Attack attackAction = new Attack();
	private Movement movement = new Movement();
	
	public static Player myPlayer;

	public GameLogic() {
		
	}
	
	public int doAttack(Vec2 playCoord, Vec2 enemyCoord) {
		return attackAction.attack(playCoord, enemyCoord, myPlayer.getCharClass().getAttackRange());
	}
	
	public float doMovement() {
		return movement.move();
	}
	
	public int receiveDamage(Player opponent, Player myPlayer) {
		return recvDamage.lostVitality(opponent, myPlayer);
	}
	
}
