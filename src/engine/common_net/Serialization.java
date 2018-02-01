package engine.common_net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class Serialization {
	
	public byte[] serialize(Object message) {
		byte[] messageBytes = new byte[1024];
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(message);
			out.flush();
			messageBytes = bos.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
			bos.close();
			} catch (IOException ex) {
				
			}	
		}
		return messageBytes;
	}
	
}
