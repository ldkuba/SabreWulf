package game.common.logic;

import engine.maths.Vec2;
import engine.net.server.core.NetPlayer;
import game.common.logic.actions.Attack;
import game.common.logic.actions.Damage;
import game.common.logic.actions.Movement;


public class GameLogic {
	
	private Attack attackAction = new Attack();
	private Damage recvDamage = new Damage();
	private Movement movement = new Movement();
	
	protected static NetPlayer myPlayer;

	public GameLogic(NetPlayer myPlayer) {
		this.myPlayer = myPlayer;
	}
	
	public int doAttack(Vec2 playCoord, Vec2 enemyCoord) {
		return attackAction.attack(playCoord, enemyCoord, myPlayer.;
	}
	
	public float doMovement() {
		return movement.move();
	}
	
	public int receiveDamage(int recvDamage) {
		//reduce vitality.
	}
	
	public NetPlayer getPlayerInfo() {
		return myPlayer;
	}
}
