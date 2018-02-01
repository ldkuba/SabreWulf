package engine.client.core;

public class Client
{
	public Client()
	{
		ClientConnection connectClient = new ClientConnection();
		connectClient.start();
	}
}
