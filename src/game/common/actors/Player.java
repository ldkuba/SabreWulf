package game.common.actors;

/**
 * Player as the engine sees it.
 */

public class Player extends Actor {

	public Player(){
		//TODO
	}

	int role;

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}