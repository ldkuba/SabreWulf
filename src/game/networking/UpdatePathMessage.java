package game.networking;

import engine.AI.Path;
import engine.common_net.AbstractMessage;

public class UpdatePathMessage extends AbstractMessage
{
	private Path path;
	
	public UpdatePathMessage(){}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}
}
