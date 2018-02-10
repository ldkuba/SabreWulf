package engine.common_net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class Serialization {
	/*
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
	*/
	public byte[] serialize(AbstractMessage msg) {
		
		AbstractMessage message = msg;		
		try
        {   
            //Saving of object in a file
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(message);
            byte[] data = outputStream.toByteArray();
            
            System.out.println("Object has been serialized");
            
            return data;
        }
        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }
		return null;
	}
	
}
