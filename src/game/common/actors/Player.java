package game.common.actors;

/**
 * Player as the engine sees it.
 */

public class Player extends Actor
{
	private String name;
	private int role;
	private boolean isLocal;

	public Player(boolean isLocal, String name)
	{
		this.isLocal = isLocal;
		this.name = name;
	}

	public int getRole()
	{
		return role;
	}

	public void setRole(int role)
	{
		this.role = role;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean isLocal()
	{
		return this.isLocal;
	}

}