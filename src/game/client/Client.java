package game.client;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import engine.client.udp.*;
import engine.client.core.ClientConnection;
import engine.common_net.AbstractMessage;
import engine.common_net.ConnectionListener;
import engine.common_net.MessageListener;
import engine.server.core.Player;
import org.lwjgl.system.CallbackI;

public class Client
{
	private ClientConnection connectClient;

	private ClientMessageListener messageListener;

	public BlockingQueue<AbstractMessage> abs = new BlockingQueue<AbstractMessage>() {
		@Override
		public boolean add(AbstractMessage abstractMessage) {
			return false;
		}

		@Override
		public boolean offer(AbstractMessage abstractMessage) {
			return false;
		}

		@Override
		public void put(AbstractMessage abstractMessage) throws InterruptedException {

		}

		@Override
		public boolean offer(AbstractMessage abstractMessage, long timeout, TimeUnit unit) throws InterruptedException {
			return false;
		}

		@Override
		public AbstractMessage take() throws InterruptedException {
			return null;
		}

		@Override
		public AbstractMessage poll(long timeout, TimeUnit unit) throws InterruptedException {
			return null;
		}

		@Override
		public int remainingCapacity() {
			return 0;
		}

		@Override
		public boolean remove(Object o) {
			return false;
		}

		@Override
		public boolean contains(Object o) {
			return false;
		}

		@Override
		public int drainTo(Collection<? super AbstractMessage> c) {
			return 0;
		}

		@Override
		public int drainTo(Collection<? super AbstractMessage> c, int maxElements) {
			return 0;
		}

		@Override
		public AbstractMessage remove() {
			return null;
		}

		@Override
		public AbstractMessage poll() {
			return null;
		}

		@Override
		public AbstractMessage element() {
			return null;
		}

		@Override
		public AbstractMessage peek() {
			return null;
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public Iterator<AbstractMessage> iterator() {
			return null;
		}

		@Override
		public Object[] toArray() {
			return new Object[0];
		}

		@Override
		public <T> T[] toArray(T[] a) {
			return null;
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			return false;
		}

		@Override
		public boolean addAll(Collection<? extends AbstractMessage> c) {
			return false;
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			return false;
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			return false;
		}

		@Override
		public void clear() {

		}
	};

	public Client()
	{
		messageListener = new ClientMessageListener();
		
		connectClient = new ClientConnection(this);
		connectClient.start();
	}

	public void notifyMessageListeners(AbstractMessage msg)
	{
		messageListener.receiveMessage(msg);
	}
	
	public void sendTCP(AbstractMessage msg)
	{
		abs.add(msg);
	}
	
	public void sendUDP(AbstractMessage msg)
	{
		// TODO connectClient.sendUDP(msg);
		connectClient.sendUDP(msg);
		
	}
}
