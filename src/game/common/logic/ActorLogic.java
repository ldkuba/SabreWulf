package game.common.logic;

import engine.maths.Vec2;
import engine.net.server.core.NetPlayer;
import engine.application.Application;
import engine.entity.Entity;
import game.common.logic.actions.Attack;
import game.common.logic.actions.Movement;
import game.common.logic.actions.Respawn;
import game.common.actors.Player;

public class ActorLogic {
	
	private Movement movement = new Movement();
	private Respawn respawn = new Respawn();
	private Attack attackAc = new Attack();
	
	protected static Player myActor;
	protected static NetPlayer myNetActor;
	
	protected static Entity myEntity;

	public ActorLogic(Player myPlayer) {
		ActorLogic.myActor = myPlayer;
		myEntity = myActor.getEntity();
	}
	
	public void attack(Vec2 playCoord, Vec2 enemyCoord, Player enemy) {
		boolean willAttack = attackAc.attack(playCoord, myActor, myEntity, enemyCoord, enemy);
		
		if(!willAttack) {
			movement.moveTo(playCoord, enemyCoord);
		}
	}
	
	public void respawn(Player myActor) {
		respawn.alive(myActor);
	}
	
	public float movement() {
		return movement.move();
	}
	
	//Unnecessary for now
	/*
	public void receiveDamage(float recvDamage) {
		//reduce vitality.
		float newHealth = myActor.getHealth() - recvDamage;
		//Display damage.s
		myActor.setHealth(newHealth);
		
		if(!alive()) {
			//Give.
			respawn();
		}
		
	}
	*/
	public Player getPlayerInfo() {
		return myActor;
	}
}
