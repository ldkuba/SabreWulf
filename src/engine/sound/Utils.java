package engine.sound;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Utils {
    public static FloatBuffer asFlippedFloatBuffer(float... values) {
        FloatBuffer output = BufferUtils.createFloatBuffer(values.length);
        output.put(values);
        output.flip();

        return output;
    }

	public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) {
		// TODO Auto-generated method stub
		return null;
	}
}