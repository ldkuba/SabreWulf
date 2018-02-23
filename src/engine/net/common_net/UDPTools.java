package engine.net.common_net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import engine.entity.NetworkEntity;

public class UDPTools {

	public static NetworkEntity deserialize(byte[] data) {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		try {
			ObjectInputStream is = new ObjectInputStream(in);
			NetworkEntity message = (NetworkEntity) is.readObject();
			return message;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] serialize(NetworkEntity msg) {
		NetworkEntity message = msg;
		try
		{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(outputStream);
			out.writeObject(message);
			byte[] data = outputStream.toByteArray();
			return data;
		}
		catch(IOException ex)
		{
			System.out.println("IOException is caught");
		}
		return null;
	}
	
}
