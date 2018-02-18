package game.common.logic;

import engine.maths.Vec2;
import game.common.logic.actions.Attack;
import game.common.logic.actions.Movement;
import game.common.player.Player;

public class GameLogic {
	
	private Attack attackAction = new Attack();
	private Movement movement = new Movement();
	//ReceiveDamage.
	
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
	
	/*
	 * ReceiveDamage method.
	 */
	
	public Player getPlayerInfo() {
		return myPlayer;
	}
}
