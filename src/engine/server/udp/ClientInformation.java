package engine.server.udp;

/*Used internally for the UDP Manager */

import java.net.InetAddress;

import engine.common_net.AbstractMessage;

public class ClientInformation {

	private AbstractMessage msg;
	private InetAddress ip;
	
	public ClientInformation(AbstractMessage message, InetAddress clientIP) {
		msg = message;
		ip = clientIP;
	}
	
	public AbstractMessage getMsg() {
		return msg;
	}
	
	public InetAddress getIP() {
		return ip;
	}
	
}
