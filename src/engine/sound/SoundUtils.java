package engine.sound;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import org.lwjgl.BufferUtils;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;

import static org.lwjgl.BufferUtils.*;

/**
 * Contains helper methods that are used in other classes in the same package.
 * 
 * @author bhavi
 *
 */
public class SoundUtils {

	private static final String[] Sounds = { "countEnd", "count", "background/game", "background/lobby", "lockIn",
			"background/menu", "background/end", "quit", "click", "movement/forest", "movement/grass",
			"movement/footstep", "attack/a1", "attack/a2", "attack/spells/s1", "attack/spells/s2", "attack/spells/s3",
			"attack/spells/s4", "attack/m1", "attack/m2", "attack/scratch", "item/i1", "item/i2" };

	/**
	 * Checks if the sound file exists in the array of known sounds
	 * 
	 * @param soundName
	 * @return
	 */
	public static boolean doesSoundFileExist(String soundName) {
		for (int i = 0; i < Sounds.length; i++) {
			if (Sounds[i].equals(soundName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Converts an input output resource to a byte buffer
	 * 
	 * @param resource
	 * @param bufferSize
	 * @return
	 */
	public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) {
		ByteBuffer buffer = null;
		Path path = Paths.get(resource);
		if (Files.isReadable(path)) {
			try (SeekableByteChannel fc = Files.newByteChannel(path)) {
				buffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
				while (fc.read(buffer) != -1)
					;
			} catch (IOException e) {
				e.getMessage();
			}
		} else {
			try {
				InputStream source = SoundUtils.class.getResourceAsStream(resource);
				ReadableByteChannel rbc = Channels.newChannel(source);
				buffer = createByteBuffer(bufferSize);

				while (true) {
					int bytes = rbc.read(buffer);
					if (bytes == -1) {
						break;
					}
					if (buffer.remaining() == 0) {
						buffer = resizeBuffer(buffer, buffer.capacity() * 2);
					}
				}
			} catch (IOException e) {
				e.getMessage();
			}
		}

		buffer.flip();
		return buffer;
	}

	/**
	 * Resizes a byte buffer - only used locally in the ioResourceToByteBuffer method 
	 * @param buffer
	 * @param newCapacity
	 * @return
	 */
	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {
		ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
		buffer.flip();
		newBuffer.put(buffer);
		return newBuffer;
	}

}