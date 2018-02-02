package game.networking;

import engine.common_net.AbstractMessage;

/*
 * Example to show how message classes might look
 */
public class ExampleMessage extends AbstractMessage
{
	private int a;
	
	/*
	 * Must provide an empty constructor
	 */
	public ExampleMessage()
	{
	}

	public int getA()
	{
		return a;
	}

	public void setA(int a)
	{
		this.a = a;
	}
}
