package game.common.logic;

import engine.maths.Vec2;
import engine.maths.Vec3;
import engine.net.server.core.NetPlayer;
import engine.application.Application;
import engine.entity.Entity;
import game.common.logic.actions.Attack;
import game.common.logic.actions.Movement;
import game.common.logic.actions.Respawn;
import game.common.actors.Actor;
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
	
	public void attack(Vec3 playCoord, Vec3 enemyCoord, Player enemy) {
		boolean willAttack = attackAc.attack(playCoord, myActor, myEntity, enemyCoord, enemy);
		
		if(!willAttack) {
			movement.moveTo(playCoord, enemyCoord);
		}
	}

	public boolean inRange(Vec3 playerCoord, Vec3 enemyCoord, float range) {
		return attackAc.inRange(playerCoord, enemyCoord, range);
	}
	
	public void respawn(Actor myActor) {
		if (!respawn.alive(myActor)) {
			respawn.respawn(myActor);
		}
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

	public float recvDamage(float dmg) {
		return 0.0f;
	}

	public Player getPlayerInfo() {
		return myActor;
	}
}
