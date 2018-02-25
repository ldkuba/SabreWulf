package game.common.logic;

import engine.maths.Vec2;
import engine.application.Application;
import game.common.logic.actions.Movement;
import game.common.logic.actions.Respawn;
import game.common.actors.Player;

public class ActorLogic extends AbstractLogic{
	
	private Movement movement = new Movement();
	private Respawn respawn = new Respawn();
	
	protected static Player myActor;

	public ActorLogic(Player myPlayer) {
		this.myActor = myPlayer;
	}
	
	public boolean attack(Vec2 playCoord, Vec2 enemyCoord, Player enemy) {
		if(inRange(playCoord, enemyCoord, myActor.getAttackRange())) {
			float dmg = myActor.getDamage();
			enemy.getLogic().receiveDamage(dmg);	//Replaced by sending it to the server.
			//Display damage.
			return true;
		} else {
			return false;
		}
	}
	
	public float movement() {
		return movement.move();
	}
	
	public void receiveDamage(float recvDamage) {
		//reduce vitality.
		float newHealth = myActor.getHealth() - recvDamage;
		//Display damage.s
		myActor.setHealth(newHealth);
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
	
	private float toPositive(float coordinate) {
		return (coordinate * -1);
		
	}
	
	
	
	public boolean alive() {
		if (zeroVitality()) {
			//Timer or screen.
			respawn();
			return false;
		} else {
			return true;
		}
	}
	
	private void respawn() {
			Vec2 spawnCoord = new Vec2(0, 0);
			myActor.setPosition(spawnCoord);
	}
	
	private boolean zeroVitality() {
		if (myActor.getHealth() <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	public Player getPlayerInfo() {
		return myActor;
	}
}
