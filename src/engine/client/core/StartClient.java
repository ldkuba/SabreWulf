package engine.client.core;

public class StartClient {
	
	public static void main(String[] args) {
		ClientConnection connectClient = new ClientConnection();
		connectClient.start();
	}

}
