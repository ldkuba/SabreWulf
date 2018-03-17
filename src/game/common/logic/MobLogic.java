package game.common.logic;

import engine.entity.Entity;
import engine.maths.Vec3;
import game.common.actors.Mob;
import game.common.actors.Player;
import game.common.logic.actions.Attack;
import game.common.logic.actions.Movement;
import game.common.logic.actions.Respawn;

public class MobLogic {
	
	private Movement movement = new Movement();
	private Respawn respawn = new Respawn();
	private Attack attackAc = new Attack();

	protected static Mob myNpc;
	protected static Entity myEntity;
	
	public MobLogic(Mob myMob) {
		this.myNpc = myMob;
		myEntity = myNpc.getEntity();
	}
	
	public void attack(Vec3 mobCoord, Vec3 playerCoord, Player player) {
		boolean willAttack = attackAc.attack(mobCoord, myNpc, myEntity, playerCoord, player);
		
		if(!willAttack) {
			movement.moveTo(mobCoord, playerCoord);
		}
	}
	
	public boolean inRange(Vec3 mobCoord, Vec3 playerCoord, float range) {
		return attackAc.inRange(mobCoord, playerCoord, range);
	}
	
	public void respawn(Mob myNpc) {
		if (!respawn.isAlive(myNpc)) {
			// some death animation
			respawn.respawn(myNpc);
		}
	}
	
	public float movement() {
		return movement.move();
	}
	
	public Mob getMobInfo() {
		return myNpc;
	}
}
