package engine.net.common_net;

import java.io.*;

public class UDPTools {

	public static Serializable deserialize(byte[] data) {
		ByteArrayInputStream in = new ByteArrayInputStream(data);
		try {
			ObjectInputStream is = new ObjectInputStream(in);
			Serializable message = (Serializable) is.readObject();
			return message;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] serialize(Serializable msg) {
		Serializable message = msg;
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
