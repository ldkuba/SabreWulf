package game.common.logic;

import engine.maths.Vec2;
import game.common.logic.actions.Attack;
import game.common.logic.actions.Damage;
import game.common.logic.actions.Movement;
import game.common.player.Player;

public class GameLogic {
	
	private Attack attackAction = new Attack();
	private Damage recvDamage = new Damage();
	private Movement movement = new Movement();
	
	protected static Player myPlayer;

	public GameLogic(Player myPlayer) {
		this.myPlayer = myPlayer;
	}
	
	public int doAttack(Vec2 playCoord, Vec2 enemyCoord) {
		return attackAction.attack(playCoord, enemyCoord, myPlayer.getCharClass().getAttackRange());
	}
	
	public float doMovement() {
		return movement.move();
	}
	
	public int receiveDamage(int recvDamage) {
		//reduce vitality.
		
	}
	
	public Player getPlayerInfo() {
		return myPlayer;
	}
}
